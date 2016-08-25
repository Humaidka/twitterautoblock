package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.util.logging.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

  
@SuppressWarnings("serial")
public class Register extends HttpServlet {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(Register.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(); 
		Twitter twitter = new TwitterFactory().getInstance();
		
		//get request token from session. user should be coming from /authorize
		RequestToken requestToken = (RequestToken) session.getAttribute("request_token");
		
		// Try to get Access Token
		AccessToken accessToken = null;
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken,req.getParameter("oauth_verifier"));
			if (accessToken != null) {
				AccessTokenEntity.persist(accessToken.getUserId(),accessToken.getScreenName(),accessToken.getToken(),accessToken.getTokenSecret());
				enqueue((Long)accessToken.getUserId());
			}
			resp.sendRedirect("/done.html");
			
		} catch (TwitterException te) {
			
			if (requestToken == null && accessToken == null){
				resp.sendRedirect("/authorize");
			}
			else {
				resp.getWriter().println("Could not get Access Token");
			}
		}
	}
	
	private static void enqueue(Long userid) {
		
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/worker").param("userid", userid.toString()));
	}
}
		  
	
	
	
