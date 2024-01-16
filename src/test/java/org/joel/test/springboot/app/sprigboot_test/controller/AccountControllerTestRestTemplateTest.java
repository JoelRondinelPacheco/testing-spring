package org.joel.test.springboot.app.sprigboot_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joel.test.springboot.app.sprigboot_test.models.TransactionDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testTransferir() {
        //Given
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(new BigDecimal("100"));
        dto.setDestinationAccountId(2L);
        dto.setOriginAccountId(1L);
        dto.setBankId(1L);

        ResponseEntity<String> response = client.postForEntity("/api/account/transfer", dto, String.class);

        String json = response.getBody();

        assertNotNull(json);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertTrue(json.contains("Ok transaction"));
    }

}