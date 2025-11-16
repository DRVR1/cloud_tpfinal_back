package com.example.adstp5back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adstp5back.model.Location;
import com.example.adstp5back.model.Property;
import com.example.adstp5back.repository.LocationRepository;
import com.example.adstp5back.repository.PropertyRepository;

@Service
public class PropertyService {

        @Autowired
        PropertyRepository repository;

        @Autowired
        LocationService locationService;

        public Property create(Property property) {
                return repository.save(property);
        }

        public Property read(Property property) {
                return repository.findById(property.getId()).get();
        }

        public List<Property> readByTitleOrDescription(String query) {
                return repository
                                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        }

        public List<Property> readAll() {
                return repository.findAll();
        }

        public Property update(Property property) {
                return repository.save(property);
        }

        public boolean delete(long id) {
                try {
                        repository.deleteById(id);
                        return true;
                } catch (Exception e) {
                        return false;
                }
        }

        public boolean restore() {
                repository.deleteAll();

                Location location = locationService.getDefault();

                repository.save(new Property(0, "Casa Mr. Black en Costa Esmeralda",
                                "Impactante casa de diseño en tonos negro, blanco y madera implantada en el barrio Senderos 4: con diseño minimalista Casa Mr. Black es una sólida construcción de 300 mts con fondo libre, es un espacio luminoso y pensado al detalle para el más alto confort y relax. Con capacidad para 8 personas, el ingreso a dos aguas diseñado en chapa negra y vidrio destaca al ingresar un espacio social amplio y blanco: el living comedor se integra sutilmente con la cocina completamente equipada, la cual está contenida con aberturas de techo a pared, disfrutándose visualmente de la piscina y el exterior, cuidadosamente parquizado.",
                                "https://imgar.zonapropcdn.com/avisos/resize/1/00/54/48/62/06/1200x1200/1932735637.jpg",
                                (float) 450000, 650, 4, 3,
                                "https://maps.app.goo.gl/YWe1DnHCTXZoxzBM8",
                                location, "L, Senderos IV, Costa Esmeralda, La pampa, Argentina"));
                repository.save(new Property(0,
                                "Venta Casa a estrenar en Martínez UNICA!",
                                "¡¡Oportunidad!! Excelente propiedad cuenta con una gran luminosidad natural en todos sus ambientes, que penetra a través de los amplios ventanales de seguridad con vidrios dobles (DVH), cuyos marcos son anodizados.",
                                "https://d1acdg20u0pmxj.cloudfront.net/listings/4b436282-39dc-4048-b35f-e959b28b38ea/860x440/ae4a8ecb-d107-44b3-aacf-f836e5d47f85.webp",
                                (float) 990000, 460, 10, 3, "https://maps.app.goo.gl/S9DRr8WLVPCa3w5f7", location,
                                "Emilio Mitre 1200, Martinez, San Isidro, Buenos Aires"));

                repository.save(new Property(0,
                                "VENTA PH 2 AMBIENTES VILLA URQUIZA",
                                "Ofrecemos a la venta PH de 2 ambientes con Patio en Villa Urquiza",
                                "https://d1acdg20u0pmxj.cloudfront.net/listings/c2879e87-e938-44d4-ba40-a3d6f5224d5e/860x440/bd1caa56-d35e-47b7-aec1-2757273563d7.webp",
                                (float) 85000, 45, 2, 1, "https://maps.app.goo.gl/qzsm3qdWdF7d4sYK9", location,
                                "Monroe 5500, Villa Urquiza, Capital Federal"));
                return true;
        }

}
