package com.example.adstp5back.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adstp5back.model.Property;
import com.example.adstp5back.service.PropertyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

// http://localhost:8080/swagger-ui/index.html
@RestController
@RequestMapping("/api")
public class PropertyController {
    @Autowired
    PropertyService service;

    @PostMapping("/property")
    public Property saveProperty(@RequestBody Property property) {
        return service.create(property);
    }

    @GetMapping("/property/{id}")
    public Property readProperty(@PathVariable Long id) {
        Property property = new Property();
        property.setId(id);
        return service.read(property);
    }

    @GetMapping("/property/all")
    public List<Property> readPropertyAll() {
        return service.readAll();
    }

    @GetMapping("/property/search/{query}")
    public List<Property> searchProperty(@PathVariable String query) {
        return service.readByTitleOrDescription(query);
    }

    @GetMapping("/property/restore")
    public boolean restoreProperties() {
        return service.restore();
    }

    @PutMapping("/property")
    public Property update(@RequestBody Property property) {
        return service.update(property);
    }

    @DeleteMapping("/property/{id}")
    public boolean delete(@PathVariable long id) {
        return service.delete(id);
    }
}
