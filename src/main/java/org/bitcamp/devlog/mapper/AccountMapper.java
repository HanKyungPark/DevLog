package org.bitcamp.devlog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Account;

import java.util.List;
import org.checkerframework.checker.units.qual.A;


@Mapper
public interface AccountMapper {

    void save(Account account);

    void update(Account account);

    Account findByEmail(String email);


    Long countByHomePage(String homepage);


    Account findByHomepage(String homepage);

    //블로그 검색
    Account findByBlogId(String blogId);

    List<Account> findAll();

    String findNameByAccountId(long accountId);


    String findFileByAccountId(Long accountId);

    List<Account> findAllOrderByCreatedAt();

    String findnameByAccountId(Long accountId);


    String findHomepageByAccountId(Long accountId);

    String findEmailByAccountId(Long accountId);

    List<Account> findbyhits();

    List<Account> findbypost();

    Account findByAccountId(Long accountId);
}


