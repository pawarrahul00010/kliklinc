package com.technohertz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.JsonUtil;
import com.technohertz.util.OtpUtil;
import com.technohertz.util.ResponseObject;
import com.technohertz.util.sendSMS;

@RestController
@RequestMapping("/otpRest")
public class OtpRestController {
	
	@Autowired
	private IUserRegisterService userRegisterService;
	
	@Autowired
	private OtpUtil util;
	
	@Autowired
	private sendSMS sms;
	
	
	@Autowired
	private ResponseObject response;
	
	
	@Autowired
	private JsonUtil jsonUtil;
	
	
	/**
	 * 2. Save Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/otp", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> saveOTP(@Valid @RequestBody UserRegister userRegister){
		
		if(userRegister.equals("") || userRegister == null ) {
			
			response.setError("1");
			response.setMessage("wrong userRegister data please enter correct value");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);
		
		}
		else {
		
			int userId = userRegister.getUserId();
			
			UserRegister retrievedUserRegister = new UserRegister();
			try {
				retrievedUserRegister = userRegisterService.getOneById(userId);
			} catch (Exception e) {
				
				response.setError("1");
				response.setMessage("You are not a registered User please register first");
				response.setData(retrievedUserRegister);
				response.setStatus("FAIL");
				jsonUtil.objToJson(response);
				return ResponseEntity.ok(response);
			}
			
			if(retrievedUserRegister != null) {
			
				UserOtp userOtp = new UserOtp();
				int otp = util.getOTP();
				userOtp.setOtp(otp);
				userOtp.setCreateDate(getDate());
				userOtp.setLastModifiedDate(getDate());
				retrievedUserRegister.setUserOtp(userOtp);
				
				userRegisterService.save(retrievedUserRegister);
				String sendmessage = sms.sendSms(String.valueOf(userId), "Your Registration is successful enter OTP to verify : "+otp);
				System.out.println(sendmessage);
				String message = "Your Registration is successful enter OTP to verify";
				response.setError("0");
				response.setMessage(message);
				response.setData(retrievedUserRegister);
				response.setStatus("SUCCESS");
				jsonUtil.objToJson(response);
				return ResponseEntity.ok(response);
			}
			else {
				
				response.setError("1");
				response.setMessage("You are not a registered User please register first");
				response.setData(retrievedUserRegister);
				response.setStatus("FAIL");
				jsonUtil.objToJson(response);
				return ResponseEntity.ok(response);
			}
		}
	}
		@RequestMapping(value = {"/forget/otp","/otp/resend"}, method = RequestMethod.POST)
		public ResponseEntity<ResponseObject> saveForgetOTP(@RequestParam("mobilNumber") String mobilNumber){
			
			if(mobilNumber.equals("") || mobilNumber == null ) {
				
				response.setError("1");
				response.setMessage("wrong userRegister data please enter correct value");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);
			
			}
			else {
			
			List<UserRegister> retrievedUserRegister = new ArrayList<UserRegister> ();
			
				
					 retrievedUserRegister = userRegisterService.findByMobileNumber(mobilNumber);
					 
					 if(!retrievedUserRegister.isEmpty()) {
						 
						 if((retrievedUserRegister.get(0).getUserOtp().getOtp() == 0) || 
								 (String.valueOf(retrievedUserRegister.get(0).getUserOtp().getOtp()) == null) || 
								 (String.valueOf(retrievedUserRegister.get(0).getUserOtp().getOtp()) == "")){
							 
							 UserOtp userOtp = new UserOtp();
								int otp = util.getOTP();
								userOtp.setOtp(otp);
								userOtp.setCreateDate(getDate());
								userOtp.setLastModifiedDate(getDate());
								retrievedUserRegister.get(0).setUserOtp(userOtp);
								
								userRegisterService.save(retrievedUserRegister.get(0));
								
								String message = "OTP send successfully enter OTP to verify";
								response.setError("0");
								response.setMessage(message);
								response.setData(retrievedUserRegister.get(0));
								response.setStatus("SUCCESS");
								return ResponseEntity.ok(response);
								
							
						}else {
							String message = "OTP send successfully enter OTP to verify";
							response.setError("0");
							response.setMessage(message);
							response.setData(retrievedUserRegister.get(0));
							response.setStatus("SUCCESS");
							return ResponseEntity.ok(response);
						}
						 
					} else {
						response.setError("1");
						response.setMessage("You are not a registered User please register first");
						response.setData("[]");
						response.setStatus("FAIL");
						return ResponseEntity.ok(response);
					}
			}
	
		}
		
	@RequestMapping(value = "/otp/verify", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> verifyOTP(@RequestParam("userId") String userid,@RequestParam("otp") String OTP){
		
		if(userid.equals("") || userid == null || OTP.equals("") || OTP == null ) {
			
			response.setError("1");
			response.setMessage("wrong userRegister data please enter correct value");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);
		
		}
		else {
			List<UserRegister> retrievedUserRegister = new ArrayList<UserRegister> ();
			
			int userId = 0;
			int otp = 0;
			try {
				userId = Integer.parseInt(userid);
				otp = Integer.parseInt(OTP);
			} catch (NumberFormatException e) {
				
				response.setError("1");
				response.setMessage("wrong userId and OTP please enter numeric value");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);
				
			}
			
			if((userid != null) && (!userid.equals("") && (OTP != null) && (!OTP.equals("")))) {
				
				 retrievedUserRegister = userRegisterService.getById(userId);
				 
				 if(!retrievedUserRegister.isEmpty()) {				
		
					if(retrievedUserRegister.get(0).getUserOtp()!= null){
							
							if(retrievedUserRegister.get(0).getUserOtp().getOtp() == otp) {
								
								UserOtp saveUserOTP = new UserOtp();
								saveUserOTP.setOtp(otp);
								saveUserOTP.setOtpId(retrievedUserRegister.get(0).getUserOtp().getOtpId());
								saveUserOTP.setCreateDate(retrievedUserRegister.get(0).getUserOtp().getCreateDate());
								saveUserOTP.setLastModifiedDate(getDate());
								retrievedUserRegister.get(0).setUserOtp(saveUserOTP);
								userRegisterService.save(retrievedUserRegister.get(0));
								
								response.setError("0");
								response.setMessage("verified");
								response.setData(retrievedUserRegister.get(0));
								response.setStatus("SUCCESS");
								return ResponseEntity.ok(response);
								
							}else {
								
								response.setError("1");
								response.setMessage("Sorry wrong OTP please try again...");
								response.setData("[]");
								response.setStatus("FAIL");
								return ResponseEntity.ok(response);
									
								}
				
						}else {
							response.setError("1");
							response.setMessage("click on resend OTP");
							response.setData("[]");
							response.setStatus("FAIL");
							return ResponseEntity.ok(response);
						}
						
				 } else {
						response.setError("1");
						response.setMessage("You are not a registered User please register first");
						response.setData("[]");
						response.setStatus("FAIL");
						return ResponseEntity.ok(response);
					}
			 }
			else {	
				
				response.setError("1");
				response.setMessage("wrong userId please enter numeric value");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);
			}
		}

	}
	
	public LocalDateTime getDate() {
		
	    LocalDateTime now = LocalDateTime.now();  
		   
		return now;
		
	}
}
