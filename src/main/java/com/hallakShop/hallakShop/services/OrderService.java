package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.OrderDTO;
import com.hallakShop.hallakShop.dto.OrderItemDTO;
import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.entities.*;
import com.hallakShop.hallakShop.repositories.OrderItemRepository;
import com.hallakShop.hallakShop.repositories.OrderRepository;
import com.hallakShop.hallakShop.repositories.ProductRepository;
import com.hallakShop.hallakShop.services.exceptions.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    private ModelMapper modelMapper;
    private UserService userService;
    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;
    private AuthService authService;

    @Autowired
    public OrderService(OrderRepository repository, ModelMapper modelMapper, UserService userService, ProductRepository productRepository, OrderItemRepository orderItemRepository, AuthService authService){
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.authService = authService;

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
        authService.validateSelfOrAdmin(order.getClient().getId());
        return modelMapper.map(order, OrderDTO.class);
    }


    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        User user = userService.authenticated();
        order.setClient(user);
        for (OrderItemDTO itemDto : dto.getItems()){
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
            order.getItems().add(item);

        }
        repository.save(order);
        orderItemRepository.saveAll(order.getItems());
        return modelMapper.map(order, OrderDTO.class);
    }
}
