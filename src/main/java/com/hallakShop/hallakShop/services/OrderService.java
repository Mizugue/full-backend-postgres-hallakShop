package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.OrderDTO;
import com.hallakShop.hallakShop.dto.OrderItemDTO;
import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.entities.Order;
import com.hallakShop.hallakShop.entities.OrderItem;
import com.hallakShop.hallakShop.entities.Product;
import com.hallakShop.hallakShop.repositories.OrderRepository;
import com.hallakShop.hallakShop.services.exceptions.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    // Método que configura o ModelMapper para usar o construtor personalizado do OrderItemDTO
// ao mapear um objeto da classe OrderItem para OrderItemDTO. O construtor específico
// garante que os dados sejam extraídos corretamente de OrderItem (incluindo o nome do produto),
// mesmo que o ModelMapper geralmente tente usar setters.
    @PostConstruct
    public void configureModelMapper() {
    modelMapper.createTypeMap(OrderItem.class, OrderItemDTO.class)
               .setProvider(request -> new OrderItemDTO((OrderItem) request.getSource()));
}


    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Resource not found"));
        return modelMapper.map(order, OrderDTO.class);
    }

}
