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

                repository.save(new Property(0, "Casa en la playa",
                                "Se presenta una interesante oportunidad de adquirir una espléndida propiedad ubicada en una zona privilegiada de Marbella. Se trata de una amplia casa aislada, con su propia parcela independiente, situada en el casco histórico de la ciudad. La parcela cuenta con una extensión de 522 metros cuadrados, proporcionando un espacio adecuado y suficiente para disfrutar de la vida al aire libre, con un bonito jardín que cuenta con una variada vegetación que incluye aguacates y otras especies de plantas.",
                                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.bleehalligan.co.uk%2Fmedia%2Ffiler_public_thumbnails%2Ffiler_public%2F79%2Fe7%2F79e7b029-fd2a-4aa0-9a9a-765edd2ab1b9%2Fbeachviewsml.jpg__1920x0_q85_subsampling-2_upscale.jpg&f=1&nofb=1&ipt=3d8cde3c45136d99f0aff997ea06796a5b28e22a1aad58fb856dc167e546c8aa",
                                (float) 900000, 35, 2, 1, location));
                repository.save(new Property(0, "Venta Departamento 3 amb c/balcón en Devoto Apto Crédito Hipotecario",
                                "Venta Depto 3 Amb con balcón al frente, sobre Av. Beiró. en Villa Devoto. APTO CRÉDITO HIPOTECARIO. Departamento tres ambientes en Villa Devoto, primer piso por escalera, al frente. Excelente ubicación, sobre Av. Beiró. A 400 metros de Cuenca, a 500 metros de Plaza Arenales y a 500 metros de Av. San Martin. Living Comedor luminoso con balcón al frente. Cocina independiente con alacenas alto y bajo mesada y comedor diario. Lavadero separado de amplias medidas con cerramiento.",
                                "https://nunainteriores.com/wp-content/uploads/2020/11/3.1.DEPTO-3-AMBIENES-1080x1080-1.jpg",
                                (float) 170000, 30, 3, 1, location));
                repository.save(new Property(0,
                                "Departamento en Venta en Palermo - Terraza, Pileta, Quincho - 5 Dorm. Luz - 2 Cocheras",
                                "Inigualable tríplex de categoría sobre Avenida del Libertador y Bulnes, con gran terraza de 190m2 con y pileta propia y vistas espectaculares al río. Un verdadero oasis urbano que combina el confort de una casa con las comodidades de un edificio de excelencia, en pleno corazón de Palermo Chico.",
                                "https://imgar.zonapropcdn.com/avisos/resize/1/00/56/89/96/26/1200x1200/1993527741.jpg",
                                (float) 170000, 34, 5, 3, location));
                repository.save(new Property(0,
                                "2 AMBIENTES A ESTRENAR!",
                                " 2 ambientes a estrenar en Viamonte esquina Rodríguez Peña, fácil acceso, múltiples colectivos y cercanía con subte D.",
                                "https://static1.adinco.net/1927736_p/extra_large_u_68911ba263cc7.jpg",
                                (float) 170000, 50, 1, 1, location));
                repository.save(new Property(0,
                                "Jorge Luis Borges al 2000",
                                "EXTRAORDINARIA PROPIEDAD USO COMERCIAL\n" + //
                                                "IDEAL GASTRONOMÍA\n" + //
                                                "2 PLANTAS + TERRAZA\n" + //
                                                "2 ENTRADAS (2 PUERTAS)\n" + //
                                                "PB: tipo chorizo con patio cubierto varias habitaciones + baño + cocina + entrepido pequeño\n"
                                                + //
                                                "1er PISO: varias habittaciones + galería vidriada + 2 baños + balcones\n"
                                                + //
                                                "Terraza de 100m2 + habitación + cocina.",
                                "https://static1.adinco.net/7534136_p/extra_large_u_67ab71282b4ba.jpg",
                                (float) 1450000, 460, 10, 3, location));

                return true;
        }

}
