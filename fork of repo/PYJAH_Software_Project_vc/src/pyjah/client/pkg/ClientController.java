package pyjah.client.pkg;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import pyjah.util.pkg.Email;
import pyjah.util.pkg.User;
//import unused.User;

public class ClientController implements Initializable {
	@FXML
	private TabPane tPane;
	@FXML
	private TextArea messageBody = new TextArea();
	@FXML
	private TextField toLine = new TextField();
	@FXML
	private TextField subjectLine = new TextField();
	@FXML
	private Button sendButton = new Button();
	@FXML
	Label userIdLabel;
	@FXML
	private RadioButton userARadioButton;
	@FXML
	private RadioButton userBRadioButton;
	@FXML
	private Button loginButton;
	@FXML
	private Tab loginTab;
	@FXML
	private Tab composeTab;
	@FXML
	private Tab inboxTab;
	@FXML
	private Tab sentTab;
	@FXML
	private Button logoutButton;

	@FXML
	private ListView<String> inboxListView;
	
	@FXML
	private ListView<String> sentboxListView;

	ToggleGroup group = new ToggleGroup();

	private ArrayList<Email> inboxAL = new ArrayList<Email>();
	private ArrayList<Email> sentBoxAL = new ArrayList<Email>();

	// private User currentUser;
	private HashMap messageFields = new HashMap();
	private User user=null;
	//private Email email;
	
	
	Client pyjahClient = new Client();

	// Method to send the input from the GUI fields to a location on button click.
	// As of now it just prints the text fields inputted.

	
	//Composes a new email object with appropriate fields.
	@FXML
	public void handleSendButtonClick(ActionEvent event) {
		Email email = new Email(user.getUsername(), toLine.getText(), subjectLine.getText(), messageBody.getText());// ,
		email.setTime();																											// email.getTime(),
																													// "Unread");
		this.user.addToSentBox(email);
		pyjahClient.sendUser(user);

		updateSentbox();
		toLine.clear();
		subjectLine.clear();
		messageBody.clear();
		
		

		/*
		 * messageFields.put("Recipient", toLine.getText());
		 * messageFields.put("Subject", subjectLine.getText());
		 * messageFields.put("Message", messageBody.getText());
		 * System.out.println("Recipient:\n" + messageFields.get("Recipient") +"\n\n" +
		 * "Subject:\n" + messageFields.get("Subject") + "\n\n" + "Message Body:\n" +
		 * messageFields.get("Message"));
		 * 
		 */

		/*
		 * String sender; String recipient=toLine.getText(); String
		 * body=messageBody.getText(); String subject=subjectLine.getText();
		 * pyjahClient.sendMessage(recipient); pyjahClient.sendMessage(body);
		 * pyjahClient.sendMessage(subject);
		 */

	}

	@FXML
	void loginOnButtonClick(ActionEvent event) {
		//pyjahClient.sendUser(user);
		//pyjahClient.setLoggedIn(true);
		composeTab.setDisable(false);
		inboxTab.setDisable(false);
		sentTab.setDisable(false);
		
		tPane.getSelectionModel().select(composeTab);
		loginTab.setDisable(true);
		
		
		updateInbox();
		updateSentbox();
		
		


	}

	@FXML
	void radioSetToUserA(ActionEvent event) {
		//System.out.println("raido");
		userARadioButton.setToggleGroup(group);
		userARadioButton.setSelected(true);
		this.user = testUserA();
		//this.user = new User();
		//user.setUsername("User A");
		this.userIdLabel.setText(this.user.getUsername());
	}

	@FXML
	void radioSetToUserB(ActionEvent event) {
		System.out.println("radio b");
		userBRadioButton.setToggleGroup(group);
		userBRadioButton.setSelected(true);
		//this.user=testUserA();
		this.user = new User();
	    user.setUsername("User B");
		this.userIdLabel.setText(this.user.getUsername());
	}

	@FXML
	void backToLoginButtonClick(ActionEvent event) {
		loginTab.setDisable(false);
		tPane.getSelectionModel().select(loginTab);
		composeTab.setDisable(true);
		inboxTab.setDisable(true);
		sentTab.setDisable(true);
	}

	public void getUser() {
		this.user = new User();
		user.setUsername("Dude");
		this.userIdLabel.setText(this.user.getUsername());

	}

	
	//Just a test user object to work on populating the list view
	public User testUserA() {
		User testUserA = new User();
		Email test = new Email();
		test.setSender("Howie");
		test.setRecipient("John");
		test.setSubject("Test email 1");
		test.setTime();
		test.setBody("Hello, this is a test email body message");
		test.setStatus("unread");
		
		Email test2 = new Email();
		test2.setSender("Howie");
		test2.setRecipient("John");
		test2.setSubject("Test email 1");
		test2.setTime();
		test2.setBody("Hello, this is a test email body message");
		test2.setStatus("unread");
		
		
		
		testUserA.setSentboxAL(sentBoxAL);
		testUserA.setInboxAL(inboxAL);
		testUserA.setUsername("User A test");
		testUserA.setPassword("123");
		
		testUserA.addToInbox(test);
		testUserA.addToInbox(test2);
		

		return testUserA;

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1r) {
		
		
	}
	
	public void updateInbox() {
		ArrayList<String> testList = new ArrayList<String>();
		for (int i =0; i<user.getInboxAL().size(); i++) {
			testList.add(user.getInboxAL().get(i).getSubject());
		}

        inboxListView.setItems(FXCollections.observableList(testList));
	}
	
	public void updateSentbox() {
		ArrayList<String> testList = new ArrayList<String>();
		for (int i =0; i<user.getSentboxAL().size(); i++) {
			testList.add(user.getSentboxAL().get(i).getSubject());
		}

        sentboxListView.setItems(FXCollections.observableList(testList));
	}

}
