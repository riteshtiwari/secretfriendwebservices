package com.cgs.ws.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.DatatypeConverter;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.util.Base64;

import com.cgs.ws.pu.Avatar;
import com.cgs.ws.pu.AvatarHeadIcon;
import com.cgs.ws.pu.Device;
import com.cgs.ws.pu.InstantMessages;
import com.cgs.ws.pu.Language;
import com.cgs.ws.pu.Users;
import com.cgs.ws.pu.VideoMessage;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import flexjson.JSONSerializer;

/*
 * @author : Naveed	
 * */

@Stateless
@LocalBean
public class SecretFrientDAO {

	@PersistenceContext
	private EntityManager em;
	

	public String validateLogin(String user, String password) {
		String sql = "Select userName from Users where emailId='" + user
				+ "' and password='" + password + "'";
		Query query = em.createNativeQuery(sql);

		String res;
		try {
			res = query.getSingleResult().toString();
			System.out.println("REs \t" + res);
		} catch (Exception e) {
			System.out.println("Exception " + e);
			res = null;
		}
		return res;
	}

	public Users validate(String user, String password) {

		String sql = "Select u from Users u where emailId='" + user
				+ "' and password='" + password + "'";
		Query query = em.createQuery(sql);

		ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
		
		if (lst.size() == 0) {
			
			//check the user is register or not..
			String str="Select u from Users u where emailId='" + user + "'";
			Query query1 = em.createQuery(str);

			ArrayList<Users> lstofUser = (ArrayList<Users>) query1.getResultList();
			
			if(lstofUser.size()==0)
			{
				System.out.println("This user is not register");
				return null;
				
			}else {
				
				System.out.println("This user is Register but Not valid password");
				return lstofUser.get(0);
			}
			
		} else {
			System.out.println("user exists");
			return lst.get(0);
		}

	}

	/*
	 * public Users validate(String user,String password,String udid) {
	 * 
	 * String sqlDev =
	 * "SELECT d FROM Device d WHERE emailid='"+user+"' AND udid='"+udid+"'";
	 * Query queryDev= em.createQuery(sqlDev); ArrayList<Device> lstDev =
	 * (ArrayList<Device>) queryDev.getResultList();
	 * 
	 * if(lstDev.size()==0) {
	 * 
	 * } String sql =
	 * "Select u from Users u where emailId='"+user+"' and password='"
	 * +password+"'"; Query query = em.createQuery(sql);
	 * 
	 * ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
	 * if(lst.size()==0) { return null; }else { //check this user having
	 * register this device or not.....
	 * 
	 * return lst.get(0);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return null;
	 * 
	 * }
	 */

	public boolean updatePassword(String emailid, String passwrd) {
		// TODO Auto-generated method stub
		String sql = "UPDATE Users SET PASSWORD ='" + passwrd
				+ "' WHERE emailId ='" + emailid + "'";
		Query query = em.createNativeQuery(sql);
		int status = query.executeUpdate();
		boolean res;
		if (status == 1)
			res = true;
		else
			res = false;
		return res;
	}

	public List<InstantMessages> getAlerts(String userid, String deviceid) {

		String sql = "SELECT * FROM instantmessages WHERE parent_id=" + userid
				+ " AND device_id=" + deviceid;
		Query query = em.createNativeQuery(sql);

		ArrayList<InstantMessages> lst = (ArrayList<InstantMessages>) query
				.getResultList();
		System.out.println("getCustomerList size" + lst.size());
		return lst;
	}

	public InstantMessages getAlert(String userid, String deviceid) {
		InstantMessages dto;
		try {
			// String sql =
			// "SELECT i FROM InstantMessages i WHERE i.parent_id="+userid+" AND i.device_id="+deviceid;
			// String sql =
			// "SELECT parent_id,device_id,message_info,voice,a.avatar_images AS charactersize FROM InstantMessages i,Avatar a WHERE i.avatar_image_id=a.id AND i.status='0' and i.parent_id="+userid+" AND i.device_id='"+deviceid+"' order by i.id desc limit 1";

			String sql = "SELECT parent_id,device_id,message_info,voice,a.avatargifurl AS charactersize FROM InstantMessages i,AvatarHeadIcon a WHERE i.avatar_image_id=a.avatarimageid AND i.status='0' and i.parent_id="
					+ userid
					+ " AND i.device_id='"
					+ deviceid
					+ "' order by i.id desc limit 1";
			Query query = em.createNativeQuery(sql);
			Object[] lst = (Object[]) query.getSingleResult();

			dto = new InstantMessages();
			System.out.println("lst[0] parent id  :: " + (Integer) lst[0]);
			dto.setParent_id((Integer) lst[0]);
			dto.setDevice_id((String) lst[1]);
			dto.setMessage_info((String) lst[2]);
			dto.setVoice((String) lst[3]);
			dto.setImgUrl((String) lst[4]);

			System.out.println("dto.getParent_id():: " + dto.getParent_id());

		} catch (Exception e) {
			dto = null;
			System.out.println("Exception :" + e);
		}
		return dto;
	}

