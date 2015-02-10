package com.google.gwt.messenger.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.messenger.model.Message;
import com.google.gwt.messenger.shared.FieldVerifier;
import com.google.gwt.messenger.shared.MessageSender;
import com.google.gwt.messenger.shared.MessageStatus;
import com.google.gwt.messenger.shared.MessageType;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessageScheduler implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel centerPanel = new HorizontalPanel();

	private VerticalPanel addMessagePanel = new VerticalPanel();
	private TextBox messageText = new TextBox();
	private TextBox groupText = new TextBox();
	private CheckBox cb = new CheckBox("Termination Message of this Group");

	private FlexTable incompleteMessagesTable = new FlexTable();
	private FlexTable completeMessagesTable = new FlexTable();

	private InlineLabel resourceCountLable = new InlineLabel();
	private TextBox resourceText = new TextBox();

	private TextBox groupCancelText = new TextBox();
	private Button groupCancelButton = new Button("Cancel Group");

	private HorizontalPanel resourcePanel = new HorizontalPanel();
	private HorizontalPanel groupCancelPanel = new HorizontalPanel();
	private HorizontalPanel prioritisationPanel = new HorizontalPanel();

	private RadioButton rb0 = new RadioButton("prioritisationRadipGroup",
			"Group Order");
	private RadioButton rb1 = new RadioButton("prioritisationRadipGroup",
			"Added Order");

	private Button addResourceButton = new Button("Add Resource");
	private ArrayList<Message> messages = new ArrayList<Message>();
	private List<Integer> terminatedGroup = new ArrayList<Integer>();
	private Integer resourceCount = 0;

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		FlowPanel groupTextField = new FlowPanel();
		InlineLabel groupLabel = new InlineLabel("Group:");
		groupTextField.add(groupLabel);
		groupTextField.add(new InlineHTML("<br/>"));
		groupTextField.add(groupText);

		FlowPanel MessageTextField = new FlowPanel();
		InlineLabel messageLabel = new InlineLabel("Message:");

		MessageTextField.add(messageLabel);
		MessageTextField.add(new InlineHTML("<br/>"));
		MessageTextField.add(messageText);

		Button addMessageButton = new Button("Add Message");

		incompleteMessagesTable.setText(0, 0, "Message");
		incompleteMessagesTable.setText(0, 1, "Status");
		incompleteMessagesTable.setText(0, 2, "Type");

		incompleteMessagesTable.getRowFormatter()
				.addStyleName(0, "tableHeader");
		incompleteMessagesTable.addStyleName("tableStyle");
		incompleteMessagesTable.getCellFormatter().addStyleName(0, 1,
				"rightAlign");
		incompleteMessagesTable.getCellFormatter().addStyleName(0, 2,
				"rightAlign");

		completeMessagesTable.setText(0, 0, "Message");
		completeMessagesTable.setText(0, 1, "Status");

		completeMessagesTable.getRowFormatter().addStyleName(0, "tableHeader");
		completeMessagesTable.addStyleName("tableStyle");
		completeMessagesTable.getCellFormatter().addStyleName(0, 1,
				"rightAlign");

		cb.setValue(false);

		addMessagePanel.add(groupTextField);
		addMessagePanel.add(MessageTextField);
		addMessagePanel.add(cb);
		addMessagePanel.add(addMessageButton);
		addMessagePanel.setSpacing(10);
		addMessagePanel.addStyleName("leftComponentStyle");

		InlineLabel priorityLabel = new InlineLabel("Prioritisation:");
		rb0.setValue(true);
		prioritisationPanel.add(priorityLabel);
		prioritisationPanel.add(rb0);
		prioritisationPanel.add(rb1);
		prioritisationPanel.addStyleName("leftComponentStyle");
		prioritisationPanel.setSpacing(15);

		InlineLabel resourceLable = new InlineLabel("Available Resource: ");
		resourceCountLable.setText(String.valueOf(resourceCount));
		resourcePanel.add(resourceLable);
		resourcePanel.add(resourceCountLable);
		resourceText.setWidth("20px");
		resourcePanel.add(resourceText);
		resourcePanel.add(addResourceButton);
		resourcePanel.addStyleName("leftComponentStyle");
		resourcePanel.setSpacing(15);

		InlineLabel groupCancelationLabel = new InlineLabel("Group Number: ");

		groupCancelPanel.add(groupCancelationLabel);

		groupCancelText.setWidth("20px");
		groupCancelPanel.add(groupCancelText);
		groupCancelPanel.add(groupCancelButton);
		groupCancelPanel.addStyleName("leftComponentStyle");
		groupCancelPanel.setSpacing(15);

		centerPanel.add(addMessagePanel);
		centerPanel.add(incompleteMessagesTable);
		centerPanel.add(completeMessagesTable);

		mainPanel.add(centerPanel);
		mainPanel.add(prioritisationPanel);
		mainPanel.add(resourcePanel);
		mainPanel.add(groupCancelPanel);

		RootPanel.get("MessageList").add(mainPanel);

		addMessageButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!FieldVerifier.isGroupNumberValid(groupText.getValue())){
					Window.alert("Group number can only be 0-9!");
					return;
				}
				if(!FieldVerifier.isMessageBodyValid(messageText.getValue())){
					Window.alert("Message body cannot be empty and must be shorter then 10 characters!");
					return;
				}
				addMessage();
				processMessage();
			}
		});

		addResourceButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!FieldVerifier.isGroupNumberValid(resourceText.getValue())){
					Window.alert("You can only added 0-9 resources");
					return;
				}
				resourceCount += Integer.valueOf(resourceText.getText());
				resourceCountLable.setText(String.valueOf(resourceCount));
				resourceText.setText("");
				processMessage();
			}
		});

		groupCancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if(!FieldVerifier.isGroupNumberValid(groupCancelText.getValue())){
					Window.alert("Group number can only be 0-9!");
					return;
				}
				
				for (Message message : MessageSender.getNewMessages(messages)) {
					if (message.getGroup() == Integer.valueOf(groupCancelText
							.getText())) {
						message.setMessageStatus(MessageStatus.CANCELED);
					}
				}
				groupCancelText.setText("");
				updateIncompleteMessageTable();
			}
		});

	}

	private void processMessage() {
		while (resourceCount > 0
				&& MessageSender.getNewMessages(messages).size() > 0) {
			final Message message;
			if (rb0.getValue()) {
				message = MessageSender.getFirstMessageByGroupPiority(messages);
			} else {
				message = MessageSender.getFirstMessageByAddedOrder(messages);
			}
			if (message != null) {
				if (terminatedGroup.contains(message.getGroup())) {
					Window.alert("Group " + message.getGroup()
							+ " has been terminated!");
					return;
				} else {
					if (message.getMessageType()
							.equals(MessageType.TERMINATION)) {
						terminatedGroup.add(message.getGroup());
					}
					resourceCount--;
					resourceCountLable.setText(String.valueOf(resourceCount));
					message.setMessageStatus(MessageStatus.PROCESSING);
					updateIncompleteMessageTable();

					{
						
						GateWayServiceAsync geteWayService = GWT.create(GateWayService.class);

					      AsyncCallback<String> callback = new AsyncCallback<String>() {
					        public void onFailure(Throwable caught) {
								Window.alert("Error processing message: "+ message.getBody() + " (Group " + message.getGroup() + ")");
								return;
					        }

							@Override
							public void onSuccess(String result) {
								message.setMessageStatus(MessageStatus.COMPLETED);
								updateCompleteMessageTable();
								updateIncompleteMessageTable();
							}
					      };

					      geteWayService.processMessage(message, callback);
						
									
						
					}
/*					Timer gateWay = new Timer() {
						@Override
						public void run() {
							message.setMessageStatus(MessageStatus.COMPLETED);

							updateCompleteMessageTable();
							updateIncompleteMessageTable();
						}
					};

					gateWay.schedule(5000);*/
				}
			}
		}
	}

	private void addMessage() {
		Message message = new Message();
		message.setMessageStatus(MessageStatus.NEW);
		message.setBody(messageText.getValue());
		message.setGroup(Integer.valueOf(groupText.getValue()));
		message.setId(messages.size());
		if (cb.getValue()) {
			message.setMessageType(MessageType.TERMINATION);
		} else {
			message.setMessageType(MessageType.REGULAR);
		}
		messages.add(message);
		messageText.setValue("");
		groupText.setValue("");
		cb.setValue(false);
		updateIncompleteMessageTable();

	}

	private void clearTableContent(FlexTable table) {
		for (int row = 1; row < table.getRowCount(); row++) {
			table.removeRow(row);
		}
	}

	private void updateIncompleteMessageTable() {
		clearTableContent(incompleteMessagesTable);
		int row = 1;
		for (Message message : MessageSender.getIncompletedMessages(messages)) {
			incompleteMessagesTable.setText(row, 0, message.getBody()
					+ " (Group: " + message.getGroup() + ")");

			incompleteMessagesTable.setText(row, 1, message.getMessageStatus()
					.name());
			incompleteMessagesTable.setText(row, 2, message.getMessageType()
					.name());
			incompleteMessagesTable.getCellFormatter().addStyleName(row, 1,
					"rightAlign");
			incompleteMessagesTable.getCellFormatter().addStyleName(row, 2,
					"rightAlign");

			row++;
		}

	}

	private void updateCompleteMessageTable() {
		clearTableContent(completeMessagesTable);
		int row = 1;
		for (Message message : MessageSender.getCompletedMessages(messages)) {
			completeMessagesTable.setText(row, 0, message.getBody()
					+ " (Group: " + message.getGroup() + ")");

			completeMessagesTable.setText(row, 1, message.getMessageStatus()
					.name());
			row++;
			completeMessagesTable.getCellFormatter().addStyleName(row, 1,
					"rightAlign");
		}

	}

}
