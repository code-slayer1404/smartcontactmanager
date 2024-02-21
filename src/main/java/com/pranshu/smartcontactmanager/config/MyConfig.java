package com.pranshu.smartcontactmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig {
    @Bean
    public UserDetailsService getUserDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // **************************New Spring 6.1 way *********************************************** */
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // http.authorizeHttpRequests(authz->authz.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").requestMatchers("/**").permitAll()).formLogin(Customizer.withDefaults());
    // http.authenticationProvider(getAuthenticationProvider());

    // return http.build();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authz -> authz.requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER").requestMatchers("/**").permitAll())
                .formLogin(
                        f ->   f.loginPage("/login")
                                .loginProcessingUrl("/do_login")
                                .defaultSuccessUrl("/user/index"))
                .csrf(c -> c.disable());

        http.authenticationProvider(getAuthenticationProvider());

        return http.build();
    }

}
