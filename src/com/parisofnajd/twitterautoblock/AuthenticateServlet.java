package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class AuthenticateServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,   HttpServletResponse resp) throws IOException {
					String authurl;
					try {
						authurl = AuthenticateServlet.Authenticate(req);
						resp.sendRedirect(authurl);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	
	private static  String Authenticate(HttpServletRequest req) throws Exception{
		//ConfigurationBuilder cb = new ConfigurationBuilder();
		//Configuration cfg = cb.build();
		//TwitterFactory tf = new TwitterFactory(cfg);
		Twitter twitter = new TwitterFactory().getInstance();
		RequestToken requestToken = twitter.getOAuthRequestToken();
			HttpSession session = req.getSession();
			session.setAttribute("request_token", requestToken);
		    return requestToken.getAuthorizationURL();
		  }

	
	
	
}
