package com.bootcamp.BootcampProject.controller;

import com.bootcamp.BootcampProject.service.UnlockAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UnlockAccountController {

    @Autowired
    UnlockAccountService unlockAccountService;

    @PostMapping("/unlock-account")
    public String unlockAccount(@RequestBody String email){
        return unlockAccountService.unlockAccount(email);
    }

    @PutMapping("/account-unlocked")
    public String updateAccountStatus(@RequestParam("token") String unlockToken) throws Exception {
        return unlockAccountService.setUnlockAccount(unlockToken);
    }
}
