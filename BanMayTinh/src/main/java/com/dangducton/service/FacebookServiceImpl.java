package com.dangducton.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookServiceImpl implements FacebookService {

	@Value("${spring.social.facebook.appId}")
	private String facebookId;
	@Value("${spring.social.facebook.appSecret}")
	private String facebookSecret;

	private FacebookConnectionFactory createFacebookConnection() {
		return new FacebookConnectionFactory(facebookId, facebookSecret);
	}

	@Override
	public String facebookLogin() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri("http://localhost:8080/facebook");
		parameters.setScope("public_profile,email");
		return createFacebookConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
	}

	@Override
	public String getFacebookAccessToken(String code) {
		// TODO Auto-generated method stub
		return createFacebookConnection().getOAuthOperations()
				.exchangeForAccess(code, "http://localhost:8080/facebook", null).getAccessToken();
	}

	@Override
	public User getFacebookUserProfile(String accessToken) {
		// TODO Auto-generated method stub
		Facebook facebook = new FacebookTemplate(accessToken);
		String[] fields = { "id", "first_name", "last_name", "cover", "email" };
		return facebook.fetchObject("me", User.class, fields);
	}

}