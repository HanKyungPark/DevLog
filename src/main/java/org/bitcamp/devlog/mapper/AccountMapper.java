package org.bitcamp.devlog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Account;


@Mapper
public interface AccountMapper {


    void saveAccount(Account account);

    void updateAccount(Account account);

}


