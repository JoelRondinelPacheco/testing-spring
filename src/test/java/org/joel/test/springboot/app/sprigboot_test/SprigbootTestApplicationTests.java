package org.joel.test.springboot.app.sprigboot_test;

import org.joel.test.springboot.app.sprigboot_test.exceptions.InsufficientMoneyException;
import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.Bank;
import org.joel.test.springboot.app.sprigboot_test.repositories.AccountRepository;
import org.joel.test.springboot.app.sprigboot_test.repositories.BankRepository;
import org.joel.test.springboot.app.sprigboot_test.services.AccountService;
import org.joel.test.springboot.app.sprigboot_test.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SprigbootTestApplicationTests {

	//@Mock //Solo con mockito
	@MockBean //MOckito y Spring
	AccountRepository accountRepository;

	//@Mock //Solo con mockito
	@MockBean //MOckito y Spring
	BankRepository bankRepository;

	//@InjectMocks //Al injctar mocks usar la implementacion
	//AccountServiceImpl service;
	@Autowired //Necesita estar definido como @Services, se puede dejar la interfaz, spring busca la implementacion
	AccountService service;
	//AccountService service;

	@BeforeEach
	void setUp() {
//		accountRepository = mock(AccountRepository.class);
//		bankRepository = mock(BankRepository.class);
//		service = new AccountServiceImpl(accountRepository, bankRepository);
	}

	@Test
	void contextLoads() {
		when(accountRepository.findById(1L)).thenReturn(Data.getAccount001());
		when(accountRepository.findById(2L)).thenReturn(Data.getAccount002());
		when(bankRepository.findById(1L)).thenReturn(Data.getBank());

		BigDecimal originBalance = service.reviewBalance(1L);
		BigDecimal destinationBalance = service.reviewBalance(2L);

		assertEquals("2000", destinationBalance.toPlainString());
		assertEquals("1000", originBalance.toPlainString());

		service.transaction(1L, 2L, new BigDecimal("100"), 1L);

		originBalance = service.reviewBalance(1L);
		destinationBalance = service.reviewBalance(2L);

		assertEquals("900", originBalance.toPlainString());
		assertEquals("2100", destinationBalance.toPlainString());

		int total = service.reviewAllTransactions(1L);

		assertEquals(1, total);

		verify(accountRepository, times(3)).findById(1L);
		verify(accountRepository, times(3)).findById(2L);
		verify(accountRepository, times(2)).save(any(Account.class));
		verify(accountRepository, times(3)).findById(2L);

		verify(bankRepository, times(2)).findById(1L);
		verify(bankRepository).save(any(Bank.class));

		verify(accountRepository, times(6)).findById(anyLong());
		verify(accountRepository, never()).findAll();

	}
	@Test
	void contextLoads2() {
		when(accountRepository.findById(1L)).thenReturn(Data.getAccount001());
		when(accountRepository.findById(2L)).thenReturn(Data.getAccount002());
		when(bankRepository.findById(1L)).thenReturn(Data.getBank());

		BigDecimal originBalance = service.reviewBalance(1L);
		BigDecimal destinationBalance = service.reviewBalance(2L);

		assertEquals("1000", originBalance.toPlainString());
		assertEquals("2000", destinationBalance.toPlainString());

		assertThrows(InsufficientMoneyException.class, () -> {
			service.transaction(1L, 2L, new BigDecimal("1200"), 1L);
		});

		originBalance = service.reviewBalance(1L);
		destinationBalance = service.reviewBalance(2L);

		assertEquals("1000", originBalance.toPlainString());
		assertEquals("2000", destinationBalance.toPlainString());

		int total = service.reviewAllTransactions(1L);

		assertEquals(0, total);

		verify(accountRepository, times(3)).findById(1L);
		verify(accountRepository, times(2)).findById(2L);
		verify(accountRepository, never()).save(any(Account.class));

		verify(bankRepository, times(1)).findById(1L);
		verify(bankRepository, never()).save(any(Bank.class));

		verify(accountRepository, times(5)).findById(anyLong());
		verify(accountRepository, never()).findAll();

	}


	@Test
	void contextLoads3() {
		when(accountRepository.findById(1L)).thenReturn(Data.getAccount001());

		Account account1 = service.findById(1L);
		Account account2 = service.findById(1L);

		assertSame(account1, account2);
		assertTrue(account1 == account2);
		assertEquals("Andrés", account1.getPerson());
		assertEquals("Andrés", account2.getPerson());

		verify(accountRepository, times(2)).findById(1L);
	}

	@Test
	void testFindAll() {
		//Given
		List<Account> data = Arrays.asList(Data.getAccount001().orElseThrow(), Data.getAccount002().orElseThrow());
		when(accountRepository.findAll()).thenReturn(data);

		// when
		List<Account> accounts = service.findAll();

		//then
		assertFalse(accounts.isEmpty());
		assertEquals(2, accounts.size());
		assertTrue(accounts.contains(Data.getAccount002().orElseThrow()));

		verify(accountRepository).findAll();
	}

	@Test
	void testSave() {
		//Given
		Account accountPepe = new Account(null, "Pepe", new BigDecimal("3000"));
		when(accountRepository.save(any())).then(invocation -> {
			Account c = invocation.getArgument(0);
			c.setId(3L);
			return c;
		});

		//When
		Account account = service.save(accountPepe);

		// Then
		assertEquals("Pepe", account.getPerson());
		assertEquals(3, account.getId());
		assertEquals("3000", account.getBalance().toPlainString());
		verify(accountRepository).save(any());

	}
}
