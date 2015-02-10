package com.google.gwt.messenger.model;

import java.io.Serializable;

import com.google.gwt.messenger.shared.MessageStatus;
import com.google.gwt.messenger.shared.MessageType;

public class Message implements Serializable {


	private static final long serialVersionUID = 139708587051131865L;
	
	private String body;
	private long id;
	private Integer group;
	private MessageStatus messageStatus;
	private MessageType messageType;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public MessageStatus getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
}
