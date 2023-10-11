package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig{
    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService myUserDetails;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

    // @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// 	// configure AuthenticationManager so that it knows from where to load
	// 	// user for matching credentials
	// 	// Use BCryptPasswordEncoder
	// 	auth.userDetailsService(myUserDetails).passwordEncoder(passwordEncoder());
	// }

    //authentication
    @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
     }

    //authorization
    //Staff -> Department
    //Admin -> Region
    //Non Login -> login, register, forgot passwort
    //Login -> change password
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests((auth) -> {
            try {
                auth
                    // .antMatchers("/authenticate").permitAll()
                    .antMatchers("/account/login").permitAll()
                    .antMatchers("/account/register").permitAll()
                    .antMatchers("api/**").authenticated()
                    .antMatchers("/account/authenticate").permitAll()
                    .antMatchers("/account/change").authenticated()
                    .antMatchers("/department/**").hasAuthority("Staff")
                    .antMatchers("/region/**").hasAuthority("Admin")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/account/login")
                    .and()
                    .httpBasic()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    // .logout();
                     httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
            } catch (Exception e) {
                // TODO: handle exception
                throw new RuntimeException(e);
            }
        });
        return httpSecurity.build();
    }

    //encrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
