package com.hallakShop.hallakShop.services;

import com.hallakShop.hallakShop.dto.CategoryDTO;
import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.dto.ProductMinDTO;
import com.hallakShop.hallakShop.entities.Category;
import com.hallakShop.hallakShop.entities.Product;
import com.hallakShop.hallakShop.repositories.CategoryRepository;
import com.hallakShop.hallakShop.repositories.ProductRepository;
import com.hallakShop.hallakShop.services.exceptions.DatabaseException;
import com.hallakShop.hallakShop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
public class ProductService {

    private CategoryRepository categoryRepository;
    private ProductRepository repository;
    private ModelMapper modelMapper;


    @Autowired
    public ProductService (ProductRepository repository, ModelMapper modelMapper, CategoryRepository categoryRepository){
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Resource not found"));
        return modelMapper.map(product, ProductDTO.class);
    }


    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(Pageable pageable, String prefix){
        Page<Product> products = repository.findByNameContainingIgnoreCase(prefix, pageable);
        return products.map(x -> modelMapper.map(x, ProductMinDTO.class));
    }


    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        modelMapper.map(dto, product);
        Set<Category> managedCategories = new HashSet<>();
        for (CategoryDTO catDTO : dto.getCategories()) {
            Category managedCategory = categoryRepository.findById(catDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not founded: " + catDTO.getId()));
            managedCategories.add(managedCategory);
        }
        product.setCategories(managedCategories);
        repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id){
        try {
            Product product = repository.getReferenceById(id);

            product.setName(dto.getName());
           product.setPrice(dto.getPrice());
           product.setDescription(dto.getDescription());
           product.setImgUrl(dto.getImgUrl());

           product.getCategories().clear();

           Set<Category> managedCategories = new HashSet<>();

           for (CategoryDTO catDTO : dto.getCategories()){
               Category managedCategory = categoryRepository.findById(catDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Category not founded: " + catDTO.getId()));
                managedCategories.add(managedCategory);
           }
           product.setCategories(managedCategories);
           repository.save(product);
           return modelMapper.map(product, ProductDTO.class);

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


    // auxiliaries






}
