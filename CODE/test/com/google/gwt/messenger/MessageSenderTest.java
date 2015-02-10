package com.google.gwt.messenger;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.messenger.model.Message;
import com.google.gwt.messenger.shared.MessageSender;
import com.google.gwt.messenger.shared.MessageStatus;

public class MessageSenderTest {

	List<Message> messages = new ArrayList<Message>();
	
	@Before
	public void setup(){
		Message message1 = new Message();
		message1.setBody("1");
		message1.setGroup(2);
		message1.setMessageStatus(MessageStatus.PROCESSING);
		
		Message message2 = new Message();
		message2.setBody("2");
		message2.setGroup(1);
		message2.setMessageStatus(MessageStatus.NEW);
		
		Message message3 = new Message();
		message3.setBody("3");
		message3.setGroup(2);
		message3.setMessageStatus(MessageStatus.NEW);
		
		Message message4 = new Message();
		message4.setBody("4");
		message4.setGroup(4);
		message4.setMessageStatus(MessageStatus.NEW);
		
		messages.add(message1);
		messages.add(message2);
		messages.add(message3);
		messages.add(message4);
		
		
	}
	
	@Test
	public void test() {
		assertEquals(MessageSender.getFirstMessageByGroupPiority(messages).getBody(), "3");
		assertEquals(MessageSender.getFirstMessageByAddedOrder(messages).getBody(), "2");
		
	}

}
