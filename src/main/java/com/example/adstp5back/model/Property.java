package com.example.adstp5back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 600)
    private String title;
    @Column(length = 600)
    private String description;
    @Column(length = 1500)
    private String imageUrl;
    private float price;

    // Agregar a service
    private float surface; // superficie en metros cuadrados
    private int bedrooms;
    private int bathrooms;

    private String mapsLink;

    @ManyToOne
    private Location location;

    private String address;

}
