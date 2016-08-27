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
public class Worker extends HttpServlet {
	private static final Logger log = Logger.getLogger(Worker.class.getName());
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userid = req.getParameter("userid");
		String screenname = null;
		log.info("Twitter Auto Block: "+Long.parseLong(userid));
		String at=AccessTokenEntity.fetchAccessToken(Long.parseLong(userid));
		String ats=AccessTokenEntity.fetchAccessTokenSecret(Long.parseLong(userid));
		log.info("Twitter Auto Block: "+at);
		log.info("Twitter Auto Block: "+ats);
		AccessToken accessToken= new AccessToken(at, ats, Long.parseLong(userid));
		Twitter twitter = new TwitterFactory().getInstance(accessToken);
		log.info("Twitter Auto Block: "+accessToken.toString());
		try {
			screenname = twitter.getScreenName();
			log.info("Twitter Auto Block: "+twitter.getScreenName());
		} catch (IllegalStateException | TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Query query = new Query();
		query.count(100);
		log.info("Twitter Auto Block: "+accessToken.getScreenName());
		query.query(screenname);
		try {
			QueryResult result = twitter.search(query);
			//log.info("Twitter Auto Block: "+result.toString());
			for (Status status : result.getTweets()) {
				String InReplyToScreenName = status.getInReplyToScreenName();
				//log.info("Twitter Auto Block: "+status.getInReplyToScreenName());
				if (InReplyToScreenName!= null && InReplyToScreenName.equals("GaryLineker")){
					log.info("Twitter Auto Block: "+status.getText());
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}