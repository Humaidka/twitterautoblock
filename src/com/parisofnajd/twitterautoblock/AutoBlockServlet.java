package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;
import java.util.logging.Logger;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


@SuppressWarnings("serial")
public class AutoBlockServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AutoBlockServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 Twitter twitter = new TwitterFactory().getInstance();
		 HttpSession session = req.getSession();
		 AccessToken accessToken = null;
		 if ( session.getAttribute("access_token") == null)
			{
			 log.info("Twitter Auto Block: access_token is null");
			 RequestToken requestToken = (RequestToken) session.getAttribute("request_token");
			 log.info("request_token: "+requestToken.toString());
		 try { 
		        	   accessToken = twitter.getOAuthAccessToken(requestToken,req.getParameter("oauth_verifier"));
		        	   log.info("Twitter Auto Block: request_token: "+requestToken.toString());
		        	   resp.getWriter().println(accessToken.toString());
		        	   session.setAttribute("access_token", accessToken);
		 } catch (TwitterException e) {
					e.printStackTrace();
		}
			}
		 else {
		 accessToken = (AccessToken) session.getAttribute("access_token");
		 }
		 log.info("Twitter Auto Block: access_token: "+accessToken.toString());
		 Query query = new Query("%40GaryLineker");
		    try {
				QueryResult result = twitter.search(query);
				resp.getWriter().println(result.toString());
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				resp.getWriter().println(e.toString());
			}
		    }
			}
		  
	
	
	
