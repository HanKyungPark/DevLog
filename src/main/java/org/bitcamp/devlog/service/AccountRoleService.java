package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.AccountRole;
import org.bitcamp.devlog.mapper.AccountRoleMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRoleService {

    final AccountRoleMapper accountRoleMapper;

    public void save(AccountRole accountRole) {

        accountRoleMapper.save(accountRole);
    }
}
