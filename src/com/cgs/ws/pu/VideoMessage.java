package com.cgs.ws.pu;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="VideoMessage")
public class VideoMessage {

	@Id
	private int parent_id;
	private int device_id;
	private String video_url;
	private boolean STATUS;
	private Date createdDate;
	private String video_format;
	private String size;
	
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public boolean isSTATUS() {
		return STATUS;
	}
	public void setSTATUS(boolean sTATUS) {
		STATUS = sTATUS;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getVideo_format() {
		return video_format;
	}
	public void setVideo_format(String video_format) {
		this.video_format = video_format;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
