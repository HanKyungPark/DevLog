package org.bitcamp.devlog.config;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final Oauth2UserService oauth2UserService;



    //스프링 시큐리티 설정 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .formLogin((login) -> login.disable());
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oauth2UserService)));
        http
                .authorizeHttpRequests((ahth) -> ahth
                        //경로설정 oauth2 모든 경로와 login으로 시작하는 모든 경로를 permitAll을 시켜서 아무나 접속 가능하게 하고
                        .requestMatchers("*").permitAll()
                        .requestMatchers("css/*","js/*").permitAll()
                        //그 외 나머지 경로는 로그인 한 사람만 접근 가능
                        .anyRequest().authenticated());
        http
                .oauth2Login(login -> login
                        .loginPage("/login")
                );
        return http.build();
    }
}
