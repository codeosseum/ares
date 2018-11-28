package com.codeosseum.ares.config.web;

import com.codeosseum.ares.config.web.Endpoints.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        this.configureRequestAccess(http)
            .configureLogin(http)
            .configureLogout(http);
    }

    private WebSecurityConfig configureRequestAccess(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(Paths.HOME, Paths.LOGIN)
                .permitAll()
            .antMatchers(Paths.Game.HOME)
                .authenticated();

        return this;
    }

    private WebSecurityConfig configureLogin(final HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage(Paths.LOGIN)
            .defaultSuccessUrl(Paths.Game.HOME)
            .failureUrl(Paths.LOGIN_ERROR)
            .permitAll();

        return this;
    }

    private WebSecurityConfig configureLogout(final HttpSecurity http) throws Exception {
        http.logout()
            .logoutSuccessUrl(Paths.LOGOUT_SUCCESS)
            .permitAll();

        return this;
    }
}
