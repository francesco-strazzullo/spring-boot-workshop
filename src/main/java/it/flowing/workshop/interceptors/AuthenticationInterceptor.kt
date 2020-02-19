package it.flowing.workshop.interceptors

import com.google.common.base.Strings
import it.flowing.workshop.controllers.SkipAuthentication
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor : HandlerInterceptorAdapter() {
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        if (handler is HandlerMethod) {
            val methodAnnotation = handler.getMethodAnnotation(SkipAuthentication::class.java)
            if (methodAnnotation != null) {
                return true
            }
            val classAnnotation = handler.beanType.getAnnotation(SkipAuthentication::class.java)
            if (classAnnotation != null) {
                return true
            }
        }

        val authentication = request.getHeader("Authentication")

        if (Strings.isNullOrEmpty(authentication)) {
            response.reset()
            response.sendError(HttpStatus.UNAUTHORIZED.value())
            return false
        }

        if (authentication != "DUMMY_TOKEN") {
            response.reset()
            response.sendError(HttpStatus.FORBIDDEN.value())
            return false
        }
        
        return true
    }
}