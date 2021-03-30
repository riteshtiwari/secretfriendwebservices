package com.cgs.ws.rest.util;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class RestServiceDAO {

	public String getDateTime() {
		
		java.util.Date dateWithTime = new java.util.Date();
		java.text.SimpleDateFormat simpleDateformater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleDateformater.format(dateWithTime);
		System.out.println("in getDateTime date : "+currentTime);
		return currentTime;
	}
}
