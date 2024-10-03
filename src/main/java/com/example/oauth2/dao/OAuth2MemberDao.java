package com.example.oauth2.dao;

import com.example.oauth2.model.OAuth2Member;

public interface OAuth2MemberDao {

    OAuth2Member getOAuth2Member(String provider, String providerId);

    Integer createOAuth2Member(OAuth2Member OAuth2Member);
}
