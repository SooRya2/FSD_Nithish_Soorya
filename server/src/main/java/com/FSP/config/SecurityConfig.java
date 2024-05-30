
package com.FSP.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.FSP.Jwt.JwtRequestFilter;
import com.FSP.Jwt.JwtTokenProvider;
import com.FSP.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter();
        jwtRequestFilter.setJwtTokenProvider(jwtTokenProvider);
        jwtRequestFilter.setUserDetailsService(userDetailsServiceImpl);
        return jwtRequestFilter;
    }
	
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

 
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	 http.csrf(csrf -> csrf.disable())
         .authorizeHttpRequests(authorizeRequests ->
             authorizeRequests
                 .requestMatchers("/sso/**").permitAll()
                 .requestMatchers("/pass/**").authenticated()
         )
         .sessionManagement(sessionManagement ->
             sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         )
         .authenticationProvider(authenticationProvider())
         .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
         .oauth2Login(oauth2 -> oauth2
             .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
             .successHandler(oAuth2AuthenticationSuccessHandler())
         );

     return http.build();
        		
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(jwtTokenProvider());
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
}
}