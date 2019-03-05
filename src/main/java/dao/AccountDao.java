package dao;

import entity.Account;
import lombok.AllArgsConstructor;
import repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AccountDao implements Dao<Account> {

    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void delete(Account account) {
        accountRepository.delete(account);
    }

}
