//package com.codegym.spb_eyesclinic_project.config;
//
//import com.example.furnitureweb.service.userService.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.BeanIds;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SpringSecurity extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private IUserService userService;
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(10);
//    }
//
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().ignoringAntMatchers("/**").disable();
//        http.authorizeRequests()
//                .antMatchers(
//                        "/api/**"
//                ).permitAll()
//                .antMatchers(
//                        "/login",
//                        "/logout",
//                        "/register/**",
//                        "/home",
//                        "/shop",
//                        "/about",
//                        "/contact"
//                ).permitAll()
//                .antMatchers(
//                        "/cart",
//                        "/checkout",
//                        "/thank-you"
//                ).hasAnyRole("ADMIN","USER")
//                .antMatchers(
//                        "/admin",
//                        "/products",
//                        "/categories",
//                        "/materials"
//                ).hasRole("ADMIN")
//                .antMatchers(
//                        "/static/**",
//                        "/resources/**",
//                        "/assets/**"
//                ).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
////
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
////                .usernameParameter("username")
////                .passwordParameter("password")
//                .defaultSuccessUrl("/home",true)
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .deleteCookies("JWT")
//                .invalidateHttpSession(true)
//        ;
//
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling().accessDeniedPage("/error/403");
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.cors();
//    }
//}
//
