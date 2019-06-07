package com.findMe.config;


import com.findMe.model.enums.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private String QUERY = "SELECT u.email, r.role FROM USER_TABLE u " +
            "JOIN USER_HAS_ROLE uhr ON u.id = uhr.user_id " +
            "JOIN ROLE r ON uhr.role_id = r.id where u.email = ?";
    private  String QUERY2 = "SELECT u.email, u.password FROM USER_TABLE u WHERE u.email = ?";


    @Autowired
    private DriverManagerDataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                //.usersByUsernameQuery(QUERY2)
                .authoritiesByUsernameQuery(QUERY)

        ;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/","/login","/user-registration","/user/*").permitAll()
                .antMatchers("/remove-post/*").hasAnyAuthority(RoleName.ADMIN.toString(),RoleName.SUPER_ADMIN.toString())
                .antMatchers("/user-update").hasAuthority(RoleName.SUPER_ADMIN.toString())
                .anyRequest().authenticated();

        ;

    }
}
