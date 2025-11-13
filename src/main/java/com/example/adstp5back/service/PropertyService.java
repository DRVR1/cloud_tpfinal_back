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
                                (float) 1900000, 400, 4, 2,
                                "https://www.google.com/maps/place/Puerto+San+Carlos/@-41.1331821,-71.3125252,15z",
                                location, "4100 Cabrillo Hwy N, Half Moon Bay, CA 94019, United States"));
                repository.save(new Property(0,
                                "Venta Casa a estrenar en Martínez UNICA!",
                                "¡¡Oportunidad!! Excelente propiedad cuenta con una gran luminosidad natural en todos sus ambientes, que penetra a través de los amplios ventanales de seguridad con vidrios dobles (DVH), cuyos marcos son anodizados.",
                                "https://d1acdg20u0pmxj.cloudfront.net/listings/4b436282-39dc-4048-b35f-e959b28b38ea/860x440/ae4a8ecb-d107-44b3-aacf-f836e5d47f85.webp",
                                (float) 990000, 460, 10, 3, "https://maps.app.goo.gl/S9DRr8WLVPCa3w5f7", location,
                                "Emilio Mitre 1200, Martinez, San Isidro, Buenos Aires"));
                return true;
        }

}
