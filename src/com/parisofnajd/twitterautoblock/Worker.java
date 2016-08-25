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
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userid = req.getParameter("userid");
		log.info("Twitter Auto Block: "+userid);

		String at=AccessTokenEntity.fetchAccessToken(Long.getLong(userid));
		String ats=AccessTokenEntity.fetchAccessTokenSecret(Long.getLong(userid));
		Twitter twitter = new TwitterFactory().getInstance();
		AccessToken accessToken= new AccessToken(at,ats);
		twitter = new TwitterFactory().getInstance(accessToken);
		Query query = new Query();
		query.count(100);
		query.query(accessToken.getScreenName());
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