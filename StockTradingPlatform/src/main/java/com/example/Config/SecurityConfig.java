package com.example.Config;

import com.example.services.servicesImpl.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

//Spring Security operates as a chain of filters (interceptors) that sit in front of your controllers.
@Configuration
@EnableWebSecurity          //this says don't go with default flow go with my flow
public class SecurityConfig {

    @Autowired
    private UserDetailService userDetailService;


    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        //you are using authorizeHttpRequests() to establish a baseline rule that says:
        // "Nobody passes past this point on the network layer unless they are authenticated."
        httpSecurity.authorizeHttpRequests(request->request
                        .requestMatchers("/user/register","/user/login").permitAll()
                        .requestMatchers("/stock/add","/stock/delete/{id}","/stock/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/user/delete/{id}","/user/get/all",
                                "/user/update/roleToAdmin/{userId}").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()    //Requires authentication
                );

       httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        httpSecurity.authenticationProvider(authenticationProvider());
        //enabling for post man for rest access otherwise it returns form login html code
        httpSecurity.httpBasic(Customizer.withDefaults())
                .exceptionHandling(exception ->exception.authenticationEntryPoint(
                        (request, response, authexception) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Unauthorized access - Token missing or invalid or you are accessing admin api.\"}");
                        }
                )
                );

        ////making api as stateless means every request will be a new request for server
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //cross side request forgery
        //not required if you are using jwt authentication
        httpSecurity.csrf((Customizer)->Customizer.disable());
        return httpSecurity.build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider(){
            //implementation of authenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;

    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized access - Token missing or invalid or you are accessing admin api.\"}");
        };
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity){
        // Retrieve the shared AuthenticationManager instance built by Spring
        AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProvider());
        return builder.build();
    }


    /*
    1. Defining the CORS Blueprint -CorsConfiguration config = new CorsConfiguration();

    2.setAllowedOrigins -Explicitly permits your frontend application running on http://localhost:4200
    (the default port for Angular) to make requests to this backend.

    3.setAllowedMethods-Why OPTIONS matters: Browsers automatically send a pre-flight OPTIONS request
    before complex requests (like POST or PUT) to check if the server will accept the actual request.
    Including OPTIONS here ensures this handshake succeeds.

    4.setAllowedHeaders - The wildcard * means the backend will accept any HTTP headers sent by the frontend
    (e.g., Content-Type, Authorization, Accept).

    5.setAllowCredentials-• Essential for security. It allows the browser to automatically include cookies,
    authorization headers (like basic auth), or TLS client certificates in cross-origin requests.
    •  When setAllowCredentials(true) is enabled, you cannot use a wildcard * for setAllowedOrigins.
    You must explicitly list the domain names (like you did with localhost:4200).

    6.• UrlBasedCorsConfigurationSource is a class that applies your CORS rules to specific URL paths.
• The "/**" pattern means apply these rules globally to every single endpoint and controller in your application.

     */


    //cors configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("POST","GET","PUT","DELETE","OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }




}
