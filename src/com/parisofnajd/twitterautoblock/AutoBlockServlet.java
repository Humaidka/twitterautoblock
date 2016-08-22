package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;
import java.util.logging.Logger;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


@SuppressWarnings("serial")
public class AutoBlockServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AutoBlockServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(); 
		RequestToken requestToken = (RequestToken) session.getAttribute("request_token");
		AccessToken accessToken = (AccessToken) session.getAttribute("access_token");
		if (requestToken == null && accessToken == null){
			log.info("Twitter Auto Block: both access_token and request_token are null, redirecting to authentication servlet");
			resp.sendRedirect("/authenticate");
		}
		else {
			Twitter twitter = new TwitterFactory().getInstance();
			if ( accessToken == null) {
				log.info("Twitter Auto Block: access_token is null, creating twitter object from request token");
				try {
					accessToken = twitter.getOAuthAccessToken(requestToken,req.getParameter("oauth_verifier"));
					session.setAttribute("access_token", accessToken);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
			else {
				log.info("Twitter Auto Block: access_token is not null, creating twitter object from session");
				twitter = new TwitterFactory().getInstance(accessToken);
			}
			Query query = new Query();
			query.count(100);
			query.query("%40GaryLineker");
			try {
				QueryResult result = twitter.search(query);
				for (Status status : result.getTweets()) {
					String InReplyToScreenName = status.getInReplyToScreenName();
					log.info("Twitter Auto Block: "+status.getInReplyToScreenName());
					if (InReplyToScreenName!= null && InReplyToScreenName.equals("GaryLineker")){
						log.info("Twitter Auto Block: "+status.getInReplyToScreenName());
						resp.getWriter().println("@" + status.getUser().getScreenName() + ":" + status.getText());
					}
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}
}
		  
	
	
	
