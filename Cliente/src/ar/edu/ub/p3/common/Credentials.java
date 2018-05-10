package ar.edu.ub.p3.common;
import java.io.Serializable;

public class Credentials implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7618275832530225891L;
	private String userName;
    private String userPassword;

    public Credentials(String userName, String userPassword) {
        this.setUserName(userName);
        this.setUserPassword(userPassword);
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

	private void setUserName(String userName) {
		this.userName = userName;
	}

	private void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}