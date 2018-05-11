package ar.edu.ub.p3.common;
import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2519977399863068059L;
	private String userName;
    private String message;
    private Date date;

    public ChatMessage(String userName, String message) {
        this.setUserName(userName);
        this.setMessage(message);
        setDate(new Date());
    }

    public String toString() {
    	
    	//Si el origen es el server, le doy otro formato
        if ( this.getUserName().equals("Server"))
            return this.getMessage();        
        
        return this.getDate() + " [" + this.getUserName() + "] " + this.getMessage();
    }

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	private void setDate(Date date) {
		this.date = date;
	}
}