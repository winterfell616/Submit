package com.google.gwt.messenger.client;

import com.google.gwt.messenger.model.Message;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GateWayServiceAsync {
	void processMessage(Message message, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
