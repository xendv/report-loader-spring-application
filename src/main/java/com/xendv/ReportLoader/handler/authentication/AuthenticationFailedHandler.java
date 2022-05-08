package com.xendv.ReportLoader.handler.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFailedHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Access denied: authentication not passed\n");
        response.getWriter().write("Credentials:\n");
        final String authorization = request.getHeader("Authorization");
       /* String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);*/
        // credentials = username:password
        //final String[] values = credentials.split(":", 2);
        response.getWriter().write("User: + " + request.getRemoteUser() + "\n");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
