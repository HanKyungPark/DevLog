package org.bitcamp.devlog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Account;

import java.util.List;


@Mapper
public interface AccountMapper {


    void save(Account account);

    void update(Account account);

    //블로그 검색
    List<Account> findAllByBlogId(String blogId);
}


