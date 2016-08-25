package com.parisofnajd.twitterautoblock;


import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;


@SuppressWarnings("serial")
public class Authorize extends HttpServlet {
	public void doGet(HttpServletRequest req,   HttpServletResponse resp) throws IOException {
		String authurl;
		try {
			authurl = authorize(req);
			resp.sendRedirect(authurl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static  String authorize(HttpServletRequest req) throws Exception{
		Twitter twitter = new TwitterFactory().getInstance();
		RequestToken requestToken = twitter.getOAuthRequestToken();
		HttpSession session = req.getSession();
		session.setAttribute("request_token", requestToken);
		return requestToken.getAuthorizationURL();
		}
}
