package com.cgs.ws.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.cgs.ws.pu.Avatar;
import com.cgs.ws.pu.AvatarHeadIcon;
import com.cgs.ws.pu.Device;
import com.cgs.ws.pu.InstantMessages;
import com.cgs.ws.pu.Language;
import com.cgs.ws.pu.Users;
import com.cgs.ws.pu.VideoMessage;
import com.cgs.ws.rest.util.RestServiceDAO;

//http://localhost:8080/RESTfulExample/rest/message/hello%20world

/*
 * @author : Naveed	
 * */

@Path("/sf")
@Stateless
@LocalBean
public class MessageRestService {

	@EJB
	private SecretFrientDAO secretfriendDao;

	/*@EJB
	private RestServiceDAO restServiceDao;*/
	
	@POST
	@Path("/login")
	@Consumes("text/plain")
	@Produces("application/json")
	public Users validateLogin(String value) {
		String val[] = value.split("~");
		System.out.println(" user :" + val[0] + "\t pass " + val[1]);
		// String res=secretfriendDao.validateLogin(val[0],val[1]);
		String res = "";
		Users dto = secretfriendDao.validate(val[0], val[1]);
		return dto;
	}

	@POST
	@Path("/deviceinfo")
	@Produces("application/json")
	public Device insertDeviceInfo(String value) {
		String val[] = value.split("~");
		System.out.println("length:---->>>>" + val.length + " parentid :"
				+ val[0] + "\t deviceid" + val[1]);
		Device res = secretfriendDao.insertDeviceInfo(val[0], val[1]);
		return res;
	}

	@POST
	@Path("/passwrd")
	@Consumes("text/plain")
	public boolean updatePassword(String value) {
		String val[] = value.split("~");
		System.out.println("updatePassword VAlue ---->>>>" + val.length
				+ " user :" + val[0] + "\t pass" + val[1]);
		boolean res = secretfriendDao.updatePassword(val[0], val[1]);
		return res;
	}

	@POST
	@Path("/alert")
	@Produces("application/json")
	/*
	 * public List<InstantMessages> getAlerts(String value) { String val[] =
	 * value.split("~"); System.out.println("getAlerts Value ---->>>>" +
	 * val.length +" userid :"+ val[0] +"\t deviceid"+val[1]);
	 * 
	 * return secretfriendDao.getAlerts(val[0],val[1]); }
	 */
	public InstantMessages getAlerts(String value) {
		String val[] = value.split("~");
		System.out.println("getAlerts Value ---->>>>" + val.length
				+ " userid :" + val[0] + "\t deviceid" + val[1]);
		return secretfriendDao.getAlert(val[0], val[1]);
	}

	@POST
	@Path("/lstdevice")
	@Produces("application/json")
	public List<Device> getDeviceLst(String emailId) {
		return secretfriendDao.getDeviceLst(emailId);
	}

	@POST
	@Path("/lstavatar")
	@Produces("application/json")
	public List<Avatar> getAvatarLst() {
		return secretfriendDao.getAvatarLst();
	}

	@POST
	@Path("/deviceAvatarImage")
	@Produces("application/json")
	public List<Avatar> getDeviceAvatarImage(String input) {
		String val[] = input.split("~");
		return secretfriendDao.getDeviceAvatarImage(val[0], val[1]);
	}

	@POST
	@Path("/lstvedio")
	@Produces("application/json")
	public List<VideoMessage> getVedioLst(String value) {
		String val[] = value.split("~");
		return secretfriendDao.getVedioLst(Integer.valueOf(val[0]),
				Integer.valueOf(val[1]));
	}

	@POST
	@Path("/lstlanguage")
	@Produces("application/json")
	public List<Language> getLanguageLst() {
		return secretfriendDao.getLanguageLst();
	}

	@POST
	@Path("/updatedevName")
	@Produces("application/json")
	public boolean updateDeviceName(String value) {
		String val[] = value.split("~");
		return secretfriendDao.updateDeviceName(val[0], val[1], val[2]);
	}

	@POST
	@Path("/register")
	@Produces("application/json")
	public boolean doRegister(String value) {
		String val[] = value.split("~");
		return secretfriendDao.doRegister(val[0], val[1], val[2]);
	}

