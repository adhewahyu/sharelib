package com.dan.sharelib.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

@Component
@Slf4j
public class WebUtil {

    private String cookieHeader = "Set-Cookie";
    private String secureCookie = "Secure";
    private String httpOnlyCookie = "HttpOnly";
    private String sameSite = "SameSite";
    private String pathCookie = "Path=/";
    private String maxAgeCookie = "Max-Age";
    private String expires = "Expires=T";

    public void doWriteCookie(ServerWebExchange exchange,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly){
        if(StringUtils.isNotEmpty(exchange.getResponse().getHeaders().getFirst(cookieHeader))
                && exchange.getResponse().getHeaders().getFirst(cookieHeader).contains(cookieName)){
            exchange.getResponse().getHeaders()
                    .set(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, false));
        }else{
            exchange.getResponse().getHeaders()
                    .add(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, false));
        }
    }

    public void doWriteCookie(ServerWebExchange exchange,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly,
                              Boolean sameSiteNone) {
        if(StringUtils.isNotEmpty(exchange.getResponse().getHeaders().getFirst(cookieHeader))
                && exchange.getResponse().getHeaders().getFirst(cookieHeader).contains(cookieName)){
            exchange.getResponse().getHeaders()
                    .set(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, sameSiteNone));
        }else{
            exchange.getResponse().getHeaders()
                    .add(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, sameSiteNone));
        }
    }

    private String getCookieString(String cookieName, String cookieValue,
                                   Integer expiry,
                                   Boolean secure,
                                   Boolean httpOnly,
                                   Boolean sameSiteNone){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cookieName).append("=").append(cookieValue).append(";");
        stringBuilder.append(" ").append(pathCookie).append(";");
        if(sameSiteNone){
            stringBuilder.append(" ").append(sameSite).append("=").append("None").append(";");
        }
        if(secure || sameSiteNone){
            stringBuilder.append(" ").append(secureCookie).append(";");
        }
        if(httpOnly){
            stringBuilder.append(" ").append(httpOnlyCookie).append(";");
        }
        if(ObjectUtils.isNotEmpty(expiry)){
            stringBuilder.append(" ").append(maxAgeCookie).append("=").append(expiry).append(";");
            stringBuilder.append(" ").append(expires).append(";");
        }
        log.info("Set Cookie Value = {}", stringBuilder);
        return stringBuilder.toString();
    }

    public void doDeleteCookie( ServerWebExchange exchange, Boolean secure, Boolean httpOnly, String cookieName){
        if(StringUtils.isNotEmpty(exchange.getResponse().getHeaders().getFirst(cookieHeader))
                && exchange.getResponse().getHeaders().getFirst(cookieHeader).contains(cookieName)){
            exchange.getResponse().getHeaders()
                    .set(cookieHeader, getCookieString(cookieName, null, 0, secure, httpOnly, false));
        }
    }

    public String getCookie( ServerWebExchange exchange,  String cookieName){
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst(cookieName);
        if(ObjectUtils.isNotEmpty(cookie)){
            return Objects.requireNonNull(cookie).getValue();
        }
        return null;
    }

}
