package it.flowing.workshop.interceptors;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authentication = request.getHeader("Authentication");

        if (Strings.isNullOrEmpty(authentication)) {
            response.reset();
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        if (!authentication.equals("DUMMY_TOKEN")) {
            response.reset();
            response.sendError(HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }
}
