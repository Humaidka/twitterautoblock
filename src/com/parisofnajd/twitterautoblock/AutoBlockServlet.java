package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;
import twitter4j.*;
import twitter4j.auth.*;

@SuppressWarnings("serial")
public class AutoBlockServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		AccessToken accessToken = null;
		try {
			accessToken = twitter.getOAuthAccessToken();
			resp.getWriter().println(accessToken.getUserId());
		}
		catch (TwitterException te) {
			if(401 == te.getStatusCode()){
			System.out.println("Unable to get the access token.");
			}else{
		    te.printStackTrace();
		   }
			
			
			
			
		     }
		    }
		    //persist to the accessToken for future reference.
		    //storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
		    //String[] args = null;
			//Status status = twitter.updateStatus(args[0]);
		    //System.out.println("Successfully updated the status to [" + status.getText() + "].");
		    //System.exit(0);
			//private static void storeAccessToken(long useId, AccessToken accessToken){
    		//store accessToken.getToken()
    		//store accessToken.getTokenSecret()
			//}
			}
		  
	
	
	
