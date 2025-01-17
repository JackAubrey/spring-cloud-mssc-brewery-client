package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class BreweryClientTest {
    @Autowired
    BreweryClient client;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getBeerById() {
        BeerDto beerDto = client.getBeerById(UUID.randomUUID());

        assertNotNull(beerDto);
    }

    @Test
    void saveNewBeer() {
        BeerDto beerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Foo Name")
                .beerStyle("Foo Style")
                .build();

        URI uri = client.saveNewBeer(beerDto);

        assertNotNull(uri);

        log.info("Saved Beer V1 Location = {}", uri);
    }

    @Test
    void updateBeer() {
        BeerDto beerDto = BeerDto.builder()
                .beerName("Update Foo Name")
                .beerStyle("Update Foo Style")
                .build();

        assertDoesNotThrow( () -> client.updateBeer(UUID.randomUUID(), beerDto) );
    }

    @Test
    void deleteBeerById() {
        assertDoesNotThrow( () -> client.deleteBeerById(UUID.randomUUID()) );
    }

    @Test
    void getCustomerById() {
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());

        assertNotNull(customerDto);
    }

    @Test
    void saveNewCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .name("Foo Name")
                .build();

        URI uri = client.saveNewCustomer(customerDto);

        assertNotNull(uri);

        log.info("Saved new Customer V1 location = {}", uri);
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .name("Update Foo Name")
                .build();

        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void deleteCustomerById() {
        client.deleteCustomerById(UUID.randomUUID());
    }
}