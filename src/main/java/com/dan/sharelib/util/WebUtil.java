package com.dan.sharelib.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

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

    public void doWriteCookie(ServerHttpResponse serverHttpResponse,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly){
        if(StringUtils.isNotEmpty(serverHttpResponse.getHeaders().getFirst(cookieHeader))
                && serverHttpResponse.getHeaders().getFirst(cookieHeader).contains(cookieName)){
            serverHttpResponse.getHeaders()
                    .set(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, false));
        }else{
            serverHttpResponse.getHeaders()
                    .add(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, false));
        }
    }

    public void doWriteCookie(ServerHttpResponse serverHttpResponse,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly,
                              Boolean sameSiteNone) {
        if(StringUtils.isNotEmpty(serverHttpResponse.getHeaders().getFirst(cookieHeader))
                && serverHttpResponse.getHeaders().getFirst(cookieHeader).contains(cookieName)){
            serverHttpResponse.getHeaders()
                    .set(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, sameSiteNone));
        }else{
            serverHttpResponse.getHeaders()
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

    public void doDeleteCookie(ServerHttpResponse serverHttpResponse, Boolean secure, Boolean httpOnly, String cookieName){
        doWriteCookie(serverHttpResponse, cookieName, null, 1, secure, httpOnly);
    }

    public String getCookie(ServerHttpRequest serverHttpRequest,  String cookieName){
        HttpCookie cookie = serverHttpRequest.getCookies().getFirst(cookieName);
        if(ObjectUtils.isNotEmpty(cookie)){
            return Objects.requireNonNull(cookie).getValue();
        }
        return null;
    }

}
