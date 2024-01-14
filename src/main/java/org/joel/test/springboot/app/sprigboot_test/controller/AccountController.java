package org.joel.test.springboot.app.sprigboot_test.controller;

import org.joel.test.springboot.app.sprigboot_test.models.Account;
import org.joel.test.springboot.app.sprigboot_test.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("")
    public Account details(Long id) {
        return this.accountService.findById(id);
    }

}
