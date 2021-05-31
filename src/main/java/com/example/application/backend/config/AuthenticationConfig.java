package com.example.application.backend.config;

import com.example.application.backend.security.jwt.JwtAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.Elements.CORS;

/**
 * This class configures Spring beans for authentication. The implementation is due to the
 * following sources:
 *
 * eparvan (https://stackoverflow.com/questions/42562893/could-not-autowire-no-beans-of-userdetailservice-type-found)
 * Roland Ewald (https://stackoverflow.com/questions/35912404/spring-boot-security-with-vaadin-login)
 * Eugen Paraschiv (http://www.baeldung.com/spring-security-registration-password-encoding-bcrypt)
 * Spring Documentation (https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html)
 *
 */

//@Configuration
//@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
/*

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        return super.userDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider daoAuthenticationProvider =
                new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder){

        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    */
/**
     * Note: view access control is done on the UI side by Vaadin; see the ViewNavigator class
     * @param httpSecurity
     * @throws Exception
     *//*

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                .csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/login").permitAll()
                .anyRequest().authenticated();



       // Cross-Site Request Forgery CSRF
       // CORS (Cross-origin resource sharing)
       */
/* httpSecurity.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/login").permitAll()
               // .antMatchers("/api/**").permitAll()

                //  Descomentar para tener autenticación
               *//*
*/
/* .antMatchers(HttpMethod.GET, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/creditcards/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/bankaccounts/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/transactions/**").permitAll()*//*
*/
/*

                .anyRequest().authenticated();*//*

    }

    */
/**
     * Allows access to static resources, bypassing Spring security.
     *//*

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }

*/

}
