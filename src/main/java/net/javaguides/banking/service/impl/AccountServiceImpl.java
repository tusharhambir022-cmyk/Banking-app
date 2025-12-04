package net.javaguides.banking.service.impl;

import net.javaguides.banking.DTO.AccountDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.exception.AccountException;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.repository.AccountRepository;
import net.javaguides.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService
{

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto)
    {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id)
    {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountException("Account Does Not Found"));

        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount)
    {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does Not Existed"));
        double total = account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount)
    {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does Not Existed"));

                if(account.getBalance()<amount)
                {
                    throw new RuntimeException("Insufficeint balance");
                }

                double total =account.getBalance()-amount;
                account.setBalance(total);
                Account savedAccounnt =accountRepository.save(account);


        return AccountMapper.mapToAccountDto(savedAccounnt);
    }

    @Override
    public List<AccountDto> getAllAccount()
    {
        List<Account> accounts=accountRepository.findAll();
        return accounts.stream().map((account -> AccountMapper.mapToAccountDto(account)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id)
    {
        Account account =accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does Not Existed"));

        accountRepository.deleteById(id);

    }
}
