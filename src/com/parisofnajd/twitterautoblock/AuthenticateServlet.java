package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;
import twitter4j.*;
import twitter4j.auth.*;

@SuppressWarnings("serial")
public class AuthenticateServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String authurl = null;
		
		try {
			authurl = AuthenticateServlet.Authenticate();
			resp.sendRedirect(authurl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private static String Authenticate() throws Exception{
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		//twitter.setOAuthConsumer("[consumer key]", "[consumer secret]");
		RequestToken requestToken = twitter.getOAuthRequestToken();
		//AccessToken accessToken = null;
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//while (null == accessToken) {
		//	System.out.println("Open the following URL and grant access to your account:");
		    return requestToken.getAuthorizationURL();
		  //  System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
		  //  String pin = br.readLine();
		  //  try{
		  //     if(pin.length() > 0){
		  //       accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		  //     }else{
		   // 	 accessToken = twitter.getOAuthAccessToken();
		   //    }
		  //    } catch (TwitterException te) {
		   //     if(401 == te.getStatusCode()){
	//	          System.out.println("Unable to get the access token.");
		 //       }else{
		 //         te.printStackTrace();
		   //     }
		 //     }
		   // }
		    //persist to the accessToken for future reference.
		    //storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
		    //String[] args = null;
			//Status status = twitter.updateStatus(args[0]);
		    //System.out.println("Successfully updated the status to [" + status.getText() + "].");
		    //System.exit(0);
		  }
		  //private static void storeAccessToken(long useId, AccessToken accessToken){
		    //store accessToken.getToken()
		    //store accessToken.getTokenSecret()
			//}
	
	
	
}
