package org.joel.test.springboot.app.sprigboot_test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Transaction;
import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.TransactionDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.w3c.dom.stylesheets.LinkStyle;

import static org.hamcrest.Matchers.*;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountControllerWebTestClientTests {

    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testTransfer() throws JsonProcessingException {
        //Given
        TransactionDTO dto = new TransactionDTO();

        //Given
        dto.setDestinationAccountId(2L);
        dto.setOriginAccountId(1L);
        dto.setAmount(new BigDecimal("100"));
        dto.setBankId(1L);


        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Ok transaction");
        response.put("transaction", dto);

    //When
        //client.post().uri("http://localhost:8080/api/account/transfer")
        client.post().uri("/api/account/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                //Then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                //Se puede especificar el return esperado
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertEquals("Ok transaction", json.path("message").asText());
                        assertEquals(1L, json.path("transaction").path("originAccountId").asLong());
                        assertEquals(LocalDate.now().toString(), json.path("date").asText());
                        assertEquals("100", json.path("transaction").path("amount").asText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .jsonPath("$.message").isNotEmpty()
                .jsonPath("$.message").value(is("Ok transaction"))
                //Lo mismo pero con lambda
                .jsonPath("$.message").value(value -> {
                    assertEquals("Ok transaction", value);
                })
                //Otra forma
                .jsonPath("$.message").isEqualTo("Ok transaction")
                .jsonPath("$.transaction.originAccountId").isEqualTo(dto.getOriginAccountId())
                .json(objectMapper.writeValueAsString(response));
    }

    @Test
    @Order(2)
    void testDetail() {
//        Si el primer test sale bien
        Account account = new Account(1L, "Andrés", new BigDecimal("900"));
       // Account account = new Account(1L, "Andrés", new BigDecimal("1000"));

        try {
            client.get().uri("/api/account/1").exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.person").isEqualTo("Andrés")
                    .jsonPath("$.balance").isEqualTo(900)
                    .json(objectMapper.writeValueAsString(account));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //Si el primer test sale bien, esta transaccion queda como
//                .jsonPath("$.balance").isEqualTo(900);
    }

    @Test
    @Order(3)
    void testDetail2() {
        client.get().uri("/api/account/2").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Account.class)
                .consumeWith(res -> {
                    Account account = res.getResponseBody();
                    assertNotNull(account);
                    assertEquals("John", account.getPerson());
                    assertEquals("2100.00", account.getBalance().toPlainString());
                });
    }

    @Test
    @Order(4)
    void testListar() {
        client.get().uri("/api/account").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].person").isEqualTo("Andrés")
                .jsonPath("$[0].id").isEqualTo(1)
                //Cambiar a 900 si el primer teste esta bien
                .jsonPath("$[0].balance").isEqualTo(900)
                .jsonPath("$[1].person").isEqualTo("John")
                .jsonPath("$[1].id").isEqualTo(2)
                //Cambiar a 2100 si el primer teste esta bien
                .jsonPath("$[1].balance").isEqualTo(2100)
                .jsonPath("$").isArray()
                .jsonPath("$").value(hasSize(2));
    }

    @Test
    @Order(5)
    void testListar2() {
        client.get().uri("/api/account").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                .consumeWith(res -> {
                    //Cambiar saldo a 900 y 2100 respectivamente si el primer test  es positivo
                    List<Account> accounts = res.getResponseBody();
                    assertNotNull(accounts);
                    assertEquals(2, accounts.size());
                    assertEquals(1L, accounts.get(0).getId());
                    assertEquals("Andrés", accounts.get(0).getPerson());
                    assertEquals(900, accounts.get(0).getBalance().intValue());

                    assertEquals(2L, accounts.get(1).getId());
                    assertEquals("John", accounts.get(1).getPerson());
                    assertEquals("2100.00", accounts.get(1).getBalance().toPlainString());
                })
                .hasSize(2)
                .value(hasSize(2));
    }

    @Test
    @Order(6)
    void testSave() {
        //Given
        Account account = new Account(null, "Pepe", new BigDecimal("3000"));

        //When
        client.post().uri("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(account)
                .exchange()

                //Then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.person").isEqualTo("Pepe")
                .jsonPath("$.person").value(is("Pepe"))
                .jsonPath("$.balance").isEqualTo(3000);
    }
    @Test
    @Order(7)
    void testSave2() {
        //Given
        Account account = new Account(null, "Pepa", new BigDecimal("3500"));

        //When
        client.post().uri("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(account)
                .exchange()

                //Then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Account.class)
                .consumeWith(res -> {
                    Account c = res.getResponseBody();
                    assertNotNull(c);
                    assertEquals(4L, c.getId());
                    assertEquals("Pepa", c.getPerson());
                    assertEquals("3500", c.getBalance().toPlainString());
                });
    }

    @Test
    @Order(8)
    void testDelete() {
        //Given
        client.get().uri("/api/account").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                .hasSize(4);


        client.delete().uri("/api/account/3")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        client.get().uri("/api/account").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Account.class)
                .hasSize(3);

        client.get().uri("/api/account/3").exchange()
//                .expectStatus().is5xxServerError();
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }
}