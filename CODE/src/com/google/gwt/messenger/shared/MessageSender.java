package com.google.gwt.messenger.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.messenger.model.Message;
 
public class MessageSender {
 
  /*  public static List<Message> messages = new ArrayList<Message>();*/
 
    public MessageSender() {
    };
 
    public static List<Message> getCompletedMessages(List<Message> messages) {
        List<Message> completedMessages = new ArrayList<Message>();
        for (Message message : messages) {
            if (message.getMessageStatus().equals(MessageStatus.COMPLETED)) {
                completedMessages.add(message);
            }
        }
        return completedMessages;
    }
 
    public static List<Message> getIncompletedMessages(List<Message> messages) {
        List<Message> completedMessages = new ArrayList<Message>();
        for (Message message : messages) {
            if (!message.getMessageStatus().equals(MessageStatus.COMPLETED)) {
                completedMessages.add(message);
            }
        }
        return completedMessages;
    }
    
    public static List<Message> getProgressingMessages(List<Message> messages) {
        List<Message> prograssingMessages = new ArrayList<Message>();
        for (Message message : messages) {
            if (message.getMessageStatus().equals(MessageStatus.PROCESSING)) {
                prograssingMessages.add(message);
            }
        }
        return prograssingMessages;
    }
 
    public static List<Message> getNewMessages(List<Message> messages) {
        List<Message> newMessages = new ArrayList<Message>();
        for (Message message : messages) {
            if (message.getMessageStatus().equals(MessageStatus.NEW)) {
                newMessages.add(message);
            }
        }
        return newMessages;
    }
   
    public static Message getFirstMessageByGroupPiority(List<Message> messages){
        for(Message progressingMessage : getProgressingMessages(messages)){
          for(Message newMessage : getNewMessages(messages)){
              if(newMessage.getGroup() == progressingMessage.getGroup()){
                  return newMessage;
              }
          }
        }
        return getNewMessages(messages).size() > 0 ? getNewMessages(messages).get(0) : null;
    }
   
    public static Message getFirstMessageByAddedOrder(List<Message> messages){
        return getNewMessages(messages).size() > 0 ? getNewMessages(messages).get(0) : null;
    }
}
 