package net.javaguides.banking.controller;

import net.javaguides.banking.DTO.AccountDto;
import net.javaguides.banking.service.AccountService;
import org.hibernate.sql.results.internal.ResolvedSqlSelection;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController
{
    //This is change we are performing

    //new second changes i created
   private AccountService accountService;

    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    //add account rest api
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto)
    {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }
    //get account by Id
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id)
    {
        AccountDto accountDto =accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //deposit
    @PutMapping
    public ResponseEntity<AccountDto> deposit(Long id,
                                              Map<String,Double>request){

        Double amount =request.get("amount");
        AccountDto accountDto=accountService.deposit(id,amount);
        return ResponseEntity.ok(accountDto);

    }

    //withdraw
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto>withdraw(@PathVariable Long id,
                                              @RequestBody Map<String,Double> request){
        double amount = request.get("amount");
        AccountDto accountDto=accountService.withdraw(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    //Get all
    @GetMapping
    public ResponseEntity<List<AccountDto>>getAllAccount(){
        List<AccountDto> accounts =accountService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

    //delete account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id)
    {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted sucessfully");
    }

}