	public List<Device> getDeviceLst(String emailId) {
		String sql = "SELECT d FROM Device d WHERE emailid='" + emailId + "'";
		Query query = em.createQuery(sql);

		// Query query= em.createNativeQuery(sql);
		ArrayList<Device> lst = (ArrayList<Device>) query.getResultList();

		return lst;
	}

	public List<Avatar> getAvatarLst() {
		String sql = "SELECT a FROM Avatar a ";
		Query query = em.createQuery(sql);
		ArrayList<Avatar> lst = (ArrayList<Avatar>) query.getResultList();
		return lst;
	}

	public List<VideoMessage> getVedioLst(int id, int deviceId) {
		String sql = "SELECT v FROM VideoMessage v where parent_id=" + id
				+ " and device_id='" + deviceId + "' and status=1";
		Query query = em.createQuery(sql);
		ArrayList<VideoMessage> lst = (ArrayList<VideoMessage>) query
				.getResultList();
		return lst;
	}

	public List<Language> getLanguageLst() {
		String sql = "SELECT l FROM Language l";
		Query query = em.createQuery(sql);
		ArrayList<Language> lst = (ArrayList<Language>) query.getResultList();
		return lst;
	}

	public Device insertDeviceInfo(String parentid, String deviceid) {

		Device dto = new Device();

		String sql = "SELECT d FROM Device d  WHERE parent_id=" + parentid
				+ " and udid='" + deviceid + "'";
		Query query = em.createQuery(sql);
		ArrayList<Device> lst = (ArrayList<Device>) query.getResultList();

		if (lst.size() > 0) {
			dto = lst.get(0);
		} else {
			String sql1 = "INSERT INTO Device (parent_id, udid, devicename, STATUS) VALUES "
					+ " (" + parentid + ", '" + deviceid + "', '', '0');";
			Query query1 = em.createNativeQuery(sql1);
			int val = query1.executeUpdate();

			// dto.setParent_Id(Integer.parseInt(parentid));
			dto.setUdid(deviceid);
		}
		return dto;
	}

	public boolean updateDeviceName(String parentid, String deviceid,
			String name) {

		boolean status = false;

		String sql = "UPDATE Device SET devicename ='" + name
				+ "' WHERE parent_id =" + parentid + " AND udid='" + deviceid
				+ "'";
		Query query = em.createNativeQuery(sql);
		int val = query.executeUpdate();

		if (val == 1)
			status = true;
		return status;
	}

	public boolean doRegister(String parentid, String deviceid, String statusVal) {
		boolean status = false;

		String sql = "UPDATE Device SET status ='" + statusVal
				+ "' WHERE parent_id =" + parentid + " AND udid='" + deviceid
				+ "'";
		Query query = em.createNativeQuery(sql);
		int val = query.executeUpdate();

		if (val == 1)
			status = true;
		return status;
	}

	public boolean insertMessage(String parent_id, String device_id,
			String message_info, String voice, String avatar_image_id,
			String createdDate, MessageRestService messageRestService) {

		System.out.println("parent_id :: " + parent_id);
		System.out.println("device_id :: " + device_id);
		System.out.println("message_info :: " + message_info);
		System.out.println("voice :: " + voice);
		System.out.println("avatar_image_id :: " + avatar_image_id);
		System.out.println("createdDate :: " + createdDate);

		boolean status = false;

		String sql = "INSERT INTO InstantMessages (parent_id,device_id,message_info,voice,avatar_image_id,status,created_date) "
				+ " VALUES('"
				+ parent_id
				+ "', '"
				+ device_id
				+ "', '"
				+ message_info
				+ "', '"
				+ voice
				+ "', '"
				+ avatar_image_id
				+ "', '0','" + createdDate + "');";
		Query query = em.createNativeQuery(sql);
		int val = query.executeUpdate();

		System.out.println("val :: " + val);
		if (val == 1) {
			status = true;
			System.out.println("parent_id :: " + parent_id);
			System.out.println("device_id :: " + device_id);

			InstantMessages im = messageRestService.getAlerts(parent_id + "~"
					+ device_id);
			String vale = new JSONSerializer().exclude("*.class")
					.include("values.*").serialize(im);

			// call APN test..
			System.out.println("i am calling apn json=:: " + vale);
			apnTest(vale, device_id, im.getMessage_info());
		}

		return status;
	}

