package model.helper;

/**
 * This model is Responsible for giving login status to the user where message
 * variable is for login status and messageType is for whether the login was a success for a failure.
 *
 */

public class LoginNotifier {

	private String messageType;
	private String message;

	public LoginNotifier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginNotifier(String messageType, String message) {
		super();
		this.messageType = messageType;
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
