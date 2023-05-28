package com.spring.yoon.football.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import javax.sql.DataSource;
import java.util.Arrays;


@RequiredArgsConstructor
@Configuration
//@EnableWebSecurity auto으로 들어가있음.
public class SecurityConfig {

    private final AuthenticationConfiguration configuration;
    private final DataSource dataSource;
    private final UserDetailsService authService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeHttpRequests()
                .antMatchers("/sign-in", "/sign-up", "/js/**", "/api/login", "/api/sign-up", "/api/auth/sign-up", "/h2-console/**","/upload/**").permitAll()
                .antMatchers("/api/admin/**", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()

                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/sign-in");
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AjaxAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AjaxAuthenticationAccessDenied();
    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new AjaxLogoutSuccessHandler();
    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy(){
        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy=
        new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());

       concurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
       concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);

       SessionFixationProtectionStrategy sessionFixationProtectionStrategy=new SessionFixationProtectionStrategy();
       ChangeSessionIdAuthenticationStrategy changeSessionIdAuthenticationStrategy = new ChangeSessionIdAuthenticationStrategy();

       RegisterSessionAuthenticationStrategy registerSessionStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());

       CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy=new CompositeSessionAuthenticationStrategy(
               Arrays.asList(concurrentSessionControlAuthenticationStrategy,changeSessionIdAuthenticationStrategy,sessionFixationProtectionStrategy,registerSessionStrategy));
       return sessionAuthenticationStrategy;
   }


    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {

        AjaxAuthenticationFilter ajaxAuthenticationFilter = new AjaxAuthenticationFilter();
        ajaxAuthenticationFilter.setAuthenticationManager(authenticationManager());
        ajaxAuthenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
        ajaxAuthenticationFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        ajaxAuthenticationFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        return ajaxAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {

        ProviderManager manager = (ProviderManager) configuration.getAuthenticationManager();
        manager.getProviders().add(ajaxAuthenticationProvider());
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AjaxAuthenticationProvider ajaxAuthenticationProvider(){

        return new AjaxAuthenticationProvider();
    }


    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler(){

        return  new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler(){

        return new AjaxAuthenticationSuccessHandler();
    }









}
