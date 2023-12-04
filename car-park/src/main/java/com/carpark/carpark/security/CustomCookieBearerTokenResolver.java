package com.carpark.carpark.security;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;


@Component
public class CustomCookieBearerTokenResolver  implements BearerTokenResolver{

    @Override
    public String resolve(HttpServletRequest request) {
        System.out.println("request = " + request);
        Cookie[] cookies = request.getCookies();
        System.out.println("cookies = " + cookies);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Bearer".equals(cookie.getName())) {
                    System.out.println("cookie = " + cookie);
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
