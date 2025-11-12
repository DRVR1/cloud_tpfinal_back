package com.example.adstp5back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adstp5back.model.Location;
import com.example.adstp5back.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Location getDefault() {
        Location location = new Location();
        location.setTitle("Bariloche (Ubicacion de prueba)");
        location.setId(0);
        // bariloche -41.132868, -71.309051
        location.setLatitude(-41.132868);
        location.setLongitude(-71.309051);
        location.setLink(
                "https://www.openstreetmap.org/directions?from=&to=-41.132868%2C-71.309051#map=19/-41.132868/-71.309051");
        return this.save(location);
    }
}
