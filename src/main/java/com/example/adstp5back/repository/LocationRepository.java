package com.example.adstp5back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adstp5back.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
