package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Service;
import com.bilgeadam.teknikservis.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final ServiceRepository repository;

    @Autowired
    public ServiceController(ServiceRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Service>> getAll(){
        return ResponseEntity.ok(repository.getAll());
    }
    }
