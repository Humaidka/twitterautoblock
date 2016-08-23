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

  
@SuppressWarnings("serial")
public class AutoBlockFromDatastoreServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AutoBlockFromDatastoreServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String at=AccessTokenEntity.fetchAccessToken(32878394);
		String ats=AccessTokenEntity.fetchAccessTokenSecret(32878394);
		log.info("Twitter Auto Block access token for userid "+32878394+" is "+at);
		log.info("Twitter Auto Block access token secret for userid "+32878394+" is "+ats);
		Twitter twitter = new TwitterFactory().getInstance();
		AccessToken accessToken= new AccessToken(at,ats);
		twitter = new TwitterFactory().getInstance(accessToken);
		log.info("Twitter Auto Block: "+accessToken.getScreenName());
		log.info("Twitter Auto Block: "+accessToken.getToken());
		log.info("Twitter Auto Block: "+accessToken.getTokenSecret());
		log.info("Twitter Auto Block: "+accessToken.getUserId());
		Query query = new Query();
		query.count(100);
		query.query("%40GaryLineker");
		try {
			QueryResult result = twitter.search(query);
			for (Status status : result.getTweets()) {
				String InReplyToScreenName = status.getInReplyToScreenName();
				//log.info("Twitter Auto Block: "+status.getInReplyToScreenName());
				if (InReplyToScreenName!= null && InReplyToScreenName.equals("GaryLineker")){
					//log.info("Twitter Auto Block: "+status.getInReplyToScreenName());
					resp.getWriter().println("@" + status.getUser().getScreenName() + ":" + status.getText());
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
	  