	public void apnTest(String json, String device_id, String alert) {

		try {

			// Production..old...
			/*ApnsService service = APNS
					.newService()
					.withCert("/opt/jboss-as-7.1.1.Final/SF/SFCertRitesh.p12",
							"game@123").withSandboxDestination().build();*/
			
			
			// Production..New 
			
			/*ApnsService service = APNS
					.newService()
					.withCert("/opt/jboss-as-7.1.1.Final/SF/PushNotifyKey.p12",
							"sfpush123").withSandboxDestination().build();*/
			
			System.out.println("Development Call ::");
			
			ApnsService service = APNS
					.newService()
					
					// Local
					.withCert("E:/Java_Dev/SF_Webservice_Workspace/SecretFriendWS/certificate/Certificates.p12","sfplus123")

					// Client AWS
					//.withCert("/opt/jboss-as-7.1.1.Final/certificate/Exported_Certificates_APNS_05_OCT.p12","sfplus123")	

					//.withProductionDestination().build();
			        .withSandboxDestination().build();
			
			System.out.println("Development Call after::"+ service);

			String payload = APNS.newPayload().alertBody(alert)
					.sound("default").customField("custom", json).build();
			
			System.out.println("payload Call ::");

			// String payload = APNS.newPayload()
			// .customField("apns",json).build();

			// String payload = APNS.newPayload()
			// .alertBody("hiiiii").customField("apns", json).build();

			// String token =
			// "30ccd4ed1cf753ace3d7bb2c59c91e4d1f4b0dbf8eeb5674db0786fad8fce732";
			System.out.println("device_id :: " + device_id);
			String token = device_id; //1234def112aa3
			service.push(token, payload);
		} catch (Exception e) {
			System.out.println("Exception Occured in APN Push notification :: "
					+ e);
			e.printStackTrace();
		}
	}

	public boolean disableAlert(int parentid, String deviceid) {
		boolean status = false;

		String sql = "UPDATE InstantMessages SET status ='1' WHERE parent_id ="
				+ parentid + " AND device_id='" + deviceid + "'";
		Query query = em.createNativeQuery(sql);
		int val = query.executeUpdate();

		if (val == 1)
			status = true;
		return status;
	}

	public String insertUser(String emailId, String password, String device,
			int pin, String status, String createdDate, String updatedDate,
			String udid, int avatarimageId, String defaultmessage,
			MessageRestService messageRestService) {
		
		System.out.println("inside insert User");
		String returnStatus = "";

		// check already having user or not.
		String sql = "SELECT u FROM Users u  WHERE emailId='" + emailId + "'";
		Query query = em.createQuery(sql);
		ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
		
		if (lst.size() > 0) {
			

			// check UDID for matching with emailId (i.e)already registered
			// device or not..
			
			String checkDevice=deviceRegisterorNot(udid);
			
			if(checkDevice.equals("exists"))
			{
				returnStatus = "exists";
				
			}else {
				
				System.out.println("if condition not exists this account so insert ");
				
				// Email id is already exitst in the DB..so we need to register
				// device name and UDID into this email account...
				// Insert device details into Device DB..
				int isDefaultDevice = 0;
				String sqlInsertDevice = "INSERT INTO Device (emailid,udid,devicename,defaultavatarid,defaultmessage,isdefaultdevice) VALUES('"+ emailId + "', '" + udid + "', '" + device + "', '" + avatarimageId + "', '" + defaultmessage +"', '" + isDefaultDevice + "');";
				Query query2 = em.createNativeQuery(sqlInsertDevice);
				int valDev = query2.executeUpdate();

				if (valDev == 1) {
					/*
					 * returnStatus=messageRestService.emailAuthendication(emailId
					 * ); System.out.println("returnStatus :: "+returnStatus);
					 */
					returnStatus = "Device Registered Successfully";
				}
			}

		} else {
			
			//user table it is not registered...and we need to test in device table also...wheather it is installed or not..
			
			System.out.println("check this device is register orr not :: ");
			String checkDevice=deviceRegisterorNot(udid);
			
			System.out.println("checkDevice :: "+checkDevice);
			
			if(checkDevice.equals("not"))
			{
				
				System.out.println("Else condition insert into DB since it is not registered..");
				
			//then insert code as new user....because device is not yet to registred...
			
			String sqlInsert = "INSERT INTO Users (emailId,password,pin,activationstatus,createdDate,updatedDate)"
					+ "VALUES('"
					+ emailId
					+ "', '"
					+ password
					+ "', "
					+ pin
					+ ", '"
					+ status
					+ "', '"
					+ createdDate
					+ "', '"
					+ updatedDate + "');";
			Query query1 = em.createNativeQuery(sqlInsert);
			int val = query1.executeUpdate();
			if (val == 1) {
				// returnStatus="success";
				// Call email activation..

				// Insert device details into Device DB..
				int isDefaultDevice = 1;
				String sqlInsertDevice = "INSERT INTO Device (emailid,udid,devicename,defaultavatarid,defaultmessage,isdefaultdevice) VALUES('" + emailId + "', '"+ udid+ "', '" + device + "', '" + avatarimageId + "', '" + defaultmessage + "', '"+ isDefaultDevice + "');";
				Query query2 = em.createNativeQuery(sqlInsertDevice);
				int valDev = query2.executeUpdate();

				if (valDev == 1) {
					
					returnStatus = messageRestService
							.emailAuthendication(emailId);
					
					System.out.println("returnStatus :: " + returnStatus);
				}

			}
			
		}else
		{
			System.out.println("Else condition :: this device is already registered.with another account..so it exists.");
			
			returnStatus = "exists";
		}
		}
		return returnStatus;
	}

	public String emailAuthentication(String email) {

		String status = "";
		status = sendEmail(email, "emailactivate", "");
		// status=sendEmailServerside(email,"emailactivate");
		System.out.println("status :: email send successfully :: " + status);
		return status;
	}

