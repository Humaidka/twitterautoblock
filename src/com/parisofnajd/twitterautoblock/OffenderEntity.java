package com.parisofnajd.twitterautoblock;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;



public class OffenderEntity {

	public static void persist(long userid,String screenname, String offince, boolean blocked) {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
		Entity at = new Entity("Offenders",userid);
		at.setProperty("UserID", userid);
		at.setProperty("ScreenName", screenname);
		at.setProperty("Offence", offince);
		at.setProperty("Blocked", blocked);
		ds.put(at);
	}
	
public static boolean fetchOffender(long userid) {
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("Offenders", userid);
		Entity offender = null;
		try {
			offender=ds.get(k);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return (boolean) offender.getProperty("Blocked");
	}
}
