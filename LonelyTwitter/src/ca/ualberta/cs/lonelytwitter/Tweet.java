/**
 * 
 */
package ca.ualberta.cs.lonelytwitter;

import java.io.Serializable;
import java.util.Date;

public class Tweet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date tweetDate;
	private String tweetBody;
	
	public Tweet() {
		super();
	}

	public Tweet(Date tweetDate, String tweetBody) {
		this.tweetDate = tweetDate;
		this.tweetBody = tweetBody;
	}

	public Date getTweetDate() {
		return tweetDate;
	}

	public void setTweetDate(Date tweetDate) {
		this.tweetDate = tweetDate;
	}

	public String getTweetBody() {
		return tweetBody;
	}

	public void setTweetBody(String tweetBody) {
		this.tweetBody = tweetBody;
	}

	@Override
	public String toString() {
		return tweetDate + " | " + tweetBody;
	}
}
