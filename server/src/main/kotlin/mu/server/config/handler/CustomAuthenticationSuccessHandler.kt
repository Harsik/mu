package mu.server.config.handler

import mu.server.service.AccountService
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationSuccessHandler(defaultTargetUrl: String) : SavedRequestAwareAuthenticationSuccessHandler() {

    private var accountService: AccountService? = null

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val principal = authentication.principal as User
        accountService?.setAccountLastLogin(principal.username)
        super.onAuthenticationSuccess(request, response, authentication)
    }

    init {
        setDefaultTargetUrl(defaultTargetUrl)
    }
}