package com.belk.batch.model;

import java.util.Date;

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;*/

public class Note {
	private Long id;
	private Date time;
	private String comments;
	private String requestId;
	private String addedBy;
	private Conversion conversion;

	public Note() {
	}

	public Note(Long id, Date time, String comments, String requestId, String addedBy,Conversion conversion) {
		super();
		this.id = id;
		this.time = time;
		this.comments = comments;
		this.requestId = requestId;
		this.addedBy = addedBy;
		this.conversion = conversion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Conversion getConversion() {
		return conversion;
	}

	public void setConversion(Conversion conversion) {
		this.conversion = conversion;
	}

}
