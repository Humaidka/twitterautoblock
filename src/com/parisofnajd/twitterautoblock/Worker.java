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
		String at=AccessTokenEntity.fetchAccessToken(Long.parseLong(userid));
		String ats=AccessTokenEntity.fetchAccessTokenSecret(Long.parseLong(userid));
		AccessToken accessToken= new AccessToken(at, ats, Long.parseLong(userid));
		Twitter twitter = new TwitterFactory().getInstance(accessToken);
		try {
			screenname = twitter.getScreenName();
			log.info("Twitter Auto Block: "+twitter.getScreenName());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query = new Query();
		query.count(100);
		query.query(screenname);
		try {
			QueryResult result = twitter.search(query);
			//log.info("Twitter Auto Block: "+result.toString());
			for (Status status : result.getTweets()) {
				String InReplyToScreenName = status.getInReplyToScreenName();
				if (InReplyToScreenName != null && InReplyToScreenName.equals(screenname)){
					log.info("Twitter Auto Block: "+screenname+" would block "+status.getUser().getScreenName()+" because he said "+status.getText());
					if (twitter.createBlock(status.getUser().getId()).getId() == status.getUser().getId()){ ;
						OffenderEntity.persist(status.getUser().getId(), status.getUser().getScreenName(), status.getText(), true);
					}
				}
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}