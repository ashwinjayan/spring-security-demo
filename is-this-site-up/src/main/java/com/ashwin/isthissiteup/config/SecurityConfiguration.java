package com.ashwin.isthissiteup.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set your configuration on the auth obj with in memory authentication
        /*
         * Userd for in memory authentication that takes in users, passwords and roles
         * as seen below
         * auth.inMemoryAuthentication().withUser("username").password("password")
         * .roles("USER").and().withUser("foo") .password("password").roles("ADMIN");
         */

        // Set your configuration on the auth obj with h2 database (inbuilt)
        // must use the user schema used by spring for users and authorities
        auth.jdbcAuthentication().dataSource(dataSource)
                // if it is not the default schema
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username = ?");

        // giving data to the default schema to add data inline to db
        /*
         * .withDefaultSchema()
         * .withUser(User.withUsername("user").password("password").roles("USER"))
         * .withUser(User.withUsername("admin").password("password").roles("ADMIN"));
         */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN").antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/check").permitAll().and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // insecure encoder for learning purposes only
        return NoOpPasswordEncoder.getInstance();
    }

}
