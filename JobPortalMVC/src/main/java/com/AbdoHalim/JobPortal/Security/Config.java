package com.AbdoHalim.JobPortal.Security;

import com.AbdoHalim.JobPortal.Service.implementaion.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class Config implements  WebMvcConfigurer{

    @Value("${file.path}") // Specify the directory where files are uploaded
    private String uploadDir;

    public final UserDetailsService userDetailsService;
    public Config(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationProvider authentication(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable());
        http.csrf(c->c.disable());
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/public/**").permitAll();
            auth.requestMatchers("/applicant/**").hasAuthority("APPLICANT");
            auth.requestMatchers("/company/**").hasAuthority("COMPANY");
            auth.requestMatchers("/account/**").hasAnyAuthority("APPLICANT","COMPANY");
            auth.anyRequest().permitAll();
        });
        http.formLogin(Customizer.withDefaults());
        http.formLogin(fl -> {
            fl.loginPage("/public/login");
            fl.defaultSuccessUrl("/public/home");
            fl.failureUrl("/public/login");
        });
        http.logout(log -> {
            log.logoutUrl("/public/logout");
            log.logoutSuccessUrl("/public/login?logout=true");
        });
        return http.build();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Resume/**")
                .addResourceLocations("file:Resume/")
                .setCacheControl(CacheControl.noCache());
    }

}
