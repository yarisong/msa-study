package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    Environment env;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().antMatchers("/**")
                .permitAll()
//                .hasIpAddress(env.getProperty("gateway.ip"))
//                .access("hasIpAddress('192.168.123.107') or hasIpAddress('127.0.0.1') or " +
//                        "hasIpAddress('10.34.102.59') or hasIpAddress('10.50.2.175') or hasIpAddress('210.122.105.5') or hasIpAddress('192.168.123.147')")
////                .hasIpAddress("10.50.2.175")
//                .hasIpAddress("192.168.123.103")
                .and()
                .addFilter(getAuthenticationFilter());

        //html frame 나뉘어진 것을 허용
        http.headers().frameOptions().disable();
        //  super.configure(http);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(), userService, env);
//        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
