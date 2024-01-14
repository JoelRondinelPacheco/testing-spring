package org.joel.test.springboot.app.sprigboot_test.controller;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.models.TransactionDTO;
import org.joel.test.springboot.app.sprigboot_test.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Account details(@PathVariable Long id) {
        return this.accountService.findById(id);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransactionDTO dto) {
        accountService.transaction(dto.getOriginAccountId(), dto.getDestinationAccountId(), dto.getAmount(), dto.getBankId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Ok transtaction");
        response.put("transaction", dto);
        return ResponseEntity.ok(response);

    }



}
