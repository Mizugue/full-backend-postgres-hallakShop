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
import java.util.List;
import java.util.Set;


@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;


    @Autowired
    public CategoryService(ModelMapper modelMapper, CategoryRepository categoryRepository){
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }




    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List <Category> result = categoryRepository.findAll();
        return result.stream().map(x -> modelMapper.map(x, CategoryDTO.class)).toList();
    }






}
