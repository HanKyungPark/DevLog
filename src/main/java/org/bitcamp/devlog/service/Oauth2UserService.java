package org.bitcamp.devlog.service;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.AccountResponse;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.mapper.AccountMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final AccountMapper accountMapper;


    //사용자 정보 데이터를 내부 인자로 받아오기
    @Override
    //OAuth2User 를 리턴하는 loaduser 라는 메서드를 만들고 내부 인자로는 OAuth2UserRequest userRequest 를 받고
    //예외처리  OAuth2AuthenticationException
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //OAuth2User라는 객체에 super를 통해서 부모클래스에서 존재하는 load user 메소드에서 userRequest를 넣어줘서
        //유저 정보를 가지고 온다
        OAuth2User oAuth2User = super.loadUser(userRequest);


        //확인을 위해서 syso으로 콘솔에 가져온 oAuth2User.getAttributes()를 찍어본다.
        System.out.println(oAuth2User.getAttributes());

        //userRequest 인자를 통해서 Oauth2 데이터가 넘어온다.

        //어떤 인증 프로바이드 인지 변수로 담아온다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        AccountResponse AccountResponse = null;


        if (registrationId.equals("kakao")) {

            AccountResponse = new AccountResponse(oAuth2User.getAttributes());

            System.out.println(AccountResponse.getName());
            System.out.println(AccountResponse.getEmail());
            System.out.println(AccountResponse.getFile());

            Account account = new Account();
            account.setName(AccountResponse.getName());
            account.setEmail(AccountResponse.getEmail());
            account.setFile(AccountResponse.getFile());

            accountMapper.saveAccount(account);
        } else {
            return null;
        }
        String role = "ROLE_USER";

//        Account account = new Account();

        return new Oauth2User(AccountResponse, role);
    }

}