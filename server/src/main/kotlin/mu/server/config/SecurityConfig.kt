package mu.server.config;

import mu.server.service.AccountService
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity
class SecurityConfig(
        private val accountService: AccountService,
        private val passwordEncoder: PasswordEncoder,
        private val loginSuccessHandler: AuthenticationSuccessHandler,
        private val loginFailureHandler: AuthenticationFailureHandler,
        private val logoutSuccessHandler: LogoutSuccessHandler,
        private val accessDeniedHandler: AccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .userDetailsService(accountService)
                .passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http
//                .csrf().disable()
                .authorizeRequests()

                /*페이지 권한 설정*/
//                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('MEMBER')")
                .antMatchers("/").access("hasRole('ADMIN') or hasRole('USER')")
                .antMatchers("/post/**").access("hasRole('ADMIN') or hasRole('USER')")
                .antMatchers("/**").permitAll()
                .and()

                /* 로그인 설정*/
                .formLogin()
                .loginPage("/user/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                .and()

                /*로그아웃 설정*/
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/user/logout"))
//                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()

                /*403 예외처리 핸들링*/
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
//              .accessDeniedPage("/denied")
        ;
    }
}