package it.flowing.workshop.filters;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authentication = httpServletRequest.getHeader("Authentication");

        if (Strings.isNullOrEmpty(authentication)) {
            httpServletResponse.reset();
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if (!authentication.equals("DUMMY_TOKEN")) {
            httpServletResponse.reset();
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
