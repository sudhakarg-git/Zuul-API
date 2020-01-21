package com.belk.fraudValidation.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName ="Note")
public class Note {
	@JacksonXmlProperty(localName = "Date")
	private String date;
	@JacksonXmlProperty(localName = "AddedBy")
	private String addedBy;
	@JacksonXmlProperty(localName = "Comment")
	private String comment;
	
	public Note() {}

	public Note(String date, String addedBy, String comment) {
		super();
		this.date = date;
		this.addedBy = addedBy;
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
