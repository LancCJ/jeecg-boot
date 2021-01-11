package com.smartlms.flowable.config;

import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.cfg.StandaloneIdmEngineConfiguration;
import org.flowable.idm.spring.SpringIdmEngineConfiguration;
import org.flowable.idm.spring.authentication.SpringEncoder;
import org.flowable.ui.common.security.ClearFlowableCookieLogoutHandler;
import org.flowable.ui.common.security.DefaultPrivileges;
import org.flowable.ui.idm.properties.FlowableIdmAppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;


/**
 * TODO
 *
 * @author chenjian
 * @version 1.0
 * @date 2021/1/5 7:50 下午
 */
@Configuration
public class IdmEngineConfig extends SpringIdmEngineConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdmEngineConfig.class);

    @Bean
    public PasswordEncoder bCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringEncoder passwordEncoder(){
        return new SpringEncoder(bCryptEncoder());
    }

    @Override
    public IdmEngineConfiguration setPasswordEncoder(org.flowable.idm.api.PasswordEncoder passwordEncoder) {
        return super.setPasswordEncoder(passwordEncoder());
    }

//    @Configuration
//    @Order(10) // API config first (has Order(1))
//    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private FlowableIdmAppProperties idmAppProperties;
//
//        @Autowired
//        private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
//
//        @Autowired
//        private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;
//
//        @Autowired
//        private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;
//
//        @Autowired
//        private Http401UnauthorizedEntryPoint authenticationEntryPoint;
//
//        @Autowired
//        private RememberMeServices rememberMeServices;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .exceptionHandling()
//                    .authenticationEntryPoint(authenticationEntryPoint)
//                    .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .rememberMe()
//                    .rememberMeServices(rememberMeServices)
//                    .key(idmAppProperties.getSecurity().getRememberMeKey())
//                    .and()
//                    .logout()
//                    .logoutUrl("/app/logout")
//                    .logoutSuccessHandler(ajaxLogoutSuccessHandler)
//                    .addLogoutHandler(new ClearFlowableCookieLogoutHandler())
//                    .permitAll()
//                    .and()
//                    .csrf()
//                    .disable() // Disabled, cause enabling it will cause sessions
//                    .headers()
//                    .frameOptions()
//                    .sameOrigin()
//                    .addHeaderWriter(new XXssProtectionHeaderWriter())
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/*").permitAll()
//                    .antMatchers("/app/rest/authenticate").permitAll()
//                    .antMatchers("/app/**").hasAuthority(DefaultPrivileges.ACCESS_IDM);
//
//            // Custom login form configurer to allow for non-standard HTTP-methods (eg. LOCK)
//            CustomFormLoginConfig<HttpSecurity> loginConfig = new CustomFormLoginConfig<>();
//            loginConfig.loginProcessingUrl("/app/authentication")
//                    .successHandler(ajaxAuthenticationSuccessHandler)
//                    .failureHandler(ajaxAuthenticationFailureHandler)
//                    .usernameParameter("j_username")
//                    .passwordParameter("j_password")
//                    .permitAll();
//
//            http.apply(loginConfig);
//        }
//
//    }
}