	public String sendEmail(String email, String check, String value) {

		System.out.println("called java mail");
		String from = "kamal.nazar2014@gmail.com";
		String to = email;
		final String user = "kamal.nazar2014@gmail.com";
		final String password = "Passw0rd@0123";

		try {

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, password);
						}
					});

			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from, "SecretFriend"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to, "ToName"));

			if (check.equals("emailactivate")) {
				// Set the subject
				message.setSubject("Activate Your SecretFriend Registration");

				// Local
				String link = "http://192.168.1.33:8080/SecretFriendWS/emailactivate.html?emailId="
						+ email;
				
				// Biz4 AWS
				/*String link = "http://ec2-54-243-39-214.compute-1.amazonaws.com:8080/SecretFriendWS/emailactivate.html?emailId="
						+ email;*/
				
				// Client AWS
				/*String link = "http://ec2-52-27-60-75.us-west-2.compute.amazonaws.com:8080/SecretFriendWS/emailactivate.html?emailId="
						+ email;*/
				
				System.out.println("link :: " + link);
				
				message.setContent(
						"<h1>Hi,<br> Your Secret Friend Registration Completed Successfully<br>Please <a href="
								+ link
								+ ">click here</a> to activate your account <br> <b>Thank you</b></h1>",
						"text/html");
			} else if (check.equals("forgotpassword")) {
				System.out.println("check :: " + check);
				message.setSubject("Secret Friend");
				message.setContent(
						"<h1>Hi,<br> Your Secret Friend Password <br>Password : "
								+ value + "<br><b>Thank you</b></h1>",
						"text/html");
			} else if (check.equals("forgotpin")) {
				message.setSubject("Secret Friend");
				message.setContent(
						"<h1>Hi,<br> Your Secret Friend Pin Number <br>Pin : "
								+ value + "<br><b>Thank you</b></h1>",
						"text/html");
			}

			// // Send message
			System.out.println("proceed to send");
			new Thread() {
				public void run() {
					try {
						Transport.send(message);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();

			System.out.println("Send Successfully");
		} catch (AuthenticationFailedException e) {
			System.out.println("AuthenticationFailedException :: "
					+ e.toString());
			e.toString();
		} catch (MessagingException e) {
			System.out.println("MessagingException :: " + e.toString());
			e.toString();
		}
		/*
		 * catch (UnsupportedEncodingException e) {
		 * System.out.println("UnsupportedEncodingException :: "+e.toString());
		 * e.toString(); }
		 */
		catch (Exception e) {
			System.out.println("Exception :: " + e.toString());
			e.toString();
		}
		return "success";
	}

	static class PopupAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("kamal.nazar2014@gmail.com",
					"Passw0rd@0123");
		}
	}

	// Email activation...
	public String emailActivate(String emailId) {
		System.out.println("emailId :" + emailId);
		Users users = new Users();
		String status = "";
		String sql = "SELECT u FROM Users u  WHERE emailId='" + emailId + "'";
		System.out.println("sql :: " + sql);
		Query query = em.createQuery(sql);
		ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
		if (lst.size() == 0) {
			status = "invalidEmail";
		} else {
			users = lst.get(0);
			if (users.getActivationstatus()) {
				status = "Already Activated";
			} else {
				// update status into true as value 1..

				String sqlupdate = "UPDATE Users SET activationstatus ='1' WHERE emailId ='"
						+ emailId + "'";
				Query queryupdate = em.createNativeQuery(sqlupdate);
				int res = queryupdate.executeUpdate();
				if (res == 1)
					status = "Successfully Activated";
				else
					status = "Failed to Activate";
			}
		}
		return status;

	}

	public String getforgetpassword(String emailId) {
		String status = "";
		String sql = "SELECT u.password,u.emailId FROM Users u WHERE u.emailId='"
				+ emailId + "'";
		Query query = em.createNativeQuery(sql);
		try {
			Object[] lst = (Object[]) query.getSingleResult();
			System.out.println("lst :: " + lst);
			if (lst.length > 0) {
				System.out.println("lst[0].toString() :: " + lst[0].toString());
				status = sendEmail(emailId, "forgotpassword", lst[0].toString());
				System.out.println("status for getPassword :: " + status);

			} else {
				status = "EmailId Does not Exists";
			}
		} catch (Exception e) {
			status = "EmailId Does not Exists";
		}

		return status;
	}

	public List<Avatar> getDeviceAvatarImage(String emailId, String udid) {

		System.out.println("Device Avatar calling " + "email id :: " + emailId
				+ "udid :: " + udid);

		String defaultMessage;
		String sql = "SELECT d FROM Device d WHERE emailid='" + emailId
				+ "' AND udid='" + udid + "'";
		Query query = em.createQuery(sql);
		ArrayList<Device> lst = (ArrayList<Device>) query.getResultList();
		
		if (lst.size() == 0) {
			
			return null;
			
		} else {
			
			int avatarId = lst.get(0).getDefaultavatarid();
			defaultMessage = lst.get(0).getDefaultmessage();
			System.out.println("avatarId :: " + avatarId);

			String sql1 = "SELECT a FROM Avatar a WHERE id=" + avatarId;
			Query query1 = em.createQuery(sql1);
			ArrayList<Avatar> lstofAvatar = (ArrayList<Avatar>) query1
					.getResultList();
			if (lstofAvatar.size() != 0) {
				lstofAvatar.get(0).setAvatardefaultmessage(defaultMessage);
				return lstofAvatar;
			} else {
				return null;
			}

		}
	}

	public String getforgotpin(String emailId) {

		String status = "";
		String sql = "SELECT u.pin,u.emailId FROM Users u WHERE u.emailId='"
				+ emailId + "'";
		Query query = em.createNativeQuery(sql);
		try {
			Object[] lst = (Object[]) query.getSingleResult();
			System.out.println("lst for pin:: " + lst);
			if (lst.length != 0) {
				System.out.println("pin :: " + String.valueOf(lst[0]));
				String pinValue = String.valueOf(lst[0]);

				status = sendEmail(emailId, "forgotpin", pinValue);
				System.out.println("status for getPin:: " + status);

			} else {
				System.out.println("else part");
				status = "EmailId Does not Exists";
			}
		} catch (Exception e) {
			status = "EmailId Does not Exists";
		}

		return status;

	}

	public List<AvatarHeadIcon> getAvatarHeadIconLst() {

		String sql = "SELECT a FROM AvatarHeadIcon a ";
		Query query = em.createQuery(sql);
		ArrayList<AvatarHeadIcon> lst = (ArrayList<AvatarHeadIcon>) query
				.getResultList();
		return lst;
	}

	public Users checkEmailAccount(String emailId) {

		/*String sql = "Select u from Users u where emailId='" + emailId + "'";
		Query query = em.createQuery(sql);

		ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
		if (lst.size() == 0) {
			return null;
		} else {
			System.out.println("lst.get(0).getpassword :: "+ lst.get(0).getPassword());
			System.out.println("lst.get(0).getPin() :: " + lst.get(0).getPin());
			return lst.get(0);
		}*/
		return null;
	}

	public boolean checkDeviceName(String emailId, String devicename) {

		String sql = "Select d from Device d where emailId='" + emailId
				+ "' and devicename='" + devicename + "'";
		Query query = em.createQuery(sql);

		ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
		if (lst.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean updateDeviceGeneralSettings(String emailId, String udid,
			String password, int pin, String deviceName,
			String defaultAvatarId, String updatedDate) {

		boolean res = false;
		String sql = "UPDATE Users SET PASSWORD ='" + password + "',pin=" + pin
				+ ",updatedDate='" + updatedDate + "' WHERE emailId ='"
				+ emailId + "'";
		Query query = em.createNativeQuery(sql);
		int status = query.executeUpdate();

		if (status == 1) {
			System.out.println("user table updated Successfully");
			// Update the Device...
			String sqlDevice = "UPDATE Device SET devicename ='" + deviceName
					+ "', defaultavatarid=" + defaultAvatarId
					+ " WHERE emailId ='" + emailId + "' AND udid='" + udid
					+ "'";
			Query queryDev = em.createNativeQuery(sqlDevice);
			int statusDevice = queryDev.executeUpdate();

			if (statusDevice == 1) {
				System.out.println("Device table updated Successfully");
				res = true;
			}
		} else {
			System.out.println("Both user table updated failed");
			res = false;
		}

		return res;

	}

	public boolean unregisterDevice(String emailId, String udid) {

		boolean res = false;

		String sql = "DELETE FROM Device WHERE udid='" + udid
				+ "' AND emailid='" + emailId + "'";
		Query query = em.createNativeQuery(sql);
		int se = query.executeUpdate();
		System.out.println("se delete " + se);
		if (se == 1) {
			//check wheather is there any other device can register with this mail id....
			
			String sql2="SELECT * FROM Device WHERE emailid='"+emailId+"'";
			Query query2 = em.createNativeQuery(sql2);
			ArrayList<Users> lst = (ArrayList<Users>) query2.getResultList();
			if (lst.size() == 0) {
			String strval="DELETE FROM Users WHERE emailid='"+emailId+"'";
			Query query3 = em.createNativeQuery(strval);
			int out = query3.executeUpdate();
			if(out==1)
			{
				res = true;
			}
			}else
			{
				res = true;
			}
			
			
		} else {
			res = false;
		}
		return res;
	}

	public boolean updateDeviceMessage(String udid, String defaultMessage,
			int avatarId) {

		boolean status = false;
		String sqlDevice = "UPDATE Device SET defaultmessage='"
				+ defaultMessage + "',defaultavatarid='"+avatarId+"' WHERE udid='" + udid + "'";
		Query queryDev = em.createNativeQuery(sqlDevice);
		int statusDevice = queryDev.executeUpdate();

		if (statusDevice == 1) {
			System.out.println("Device table Message updated Successfully");

			String sqlAvatar = "UPDATE Avatar SET avatardefaultmessage='"
					+ defaultMessage + "' WHERE id=" + avatarId + "";
			Query queryAvatar = em.createNativeQuery(sqlAvatar);
			int statusAvatar = queryAvatar.executeUpdate();
			if (statusAvatar == 1) {
				System.out.println("Avatar message is Updated Successfully");
				status = true;
			}

		}
		return status;
	}

	public Device checkDeviceRegisterStatus(String imei) {
		/*System.out.println(" -------- imei : "+ imei);
		String sql = "Select u from Device u where udid='" + imei + "'";
		Query query = em.createQuery(sql);

		ArrayList<Device> lst = (ArrayList<Device>) query.getResultList();
		if (lst.size() == 0) {
			return null;
		} else {
			System.out.println("lst.get(0).getpassword :: "
					+ lst.get(0).getDeviceName());
			return lst.get(0);
		}*/
		return null;
	}

	public String deviceRegisterorNot(String udid) {
		
		//check this user..
		System.out.println("udid :: "+udid);
		String returnStatusVal="";
		
		String sqlDev = "SELECT d FROM Device d  WHERE udid='" + udid + "'";
		
		Query queryDev = em.createQuery(sqlDev);
		
		ArrayList<Device> lstDev = (ArrayList<Device>) queryDev.getResultList();
		
		if(lstDev.size()>0)
		{
			System.out.println("device already exists with another account ");
			returnStatusVal="exists";
		}else
		{
			System.out.println("device not register yet ::");
			returnStatusVal="not";
		}
		
		return returnStatusVal;
		
	}
	
	//============================
	
	/*
	 *     Changes by Biz4Solutions
	 * 
	 */
	
	
	/* Replacement for validateLogin() */
	public Map<String, Object> signin(String user, String password) {

		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
				
		/*String sql = "Select u from Users u where emailId='" + user + "' and password='" + password + "'";*/
		String sql = "Select u from Users u where emailId=? and password=?";
		Query query = em.createQuery(sql);
		query.setParameter(1, user);
		query.setParameter(2, password);
		ArrayList lst = (ArrayList) query.getResultList();
		
		System.out.println("lst.size() : "+ lst.size());
		if (lst.size() == 0) {
			
			//check the user is register or not..
			/*String str="Select u from Users u where emailId='" + user + "'";*/
			String str="Select u from Users u where emailId=?";
			Query query1 = em.createQuery(str);
			query1.setParameter(1, user);
			ArrayList<Users> lstofUser = (ArrayList<Users>) query1.getResultList();
			
			if(lstofUser.size()==0) {
				message =  "User does not exists.";
			}else {
				message =  "Invalid username or password.";
			}
			returnObj.put("success", false);
			returnObj.put("data", message);
		} else {
			
			returnObj.put("success", true);
			returnObj.put("data", lst);
			lst.add(getDeviceDetails(user));
			//lst.add("Authorization:"+encodeUserNameAndPassword(user,password));
		}
		return returnObj;
	}
	
	public Device getDeviceDetails(String emailId) {
		
		String str="Select d from Device d where emailId=? AND isdefaultdevice=?";
		Query query1 = em.createQuery(str);

		query1.setParameter(1,emailId);
		query1.setParameter(2,true);
		ArrayList<Device> deviceObject = (ArrayList<Device>) query1.getResultList();
		
		if(deviceObject == null || deviceObject.size()==0) {
			return null;
		}
		return deviceObject.get(0);
		
	}
	/* Replacement for insertUser() */
	public Map<String, Object> signUp(String emailId, String password,String status, String createdDate, String updatedDate,
									  int avatarimageId, String defaultmessage, MessageRestService messageRestService) {
		
		System.out.println("inside insert User");
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean resultFlag = false;
		Date inputDate = null;
		
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
		try{
			inputDate = formatter.parse(createdDate);
		}catch(Exception e) {
			System.out.println("Error Message : "+e.getMessage());
			return returnObj = messageRestService.getResponseMessage("Invalid created date format.Please try again.", resultFlag);
		}
		System.out.println("createdDate : "+inputDate);
		try{
			inputDate = formatter.parse(updatedDate);
		}catch(Exception e) {
			System.out.println("Error Message : "+e.getMessage());
			return returnObj = messageRestService.getResponseMessage("Invalid updated date format.Please try again.", resultFlag);
		}
		System.out.println("updatedDate : "+updatedDate);
		
		// check already having user or not.
		String sql = "SELECT u FROM Users u  WHERE emailId='" + emailId + "'";
		Query query = em.createQuery(sql);
		ArrayList<Users> lst = (ArrayList<Users>) query.getResultList();
		
		if (lst.size() > 0) {
			System.out.println("User exists with "+emailId);
			message = "Email address is already registered.";
		} else {
			String sqlInsert = "INSERT INTO Users (emailId,password,activationstatus,createdDate,updatedDate)"
					+ "VALUES('"
					+ emailId
					+ "', '"
					+ password
					+ "', '"
					+ status
					+ "', '"
					+ createdDate
					+ "', '"
					+ updatedDate + "');";
			Query query1 = em.createNativeQuery(sqlInsert);
			int val = query1.executeUpdate();
			if (val == 1) {
				
				String sqlInsertDevice = "INSERT INTO Device (emailid,defaultavatarid,defaultmessage)"
						+ "VALUES('"
						+ emailId
						+ "', "
						+ avatarimageId
						+ ", '"
						+ defaultmessage +"');";
				Query query2 = em.createNativeQuery(sqlInsertDevice);
				int valDev = query2.executeUpdate();
				if(valDev == 1) {
					// Call email activation..
		
					String returnStatus = messageRestService
							.emailAuthendication(emailId);
					
					System.out.println("email activation send mail returnStatus :: " + returnStatus);
					resultFlag = true;
					message = "Registeration successfully done.";
					System.out.println("Registeration Successfully done.");
				}else{
					System.out.println("Error occured while inserting into device.");
					message = "Some error occured.Please try again.";
					// so delete user also which matches email address
					deleteUser(emailId);
				}
			}else{
				System.out.println("Error occured while Registeration.");
				message = "Some error occured.Please try again.";
			}
		}
		
		returnObj.put("success", resultFlag);
		returnObj.put("data",message);
		return returnObj;
	}
	
	/* Replacement for getDeviceLst() */
	public Map<String, Object> getAllDevices(String emailId) {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isDeviceListavailable = false;
		
		/*String sql = "SELECT d FROM Device d WHERE emailid='" + emailId + "'";*/
		String sql = "SELECT d FROM Device d WHERE emailid=?";
		Query query = em.createQuery(sql);
		query.setParameter(1, emailId);
		// Query query= em.createNativeQuery(sql);
		ArrayList<Device> lst = (ArrayList<Device>) query.getResultList();

		if (lst.size() == 0) {
			message = "No data available.";
			returnObj.put("data", message);
		}else{
			isDeviceListavailable = true;
			returnObj.put("data", lst);
		}
		returnObj.put("success", isDeviceListavailable);
		return returnObj;
	}
	
	/* Replacement for getAvatarLst() */
	public Map<String, Object> listAvatar() {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isAvatarListavailable = false;
		
		String sql = "SELECT a FROM Avatar a ";
		Query query = em.createQuery(sql);
		ArrayList<Avatar> lst = (ArrayList<Avatar>) query.getResultList();
		if (lst.size() == 0) {
			message = "No friend is available.";
			returnObj.put("data", message);
		}else{
			isAvatarListavailable = true;
			returnObj.put("data", lst);
		}
		returnObj.put("success", isAvatarListavailable);
		return returnObj;
	}
	
	public Map<String, Object> logout(HttpServletRequest request) {
		System.out.println("request obj :"+request);
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isSuccessfullyLogout = false;
		
		 HttpSession session = request.getSession();
		 System.out.println("session obj : "+session);
		 if(session != null) {
			 session.invalidate();
			 isSuccessfullyLogout = true;
			 message = "Logout successfully.";
		 }else{
			message = "Not able to logout,try again.";
		 }
		 returnObj.put("data", message);
		 returnObj.put("success", isSuccessfullyLogout);
		 return returnObj;
	}
	
	/* getUDID() */
	public String getUDID(String selectedDBId) {
		String udid = "";
		int dbID = Integer.parseInt(selectedDBId);
		
		String str="Select u from Device u where id='" + dbID + "'";
		Query query = em.createQuery(str);
		ArrayList<Device> lst = (ArrayList<Device>) query.getResultList();
		if (lst.size() == 0) {
			return "false";
		}else{
			Device d = lst.get(0);
			return d.getUdid();
		}
	}
	
	/* Replacement for insertMessage() */
	public Map<String, Object> insertMsg(String parent_id, String device_id,
			String message_info, String voice, String avatar_image_id,
			String createdDate, MessageRestService messageRestService) {

		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isMsgInserted = false;
		int parentID = 0;
		int avatarImageId = 0;
		
		System.out.println("parent_id :: " + parent_id);
		System.out.println("device_id :: " + device_id);
		System.out.println("message_info :: " + message_info);
		System.out.println("voice :: " + voice);
		System.out.println("avatar_image_id :: " + avatar_image_id);
		System.out.println("createdDate :: " + createdDate);

		try{
			parentID = Integer.parseInt(parent_id);
		}catch(Exception e) {
			return returnObj = messageRestService.getResponseMessage("Could not send message,try again.", isMsgInserted);
		}
		try{
			avatarImageId = Integer.parseInt(avatar_image_id);
		}catch(Exception e) {
			return returnObj = messageRestService.getResponseMessage("Could not send message,try again.", isMsgInserted);
		}
		
		/*String sql = "INSERT INTO InstantMessages (parent_id,device_id,message_info,voice,avatar_image_id,status,created_date) "+ " VALUES('"+ parentID+ "', '"+ device_id+ "', '"+ message_info+ "', '"+ voice+ "', '"+ avatarImageId+ "', '0','" + createdDate + "');";*/
		String sql = "INSERT INTO InstantMessages (parent_id,device_id,message_info,voice,avatar_image_id,status,created_date) VALUES(?, ?, ?, ?, ?, '0', ?);";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, parentID);
		query.setParameter(2, device_id);
		query.setParameter(3, message_info);
		query.setParameter(4, voice);
		query.setParameter(5, avatarImageId);
		query.setParameter(6, createdDate);
		int val = query.executeUpdate();

		System.out.println("val :: " + val);
		if (val == 1) {
			isMsgInserted = true;
			message = "Message sent successfully.";
			System.out.println("parent_id :: " + parent_id);
			System.out.println("device_id :: " + device_id);

			InstantMessages im = messageRestService.getAlerts(parent_id + "~"
					+ device_id);
			String vale = new JSONSerializer().exclude("*.class")
					.include("values.*").serialize(im);

			// call APN test..
			System.out.println("i am calling apn json=:: " + vale);
			apnTest(vale, device_id, im.getMessage_info());
		}else{
			message = "Could not send message,try again.";
		}

		returnObj.put("data", message);
		returnObj.put("success", isMsgInserted);
		return returnObj;
	}

	public Map<String, Object> deleteUser(String emailId) {
		
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isUserDeleteded = false;
		
		/*String strval="DELETE FROM Users WHERE emailid='"+emailId+"'";*/
		String strval="DELETE FROM Users WHERE emailid=?";
		Query query3 = em.createNativeQuery(strval);
		query3.setParameter(1, emailId);
		int result = query3.executeUpdate();
		if(result > 0) {
			isUserDeleteded = true;
			message = "Successfully deleted user.";
		}else{
			message = "Error occured while deleting user.Please try again.";
		}
		returnObj.put("data", message);
		returnObj.put("success", isUserDeleteded);
		return returnObj;
	}
	
	/* Replacement for updatePassword() */
	public Map<String, Object> changePassword(String emailid, String passwrd) {
		
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isPwdChanged = false;
		
		/*String sql = "UPDATE Users SET PASSWORD ='" + passwrd+ "' WHERE emailId ='" + emailid + "'";*/
		String sql = "UPDATE Users SET PASSWORD =? WHERE emailId =?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, passwrd);
		query.setParameter(2, emailid);
		int status = query.executeUpdate();
		if (status == 1) {
			isPwdChanged = true;
			message = "Successfully changed password.";
		}else {
			message = "User not found.Please try again.";
		}
		returnObj.put("data", message);
		returnObj.put("success", isPwdChanged);
		return returnObj;
	}
	
	public String encodeUserNameAndPassword(String user, String password) {
		String decodedString = null;
		String AUTHENTICATION_SCHEME = "Basic ";
		String usernameAndPassword = user + ":" + password;
		
		try {
			decodedString = new String(DatatypeConverter.printBase64Binary(usernameAndPassword.getBytes()));
		}catch(Exception e) {
			System.out.println("Exception in generating token : " + e.getMessage());
		}
	      String res = new String(DatatypeConverter.parseBase64Binary(decodedString));
	      System.out.println("decodedString : " + decodedString+ " res : " + res);
		return AUTHENTICATION_SCHEME + decodedString;
	}
	
	public String getToken(HttpRequest request) {
		
		String AUTHORIZATION_PROPERTY = "Authorization";
		
		//Get request headers
        final HttpHeaders headers = request.getHttpHeaders();
         
        //Fetch authorization header
        final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_PROPERTY);
         
        //If no authorization information present; block access
        if(authorization == null || authorization.isEmpty())
        {
            return null;
        }
        return authorization.get(0);
	}
	
	/* Replacement for updateDeviceGeneralSettings() */
	public Map<String, Object> changeDeviceGeneralSettings(String emailId, String old_udid,String new_udid,
			String deviceName,String defaultAvatarId,String defaultMessage, String defaultVoice) {

		Map<String, Object> returnObj = new HashMap<String, Object>();
		String message = null;
		boolean isDeviceSettingChanged = false;
		
		// Update the Device...
		String sqlDevice = "UPDATE Device SET isdefaultdevice=? WHERE emailId =? AND udid=?";
		
		Query queryDev1 = em.createNativeQuery(sqlDevice);
		queryDev1.setParameter(1, false);
		queryDev1.setParameter(2, emailId);
		queryDev1.setParameter(3, old_udid);
		int statusDevice = queryDev1.executeUpdate();
		if(statusDevice > 0) {
			String sqlDevice2 = "UPDATE Device SET devicename =?,defaultavatarid=?,defaultmessage=?,isdefaultdevice=?, defaultvoice=? WHERE emailId =? AND udid=?";
		
			Query queryDev2 = em.createNativeQuery(sqlDevice2);
			int statusDevice2 = 0;
			
			queryDev2.setParameter(1, deviceName);
			queryDev2.setParameter(2, defaultAvatarId);
			queryDev2.setParameter(3, defaultMessage);
			queryDev2.setParameter(4, true);
			queryDev2.setParameter(5, defaultVoice);
			queryDev2.setParameter(6, emailId);
			queryDev2.setParameter(7, new_udid);
			statusDevice2 = queryDev2.executeUpdate();
			
			if (statusDevice2 > 0) {
				returnObj.put("data", getDeviceDetails(emailId));
				isDeviceSettingChanged = true;
			}else{
				message = "Settings not updated, try again !";
				returnObj.put("data", message);
			}
		}else{
			message = "Settings not updated, try again !";
			returnObj.put("data", message);
		}
		returnObj.put("success", isDeviceSettingChanged);
		System.out.println("returnObj : "+returnObj);
		return returnObj;
	}
}
