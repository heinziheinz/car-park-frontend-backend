package com.carpark.carpark.security;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


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
                    System.out.println("cookie.getName() = " + cookie.getName());
                    System.out.println("cookie.getValue() = " + cookie.getValue());
                    System.out.println("cookie.getDomain() = " + cookie.getDomain());
                    System.out.println("cookie.getPath() = " + cookie.getPath());
                    System.out.println("cookie.getMaxAge() = " + cookie.getMaxAge());
                    System.out.println("cookie.getSecure() = " + cookie.getSecure());
                    System.out.println("cookie.isHttpOnly() = " + cookie.isHttpOnly());
                    System.out.println("cookie.getComment() = " + cookie.getComment());
                    System.out.println("cookie.getVersion() = " + cookie.getVersion());
                    
                    String decodedValue = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    System.out.println("decodedValue = " + decodedValue);

                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
