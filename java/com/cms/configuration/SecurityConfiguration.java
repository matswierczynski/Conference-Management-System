package com.cms.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.cms.configuration" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    // for querying all users from db, query in application.properties
    @Value("${spring.queries.users-query}")
    private java.lang.String usersQuery;

    // for querying corresponding users' roles, query in application.properties
    @Value("${spring.queries.roles-query}")
    private java.lang.String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/home").permitAll().antMatchers("/login")
                .permitAll().antMatchers("/registration").permitAll().antMatchers("/schedule")
                .hasAnyAuthority("PARTICIPANT", "SCIENTIST", "ORGANIZER", "REVIEWER").antMatchers("/home-participant")
                .hasAuthority("PARTICIPANT").antMatchers("/home-organizer", "/assign/*").hasAuthority("ORGANIZER")
                .antMatchers("/user/upload", "/user/files/**").hasAuthority("SCIENTIST").anyRequest().authenticated()
                .and().csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home").usernameParameter("email").passwordParameter("password").and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");

    }
}
