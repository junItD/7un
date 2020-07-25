package top.i7un.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import top.i7un.springboot.service.security.CustomUserServiceImpl;
import top.i7un.springboot.utils.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author:  Noone
 * @Date: 2018/6/5 18:45
 * Describe: SpringSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserServiceImpl();
    }

    @Autowired
    private CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService())
            //启动MD5加密
            .passwordEncoder(new PasswordEncoder() {
                MD5Util md5Util = new MD5Util();
                @Override
                public String encode(CharSequence rawPassword) {
                    return md5Util.encode((String) rawPassword);
                }

                @Override
                public boolean matches(CharSequence rawPassword, String encodedPassword) {
                    return encodedPassword.equals(md5Util.encode((String)rawPassword));
                }
            });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //放行无需验证的url
            .authorizeRequests()
            .antMatchers("/","/index","/aboutme","/archives","/categories","/friendlylink","/tags","/update","/getWorkRecordById","/myleavemessage")
                .permitAll()

                //配置各个角色可以用的url
                .antMatchers("/user").hasAnyRole("USER","SUPERADMIN")
//                .antMatchers("/editor","/ali","/mylove").hasAnyRole("ADMIN")
                .antMatchers("/ali","/editor","/superadmin","/myheart","/today","/yesterday","/mylove","/mystory").hasAnyRole("SUPERADMIN")
//"/ali","/editor","/superadmin","/myheart","/today","/yesterday","/mylove","/user",

                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/")
//                .successHandler(customSavedRequestAwareAuthenticationSuccessHandler)

                .and()
                .headers().frameOptions().sameOrigin()
                //登出
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");

        http.csrf().disable();
    }
}
