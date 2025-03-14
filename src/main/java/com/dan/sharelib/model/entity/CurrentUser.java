package com.dan.sharelib.model.entity;

import com.dan.sharelib.util.Constants;
import io.jsonwebtoken.Claims;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser {

    private String username;
    private String role;
    private String mobile;
    private String email;

    public CurrentUser(Claims claims) {
        this.username = claims.get(Constants.FLD_USERNAME).toString();
        this.role = claims.get(Constants.FLD_ROLE).toString();
        this.mobile = claims.get(Constants.FLD_MOBILE).toString();
        this.email = claims.get(Constants.FLD_EMAIL).toString();
    }

    public Map<String, Object> toClaims() {
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put(Constants.FLD_USERNAME, username);
        claims.put(Constants.FLD_ROLE, role);
        claims.put(Constants.FLD_MOBILE, mobile);
        claims.put(Constants.FLD_EMAIL, email);
        return claims;
    }
}
