package com.example.contact.config;

import com.example.contact.authentication.token.TokenFilterHandler;
import com.example.contact.authentication.token.TokenOAuth2Handler;
import com.example.contact.authentication.token.TokenUtils;
import com.example.contact.authentication.user.oauth2.KakaoService;
import com.example.contact.authentication.user.oauth2.NaverService;
import com.example.contact.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final TokenOAuth2Handler oAuth2Handler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // CORS Filter
//                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    auth.requestMatchers("/token/**", "/static/**",
                            "/ws", "/topic", "/app", "/oauth2/**").permitAll();
//                    auth.requestMatchers("/users/**").authenticated();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(
                        new TokenFilterHandler(tokenUtils, userService),
                        AuthorizationFilter.class
                )
                .oauth2Login(oauth2->oauth2
                        .userInfoEndpoint(userInfo-> userInfo
                                .userService(oAuth2UserServiceSelector()))
                        .successHandler(oAuth2Handler)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserServiceSelector(){
        return userRequest -> {
            String registerId = userRequest.getClientRegistration().getRegistrationId();
            if ("naver".equals(registerId)){
                return naverService.loadUser(userRequest);
            }
            else if ("kakao".equals(registerId)){
                return kakaoService.loadUser(userRequest);
            }
            else {
                throw new OAuth2AuthenticationException("Unsupported provider"+ registerId);
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