	@POST
	@Path("/insertmsg")
	@Produces("application/json")
	public boolean insertMessage(String value) {
		String val[] = value.split("~");

		System.out.println("val[0] :: " + val[0]);
		System.out.println("val[1] :: " + val[1]);
		System.out.println("val[2] :: " + val[2]);
		System.out.println("val[3] :: " + val[3]);
		System.out.println("val[4] :: " + val[4]);
		System.out.println("val[5] :: " + val[5]);

		String device_id = val[1].trim();

		if (device_id.contains("##")) {

			String[] strDeviceArray = device_id.split("##");
			for (int i = 0; i < strDeviceArray.length; i++) {
				String dev_id = strDeviceArray[i];
				secretfriendDao.insertMessage(val[0], dev_id, val[2], val[3],
						val[4], val[5], this);

			}

		} else {
			secretfriendDao.insertMessage(val[0], val[1], val[2], val[3],
					val[4], val[5], this);

		}

		// secretfriendDao.insertMessage(val[0], val[1], val[2], val[3], val[4],
		// val[5], this);

		return true;
	}

	// This code for to send to list of Devices
	/*
	 * public boolean insertMessage(String value){ String val[] =
	 * value.split("~"); String splitDeviceId[] =val[1].split("##"); boolean
	 * status=false;
	 * System.out.println("splitDeviceId.length :: "+splitDeviceId.length);
	 * for(int i=0;i<splitDeviceId.length;i++) { status=
	 * secretfriendDao.insertMessage
	 * (val[0],splitDeviceId[i],val[2],val[3],val[4],val[5], this); } return
	 * status;
	 * 
	 * }
	 */

	@POST
	@Path("/disablealert")
	@Produces("application/json")
	public boolean disableAlert(String value) {
		String val[] = value.split("~");
		System.out.println("value :: " + value);
		System.out.println("val[0] :: " + val[0]);
		System.out.println("val[1] :: " + val[1]);
		int parentId = Integer.parseInt(val[0]);
		return secretfriendDao.disableAlert(parentId, val[1]);
	}

	@POST
	@Path("/signup")
	@Consumes("text/plain")
	@Produces("application/json")
	public String registration(String value) {
		String status;
		String val[] = value.split("~");

		if (val[4].equals("true")) {
			val[4] = "1";
		} else {
			val[4] = "0";
		}

		status = secretfriendDao.insertUser(val[0], val[1], val[2],
				Integer.parseInt(val[3]), val[4], val[5], val[6], val[7],
				Integer.parseInt(val[8]), val[9], this);

		return status;
	}

	// Email authendication...
	@POST
	@Path("/emailAuth")
	@Consumes("text/plain")
	@Produces("application/json")
	public String emailAuthendication(String value) {
		String status;

		status = secretfriendDao.emailAuthentication(value);

		return status;
	}

	@POST
	@Path("/emailActivate")
	// @Consumes("text/plain")
	// @Produces("application/json")
	@Produces("text/plain")
	public String emailActivate(String emailId) {
		String status;
		emailId = emailId.replace("%40", "@");

		String[] splitEmail = emailId.split("=");
		System.out.println("splitEmail[1] :: " + splitEmail[1]);

		status = secretfriendDao.emailActivate(splitEmail[1]);

		System.out.println("status :: " + status);
		return status;
	}

	@POST
	@Path("/forgotpassword")
	@Produces("text/plain")
	public String forgotpasword(String emailId) {
		String status;
		status = secretfriendDao.getforgetpassword(emailId);
		System.out.println("status :: " + status);
		return status;
	}

	@POST
	@Path("/forgotpin")
	@Produces("text/plain")
	public String forgotpin(String emailId) {
		String status;
		status = secretfriendDao.getforgotpin(emailId);
		System.out.println("status :: " + status);
		return status;
	}

	@POST
	@Path("/lstavatarheadicon")
	@Produces("application/json")
	public List<AvatarHeadIcon> getAvatarHeadIconLst() {
		return secretfriendDao.getAvatarHeadIconLst();
	}

	@POST
	@Path("/checkEmailAccount")
	@Produces("application/json")
	public Users checkEmailAccount(String emailId) {
		Users dto = secretfriendDao.checkEmailAccount(emailId);
		return dto;
	}

