package mu.server.config;

import mu.server.config.handler.CustomAccessDeniedHandler
import mu.server.config.handler.CustomAuthenticationSuccessHandler
import mu.server.config.handler.CustomLoginFailureHandler
import mu.server.config.handler.CustomLogoutSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@Configuration
class BeanConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jsonView(): MappingJackson2JsonView {
        return MappingJackson2JsonView()
    }

    @Bean
    fun loginFailureHandler(): AuthenticationFailureHandler {
        return CustomLoginFailureHandler()
    }

    @Bean
    fun loginSuccessHandler(): AuthenticationSuccessHandler {
        return CustomAuthenticationSuccessHandler("/")
    }

    @Bean
    fun logoutSuccessHandler(): LogoutSuccessHandler? {
        return CustomLogoutSuccessHandler()
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler? {
        return CustomAccessDeniedHandler()
    }


}