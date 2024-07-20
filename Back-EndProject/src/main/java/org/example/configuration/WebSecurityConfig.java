package org.example.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .csrf(csrf ->csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/**","/login", "/register", "/findId", "/checkDuplicatedId","/findPassword","/changePassword"))
                .authorizeRequests(authz -> authz
                        .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/loginPage")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/registerPage")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/findIdPage")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/findPasswordPage")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/findPassword")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/changePassword")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/fileListPage")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/register")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/findId")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/checkDuplicatedId")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/indext.html")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/css/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/js/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/images/**")).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/fileListPage")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{\"success\":true");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{\"success\":false,\"MESSAGE\":\"로그인 실패\"");
                        }))

                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json");
                            response.getWriter().write("{\"success|':true");
                        }));

        return http.build();
    }
}
