package mu.server.config.handler

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            exc: AccessDeniedException) {
        response.sendRedirect(request.contextPath + "/denied")
    }
}