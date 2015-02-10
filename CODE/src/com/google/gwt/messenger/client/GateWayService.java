package com.google.gwt.messenger.client;

import com.google.gwt.messenger.model.Message;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("gateway")
public interface GateWayService extends RemoteService {
	String processMessage(Message message) throws IllegalArgumentException;
}
