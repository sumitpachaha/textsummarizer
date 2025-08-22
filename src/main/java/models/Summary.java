package models;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="summary")
public class Summary {
	@Id
	private String sourceUrl;
	private String sourceText;
	private int sentenceNumber;
	private boolean userActive;
	private int userId;
	
	private ArrayList<String> finalSummary= new ArrayList<>();

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public ArrayList<String> getFinalSummary() {
		return finalSummary;
	}
	public void setFinalSummary(ArrayList<String> finalSummary) {
		this.finalSummary = finalSummary;
	}
	public int getSentenceNumber() {
		return sentenceNumber;
	}
	public void setSentenceNumber(int sentenceNumber) {
		this.sentenceNumber = sentenceNumber;
	}
	public boolean isUserActive() {
		return userActive;
	}
	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getSourceText() {
		return sourceText;
	}
	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	
}
