package com.codeosseum.ares.security.config;

import com.codeosseum.ares.web.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.boot.autoconfigure.security.StaticResourceLocation.WEB_JARS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        this.configureRequestAccess(http)
            .configureLogin(http)
            .configureLogout(http);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    private WebSecurityConfig configureRequestAccess(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations().excluding(WEB_JARS))
                .permitAll()
            .antMatchers(Paths.HOME, Paths.LOGIN, Paths.REGISTRATION)
                .permitAll()
            .antMatchers(Paths.Api.REGISTRATION, "/api/event")
                .permitAll()
            .antMatchers(Paths.Game.HOME, Paths.Game.MATCHMAKING, Paths.Api.MATCHMAKING, Paths.Api.MATCHMAKING_SUBPATHS)
                .authenticated()
            .and()
                .csrf().disable();

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
