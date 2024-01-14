package org.joel.test.springboot.app.sprigboot_test.repositories;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByPerson(String personName);
   // List<Account> findAll();

  //  Optional<Account> findById(Long id);

   // void update(Account account);

}
