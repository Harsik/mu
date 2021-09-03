package mu.server.config.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomLogoutSuccessHandler : LogoutSuccessHandler {
    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse,
                                 authentication: Authentication) {
        if (authentication.details != null) {
            try {
                request.session.invalidate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        response.status = HttpServletResponse.SC_OK
        response.sendRedirect("/user/login")
    }
}