	@POST
	@Path("/checkDeviceRegisterStatus")
	@Produces("application/json")
	public Device checkDeviceRegisterStatus(String imei) {
		Device dto = secretfriendDao.checkDeviceRegisterStatus(imei);
		return dto;
	}

	@POST
	@Path("/checkDeviceName")
	@Produces("text/plain")
	public boolean checkDeviceName(String input) {
		boolean status;
		String val[] = input.split("~");
		status = secretfriendDao.checkDeviceName(val[0], val[1]);
		return status;
	}

	@POST
	@Path("/updateDeviceGeneralSettings")
	@Produces("text/plain")
	public boolean updateDeviceGeneralSettings(String input) {
		boolean status;
		String val[] = input.split("~");
		String emailId = val[0];
		String udid = val[1];
		String password = val[2];
		int pin = Integer.parseInt(val[3]);
		String deviceName = val[4];
		String defaultAvatarId = val[5];
		String updatedDate = val[6];

		status = secretfriendDao.updateDeviceGeneralSettings(emailId, udid,
				password, pin, deviceName, defaultAvatarId, updatedDate);
		return status;
	}

	@POST
	@Path("/unregisterDevice")
	@Produces("text/plain")
	public boolean unregisterDevice(String input) {
		boolean status;
		String val[] = input.split("~");
		status = secretfriendDao.unregisterDevice(val[0], val[1]);
		return status;
	}

	@POST
	@Path("/updateDeviceMessage")
	@Produces("text/plain")
	public boolean updateDeviceMessage(String input) {
		boolean status;
		String val[] = input.split("~");
		String devUdid = val[0];
		String defaultMessage = val[1];
		int avatarId = Integer.parseInt(val[2]);
		status = secretfriendDao.updateDeviceMessage(devUdid, defaultMessage,
				avatarId);
		return status;
	}
	
	
	
	
	@POST
	@Path("/deviceRegisterorNot")
	@Produces("text/plain")
	public String deviceRegisterorNot(String input) {
		String status;
		status = secretfriendDao.deviceRegisterorNot(input);
		return status;
	}

	//============================
	
	/*
	 *     Changes by Biz4Solutions
	 * 
	 */
	
	
	/* Replacement for validateLogin() */
	@POST
	@Path("/signin")
	@Consumes("text/plain")
	@Produces("application/json")
	@PermitAll
	public Map<String, Object> signin(String value) {
		
		Map<String, Object> responseObj = null;
		
		if(checkNullUndefined(value,2)) {
			String val[] = value.split("~");
			System.out.println(" user :" + val[0] + "\t pass " + val[1]);
			responseObj = secretfriendDao.signin(val[0], val[1]);
			System.out.println("====================== dto : "+ responseObj);
		}else{
			responseObj = getResponseMessage("Please enter username and password.",false);
		}
		return responseObj;
	}
	
	/* Replacement for registration() 
	 * 
	 * But this web service is not required,that's why it is incomplete.
	 * 
	 * */
	@POST
	@Path("/signUp")
	@Consumes("text/plain")
	@Produces("application/json")
	@PermitAll
	public Map<String, Object> signUp(String value) {
		
		Map<String, Object> responseObj = null;
		if(value != null) {
			String val[] = value.split("~");
			if(val.length == 7) {
				
				if (val[2].equals("true")) {
					val[2] = "1";
				} else {
					val[2] = "0";
				}
		
				responseObj = secretfriendDao.signUp(val[0], val[1], val[2],
						val[3], val[4], Integer.parseInt(val[5]), val[6], this);				
			}else{
				responseObj = getResponseMessage("Some parameter missing.Please try again.",false);
			}
		}else{
			responseObj = getResponseMessage("Value required.Please try again.",false);
		}
		return responseObj;
	}
	
	/* Replacement for getDeviceLst() */
	@POST
	@Path("/getAllDevices")
	@Produces("application/json")
	/*@PermitAll*/
	public Map<String, Object> getAllDevices(String emailId) {
		
		Map<String, Object> responseObj = null;
		
		System.out.println(" emailId : "+ emailId);
		
		if(checkNullUndefined(emailId,1)) {
			System.out.println(" in if : ");
			responseObj =  secretfriendDao.getAllDevices(emailId);
		}else{
			System.out.println(" in else ");
			responseObj = getResponseMessage("No data available.",false);
		}
		return responseObj;
	}
	
