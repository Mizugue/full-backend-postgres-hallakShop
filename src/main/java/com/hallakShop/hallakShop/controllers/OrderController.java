package com.hallakShop.hallakShop.controllers;

import com.hallakShop.hallakShop.dto.OrderDTO;
import com.hallakShop.hallakShop.dto.ProductDTO;
import com.hallakShop.hallakShop.dto.ProductMinDTO;
import com.hallakShop.hallakShop.services.OrderService;
import com.hallakShop.hallakShop.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    
}
