package com.cgs.ws.pu;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="AvatarHeadIcon")
public class AvatarHeadIcon {

	@Id
	private int avatarimageid;
	private String avatarimageurl;
	private String avatarbodyimageurl;
	private String avatargifurl;
	private String avatarname;

	public String getAvatarname() {
		return avatarname;
	}

	public void setAvatarname(String avatarname) {
		this.avatarname = avatarname;
	}

	public int getAvatarimageid() {
		return avatarimageid;
	}

	public void setAvatarimageid(int avatarimageid) {
		this.avatarimageid = avatarimageid;
	}

	public String getAvatarimageurl() {
		return avatarimageurl;
	}

	public void setAvatarimageurl(String avatarimageurl) {
		this.avatarimageurl = avatarimageurl;
	}

	public String getAvatargifurl() {
		return avatargifurl;
	}

	public void setAvatargifurl(String avatargifurl) {
		this.avatargifurl = avatargifurl;
	}

	public String getAvatarbodyimageurl() {
		return avatarbodyimageurl;
	}

	public void setAvatarbodyimageurl(String avatarbodyimageurl) {
		this.avatarbodyimageurl = avatarbodyimageurl;
	}
}
