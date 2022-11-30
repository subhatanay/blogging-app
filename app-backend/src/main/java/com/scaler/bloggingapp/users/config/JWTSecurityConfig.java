package com.scaler.bloggingapp.users.config;

import com.scaler.bloggingapp.users.services.JwtAuthenticationFilter;
import com.scaler.bloggingapp.users.services.JwtAuthenticationManager;
import com.scaler.bloggingapp.users.services.JwtService;
import com.scaler.bloggingapp.users.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false, securedEnabled = false, jsr250Enabled = true)
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public JWTSecurityConfig(JwtService jwtService, UserService userService) {
        jwtAuthenticationFilter =  new JwtAuthenticationFilter(new JwtAuthenticationManager(jwtService, userService));
    }



    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users/signup","/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/feed/top**","/articles/*","/users/*/profile/info","/users/*/profile/liked/articles","/users/*/articles","/articles/*/comments").permitAll()
                .antMatchers(HttpMethod.GET, "/api-docs/**","/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("PATCH","GET","POST","PUT","DELETE");
    }
}
