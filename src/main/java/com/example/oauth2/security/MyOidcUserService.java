package com.example.oauth2.security;

import java.sql.Timestamp;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import com.example.oauth2.dao.OAuth2MemberDao;
import com.example.oauth2.model.OAuth2Member;

// 自訂 OidcUserService (Oauth2.0 + OpenID Connect) 社交登入服務 ex.Google
@Component
public class MyOidcUserService extends OidcUserService {

        @Autowired
        private OAuth2MemberDao oAuth2MemberDao;

        @Override
        public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {

            OidcUser oidcUser = super.loadUser(oidcUserRequest);

            // 取得使用者的 email 和 name
            String email = Objects.toString(oidcUser.getAttributes().get("email"), null);
            String name = Objects.toString(oidcUser.getAttributes().get("name"), null);

            // 取得 provider 和 providerId
            String provider = oidcUserRequest.getClientRegistration().getRegistrationId();
            String providerId = Objects.toString(oidcUser.getAttributes().get("sub"), null);

            // 取得 accessToken 和 expiresAt
            String accessToken = oidcUserRequest.getAccessToken().getTokenValue();
            Timestamp expiresAt = Timestamp.from(oidcUserRequest.getAccessToken().getExpiresAt());

            // 檢查資料庫中是否已經有這個使用者
            OAuth2Member oAuth2Member = oAuth2MemberDao.getOAuth2Member(provider, providerId);
            // 如果沒有，則新增這個使用者
            if (oAuth2Member == null) {
                OAuth2Member newOAuth2Member = new OAuth2Member();
                newOAuth2Member.setProvider(provider);
                newOAuth2Member.setProviderId(providerId);
                newOAuth2Member.setName(name);
                newOAuth2Member.setEmail(email);
                newOAuth2Member.setAccessToken(accessToken);
                newOAuth2Member.setExpiresAt(expiresAt);
                
                oAuth2MemberDao.createOAuth2Member(newOAuth2Member);
            }

            return oidcUser;

        }
}
