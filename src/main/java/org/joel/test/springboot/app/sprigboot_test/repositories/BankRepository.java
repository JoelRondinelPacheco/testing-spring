package org.joel.test.springboot.app.sprigboot_test.repositories;

import org.joel.test.springboot.app.sprigboot_test.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
