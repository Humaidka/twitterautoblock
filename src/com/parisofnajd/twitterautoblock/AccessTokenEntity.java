package com.parisofnajd.twitterautoblock;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;



public class AccessTokenEntity {

	public static void persist(long userid,String screenname, String token, String secret) {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
		Entity at = new Entity("AccessTokens",userid);
		at.setProperty("UserID", userid);
		at.setProperty("ScreenName", screenname);
		at.setProperty("Token", token);
		at.setProperty("Secret", secret);
		ds.put(at);

	}
	
	public static String fetchAccessToken(long userid) {
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("AccessTokens", userid);
		Entity accesstoken=null;
		try {
			accesstoken=ds.get(k);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return accesstoken.getProperty("Token").toString();
	}
	
public static String fetchAccessTokenSecret(long userid) {
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("AccessTokens", userid);
		Entity accesstoken=null;
		try {
			accesstoken=ds.get(k);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return accesstoken.getProperty("Secret").toString();
	}
}
