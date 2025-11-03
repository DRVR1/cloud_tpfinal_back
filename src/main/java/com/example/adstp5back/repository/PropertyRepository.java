package com.example.adstp5back.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adstp5back.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String desc);
}
