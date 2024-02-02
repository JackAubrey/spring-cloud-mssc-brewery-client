package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {
    private static final String BEER_PATH_V1 = "/api/v1/beer";
    private String apiHost;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID beerId) {
        return restTemplate.getForObject(apiHost + BEER_PATH_V1 + "/" + beerId, BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(apiHost + BEER_PATH_V1, beerDto);
    }

    public void updateBeer(UUID beerId, BeerDto beerDto) {
        restTemplate.put(apiHost + BEER_PATH_V1 + "/" + beerId, beerDto);
    }

    public void deleteBeerById(UUID beerId) {
        restTemplate.delete(apiHost + BEER_PATH_V1 + "/" + beerId);
    }

    /**
     *
     * @param apiHost
     */
    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
