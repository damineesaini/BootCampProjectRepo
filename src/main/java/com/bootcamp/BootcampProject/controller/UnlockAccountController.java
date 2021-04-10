package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.service.UnlockAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unlock")
public class UnlockAccountController {

    @Autowired
    UnlockAccountService unlockAccountService;

    @GetMapping("/unlock-account")
    public String unlockAccount(@RequestParam("email") String email){
        return unlockAccountService.unlockAccount(email);
    }

    @PutMapping("/account-unlocked")
    public String updateAccountStatus(@RequestParam("token") String unlockToken) throws Exception {
        return unlockAccountService.setUnlockAccount(unlockToken);
    }
}
