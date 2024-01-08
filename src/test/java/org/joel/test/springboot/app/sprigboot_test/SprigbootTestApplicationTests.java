package org.joel.test.springboot.app.sprigboot_test;

import org.joel.test.springboot.app.sprigboot_test.repositories.AccountRepository;
import org.joel.test.springboot.app.sprigboot_test.repositories.BankRepository;
import org.joel.test.springboot.app.sprigboot_test.services.AccountService;
import org.joel.test.springboot.app.sprigboot_test.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SprigbootTestApplicationTests {

	AccountRepository accountRepository;

	BankRepository bankRepository;

	AccountService service;

	@BeforeEach
	void setUp() {
		accountRepository = mock(AccountRepository.class);
		bankRepository = mock(BankRepository.class);
		service = new AccountServiceImpl(accountRepository, bankRepository);
	}

	@Test
	void contextLoads() {
		when(accountRepository.findById(1L)).thenReturn(Data.ACCOUNT_001);
		when(accountRepository.findById(2L)).thenReturn(Data.ACCOUNT_002);
		when(bankRepository.findById(1L)).thenReturn(Data.BANK);

		BigDecimal originBalance = service.reviewBalance(1L);
		BigDecimal destinationBalance = service.reviewBalance(2L);

		assertEquals("2000", destinationBalance.toPlainString());
		assertEquals("1000", originBalance.toPlainString());

		service.transaction(1L, 2L, new BigDecimal("100"), 1L);

		originBalance = service.reviewBalance(1L);
		destinationBalance = service.reviewBalance(2L);

		assertEquals("900", originBalance.toPlainString());
		assertEquals("2100", destinationBalance.toPlainString());
	}



}
