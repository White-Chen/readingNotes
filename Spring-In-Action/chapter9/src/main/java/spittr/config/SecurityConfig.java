package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * \* Created with Chen Zhe on 1/22/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig
        extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER","ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login")
            .and()
                .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("chenzhe")
            .and()
                .logout()
                .logoutSuccessUrl("/")
            .and()
                .httpBasic()
                .realmName("Spittr")
            .and()
                .authorizeRequests()
                .antMatchers("/")
                    .authenticated()
                .antMatchers("/spitter/me")
                    .authenticated()
                .antMatchers(HttpMethod.POST, "/spittles")
                    .authenticated()
                .anyRequest()
                    .permitAll();
    }
}
