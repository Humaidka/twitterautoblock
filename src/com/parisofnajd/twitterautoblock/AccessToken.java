package com.parisofnajd.twitterautoblock;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;


public class AccessToken {

	public void persist(String screenname,String userid, String token, String secret) {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
		Entity at = new Entity("AccessTokens",userid);
		at.setProperty("UserID", userid);
		at.setProperty("ScreenName", screenname);
		at.setProperty("Token", token);
		at.setProperty("Secret", secret);
		ds.put(at);

	}
}
