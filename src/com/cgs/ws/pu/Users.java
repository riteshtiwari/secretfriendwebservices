package com.cgs.ws.pu;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="Users")
public class Users {

	  @Id
	  	private int userId;
	  
	    private String emailId;
	    private String password;
	    private Integer pin;
	    private Boolean activationstatus;
	    private Date createdDate;
	    private Date updatedDate;
	    
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getEmailId() {
			return emailId;
		}
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getUpdatedDate() {
			return updatedDate;
		}
		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
		}
		public int getPin() {
			return pin;
		}
		public void setPin(Integer pin) {
			this.pin = pin;
		}
		public Boolean getActivationstatus() {
			return activationstatus;
		}
		public void setActivationstatus(Boolean activationstatus) {
			this.activationstatus = activationstatus;
		}
}
