package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.entities.Product;
import com.hallakShop.hallakShop.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

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
        Optional<Product> productOptional = repository.findById(id);
        Product product = productOptional.get();
        return modelMapper.map(product, ProductDTO.class);
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> products = repository.findAll(pageable);
        return products.map(x -> modelMapper.map(x, ProductDTO.class));
    }


    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product product = modelMapper.map(dto, Product.class);
        repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id){
           Product entity = repository.getReferenceById(id);
           entity.setName(dto.getName());
           entity.setPrice(dto.getPrice());
           entity.setDescription(dto.getDescription());
           entity.setImgUrl(dto.getImgUrl());
           return modelMapper.map(entity, ProductDTO.class);
    }


    @Transactional
    public ProductDTO delete(Long id){
        Optional<Product> productOptional = repository.findById(id);
        Product product = productOptional.get();
        repository.deleteById(id);
        return modelMapper.map(product, ProductDTO.class);
    }

}
