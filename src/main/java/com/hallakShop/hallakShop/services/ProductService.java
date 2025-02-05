package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.entities.Product;
import com.hallakShop.hallakShop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductService (ProductRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = repository.findById(id).get();
        return modelMapper.map(product, ProductDTO.class);
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> products = repository.findAll(pageable);
        return products.map(x -> modelMapper.map(x, ProductDTO.class));
    }

}
