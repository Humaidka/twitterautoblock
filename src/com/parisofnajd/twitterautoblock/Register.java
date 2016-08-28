package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;



import java.util.logging.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

  
@SuppressWarnings("serial")
public class Register extends HttpServlet {
	private static final Logger log = Logger.getLogger(Register.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(); 
		Twitter twitter = new TwitterFactory().getInstance();
		RequestToken requestToken = null;
		String oauth_verifier = (String) req.getParameter("oauth_verifier");
		//get request token from session. user should be coming from /authorize
		
		log.info("Twitter Auto Block : "+session.getAttribute("request_token"));
		requestToken = (RequestToken) session.getAttribute("request_token");

		if ((requestToken != null) && (oauth_verifier != null)) {
		
			// Try to get Access Token
			AccessToken accessToken = null;
			try {
				accessToken = twitter.getOAuthAccessToken(requestToken,req.getParameter("oauth_verifier"));
				
			} catch (TwitterException te) {
				resp.getWriter().println("Something went wrong, call your admin");
			}
			if (accessToken != null) {
				log.info("Twitter Auto Block : persisting user info in datastore");
				AccessTokenEntity.persist(accessToken.getUserId(),accessToken.getScreenName(),accessToken.getToken(),accessToken.getTokenSecret());
			}
			resp.sendRedirect("/done.html");
		}
		//The user is not coming from /authorize redirect him (later we can check if he exists in datastore)
		else {
			resp.sendRedirect("/authorize");
		}
	}
}
		  
	
	
	
