package com.google.gwt.messenger.server;

import com.google.gwt.messenger.client.GateWayService;
import com.google.gwt.messenger.model.Message;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GateWayServiceImpl extends RemoteServiceServlet implements
		GateWayService {

	public String processMessage(Message message) throws IllegalArgumentException {
		 
		//Nothing to be implemented in processing message. Assuming it takes 5 second to process each message.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			return "ERROR";
		}  
		return "SUCCESS";
	}

}
