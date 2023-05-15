package model;

/**
 * this is the model responsible for all the user that will be using our
 * e-commerce site. email is the email address of the user which is unique,
 * username is the name given by the user to themselves, passoword is the
 * password set by the user and pp is the profile picture name of the user.
 *
 */
public class User {
	private String email;
	private String username;
	private String password;
	private String address;
	private String phoneNo;
	private String pp;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username + ", password=" + password + ", address=" + address
				+ ", phoneNo=" + phoneNo + ", pp=" + pp + "]";
	}

	

}
