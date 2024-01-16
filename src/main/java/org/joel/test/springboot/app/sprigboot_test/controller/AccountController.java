package org.joel.test.springboot.app.sprigboot_test.controller;

import org.apache.coyote.Response;
import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.TransactionDTO;
import org.joel.test.springboot.app.sprigboot_test.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Account> list() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {

        Account account = null;
        try {
            account = this.accountService.findById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public Account save(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransactionDTO dto) {
        accountService.transaction(dto.getOriginAccountId(), dto.getDestinationAccountId(), dto.getAmount(), dto.getBankId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Ok transaction");
        response.put("transaction", dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountService.deleteById(id);
    }

}
