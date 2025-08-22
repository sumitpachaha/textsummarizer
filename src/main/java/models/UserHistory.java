package models;

import javax.persistence.*;

@Entity
@Table(name="userhistory")
public class UserHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Lob
	private String source;
	private double timeConsumed;
	@Lob
	private String summary;
	
	@ManyToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getTimeConsumed() {
		return timeConsumed;
	}

	public void setTimeConsumed(double timeConsumed) {
		this.timeConsumed = timeConsumed;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
