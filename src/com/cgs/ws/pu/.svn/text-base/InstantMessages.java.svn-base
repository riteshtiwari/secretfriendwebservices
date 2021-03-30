package com.cgs.ws.pu;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="InstantMessages")
public class InstantMessages {
	@Id
	private int id; 
	private int parent_id;
	private String device_id; 
	private String message_info;
	private String voice;
	private int avatar_image_id;
	private boolean status;
	private Date created_date;
	private String charactersize;
	private String imgUrl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getMessage_info() {
		return message_info;
	}
	public String getAlert() {
		return message_info;
	}
	public void setMessage_info(String message_info) {
		this.message_info = message_info;
	}
	public String getVoice() {
		return voice;
	}
	public String getSound() {
		return "default";
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public int getAvatar_image_id() {
		return avatar_image_id;
	}
	public void setAvatar_image_id(int avatar_image_id) {
		this.avatar_image_id = avatar_image_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getCharactersize() {
		return charactersize;
	}
	public void setCharactersize(String charactersize) {
		this.charactersize = charactersize;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
