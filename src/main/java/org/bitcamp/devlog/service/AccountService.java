package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.mapper.AccountMapper;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AccountService {

    final AccountMapper accountMapper;

    public void save(Account account) {
        accountMapper.save(account);

    }
    public void update(Account account) {

        accountMapper.update(account);
    }
    public void saveBlog(Account account) {


    }
}
