package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.entities.Product;
import com.hallakShop.hallakShop.repositories.ProductRepository;
import com.hallakShop.hallakShop.services.exceptions.DatabaseException;
import com.hallakShop.hallakShop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        Product product = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Resource not found"));
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
        try {
            Product entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
           entity.setPrice(dto.getPrice());
           entity.setDescription(dto.getDescription());
           entity.setImgUrl(dto.getImgUrl());
           return modelMapper.map(entity, ProductDTO.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");
        }}


    @Transactional(propagation = Propagation.SUPPORTS)
    public ProductDTO delete(Long id)  {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found");
        }
        try {
            Product product = repository.getReferenceById(id);
            repository.deleteById(id);
            return modelMapper.map(product, ProductDTO.class);

        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Failure of referential integrity");
        }
    }

}