	/* Replacement for getAvatarLst() */
	@POST
	@Path("/listAvatar")
	@Produces("application/json")
	/*@PermitAll*/
	public Map<String, Object> listAvatar() {
		return secretfriendDao.listAvatar();
	}
	
	@POST
	@Path("/logout")
	@Produces("application/json")
	/*@PermitAll*/
	public Map<String, Object> logout(@Context HttpServletRequest req) {
		return secretfriendDao.logout(req);
	}
	
	/* Replacement for insertMessage() */
	@POST
	@Path("/insertMsg")
	@Produces("application/json")
	/*@PermitAll*/
	public Map<String, Object> insertMsg(String value) {
		
		Map<String, Object> responseObj = null;
		
		if(checkNullUndefined(value,6)) {
			System.out.println("value : "+value);
			String val[] = value.split("~");

			System.out.println("val : "+val);

			String device_id = val[1].trim();
			if (device_id.contains("##")) {
	
				String[] strDeviceArray = device_id.split("##");
				for (int i = 0; i < strDeviceArray.length; i++) {
					String dev_id = strDeviceArray[i];
					String udid = secretfriendDao.getUDID(dev_id);
					responseObj = secretfriendDao.insertMsg(val[0], udid, val[2], val[3],
							val[4], val[5], this);
				}
			} else {
				String udid = secretfriendDao.getUDID(val[1]);
				responseObj = secretfriendDao.insertMsg(val[0], udid, val[2], val[3],
						val[4], val[5], this);
			}			
		}else{
			responseObj = getResponseMessage("Select options to send message.",false);
		}
		return responseObj;
	}
	
	public Map<String, Object> getResponseMessage(String msg,boolean result) {
		Map<String, Object> responseObj = new HashMap<String, Object>();
		responseObj.put("data", msg);
		responseObj.put("success", result);
		return responseObj;
	}
	
	/* Replacement for updatePassword() */
	@POST
	@Path("/changePassword")
	@Produces("application/json")
	@Consumes("text/plain")
	/*@PermitAll*/
	public Map<String, Object> changePassword(String value) {
		
		Map<String, Object> responseObj = null;
		if(checkNullUndefined(value,2)) {
			String val[] = value.split("~");
			System.out.println("updatedPassword Value length :" + val.length + " user :" + val[0] + " pass :" + val[1]);
			responseObj = secretfriendDao.changePassword(val[0], val[1]);
		}else{
			responseObj = getResponseMessage("Please try again.",false);
		}
		return responseObj;
	}
	
	/* Replacement for updateDeviceGeneralSettings() */
	@POST
	@Path("/changeDeviceGeneralSettings")
	@Produces("application/json")
	@Consumes("text/plain")
	public Map<String, Object> changeDeviceGeneralSettings(String input) {
		
		Map<String, Object> responseObj = null;
		if(checkNullUndefined(input,7)) {
			String val[] = input.split("~");
			String emailId = val[0];
			String old_udid = val[1];
			String new_udid = val[2];
			String deviceName = val[3];
			String defaultAvatarId = val[4];
			String defaultMessage = val[5];
			String defaultVoice = val[6];
			System.out.println(" defaultVoice: "+defaultVoice);
			
			responseObj = secretfriendDao.changeDeviceGeneralSettings(emailId, old_udid,new_udid,deviceName, defaultAvatarId, defaultMessage, defaultVoice);
		}else{
			responseObj = getResponseMessage("Select options to change default settting.",false);
		}
		return responseObj;
	}
	
	public boolean checkNullUndefined(String value,int length) {
		boolean isOk = true;
		System.out.println("Input value = "+value+" Input length = "+length);
		String doubleQuote = "";
		if(value == null || value.equalsIgnoreCase(doubleQuote) || value.length() == 0 || value.equalsIgnoreCase("null") || value.equalsIgnoreCase("undefined")) {
			isOk = false;
			System.out.println("value == null");
		}else{
			String val[] = value.split("~");
			if(val.length < length) {
				isOk = false;
				System.out.println("value length minimum : Actual length : "+val.length);
			} else {
				for(String obj : val) {
					if(obj == null || obj.equalsIgnoreCase("null") || obj.equalsIgnoreCase("undefined")) {
						isOk = false;
						System.out.println("value == null/undefined");
						break;
					}
				}
			}
		}
		return isOk;
	}
}