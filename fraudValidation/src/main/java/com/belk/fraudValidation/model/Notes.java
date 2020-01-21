package com.belk.fraudValidation.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName ="Notes")
public class Notes {
	@JacksonXmlProperty(localName = "Note")
	private Note note;
	
	public Notes() {}

	public Notes(Note note) {
		super();
		this.note = note;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}
	
	
	

}
