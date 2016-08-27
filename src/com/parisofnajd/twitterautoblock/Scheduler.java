package com.parisofnajd.twitterautoblock;

import java.io.*;
import javax.servlet.http.*;

import java.util.List;
import java.util.logging.Logger;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.modules.ModulesServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
  
@SuppressWarnings("serial")
public class Scheduler extends HttpServlet {
	private static final Logger log = Logger.getLogger(Scheduler.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("AccessTokens");
		q.addProjection(new PropertyProjection("UserID", Long.class));
		List<Entity> results = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		//log.info("Twitter Auto Block : " + results.toString());
		
		for (Entity temp : results) {
			//log.info("Twitter Auto Block : " + temp.toString());
			long userid = (long) temp.getProperty("UserID");
			enqueue(userid);
		}
			//log.info("Twitter Auto Block : persisting user info in datastore");
			//enqueue((Long)userid);
	
		//loop through all AccessTokens and call scheduler
		
	
	}
	
	private static void enqueue(Long userid) {
		
		Queue queue = QueueFactory.getDefaultQueue();
		TaskOptions to = TaskOptions.Builder.withUrl("/worker").header("Host", ModulesServiceFactory.getModulesService().getVersionHostname("default", null)).param("userid", userid.toString());
		queue.add(to);
		log.info("Twitter Auto Block : "+to.toString());
	}
}
		  
	
	
	
