package com.cgs.ws.pu;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="Avatar")
public class Avatar {
	@Id
	private int id;
	private String avatarName;
	private String avatarimageurl;
	private String avatargifurl;
	private String avatargender;
	private String avatardefaultmessage;
	private String voice;
	private String bitrate;
	private String speed;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAvatarName() {
		return avatarName;
	}
	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}
	public String getAvatarimageurl() {
		return avatarimageurl;
	}
	public void setAvatarimageurl(String avatarimageurl) {
		this.avatarimageurl = avatarimageurl;
	}
	public String getAvatargender() {
		return avatargender;
	}
	public void setAvatargender(String avatargender) {
		this.avatargender = avatargender;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public String getBitrate() {
		return bitrate;
	}
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getAvatargifurl() {
		return avatargifurl;
	}
	public void setAvatargifurl(String avatargifurl) {
		this.avatargifurl = avatargifurl;
	}
	public String getAvatardefaultmessage() {
		return avatardefaultmessage;
	}
	public void setAvatardefaultmessage(String avatardefaultmessage) {
		this.avatardefaultmessage = avatardefaultmessage;
	}
	

	 public String toString() {
	        return "Device  id: " + getId() + " getAvatarName: " + getAvatarName() + " getAvatarimageurl: " 
	        			+ getAvatarimageurl()+ " getVoice: " + getVoice()+ " getAvatargifurl: " + getAvatargifurl();
	 }
	
}
