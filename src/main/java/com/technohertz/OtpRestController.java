package com.technohertz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserOtpService;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.JsonUtil;
import com.technohertz.util.OtpUtil;
import com.technohertz.util.ResponseObject;
import com.technohertz.util.sendSMS;

@RestController
@RequestMapping("/otpRest")
public class OtpRestController {
	
	@Autowired
	private IUserOtpService service;
	
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
	public ResponseEntity<String> saveOTP(@Valid @RequestBody UserRegister userRegister){
		
			int userId = userRegister.getUserId();
			
			UserRegister retrievedUserRegister = new UserRegister();
			try {
				retrievedUserRegister = userRegisterService.getOneById(userId);
			} catch (Exception e) {
				
				response.setError(true);
				response.setMessage("You are not a registered User please register first");
				response.setData(retrievedUserRegister);
				response.setStatus("FAILURE");
				jsonUtil.objToJson(response);
				return ResponseEntity.ok(jsonUtil.objToJson(response));
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
				response.setError(false);
				response.setMessage(message);
				response.setData(retrievedUserRegister);
				response.setStatus("SUCCESS");
				jsonUtil.objToJson(response);
				return ResponseEntity.ok(jsonUtil.objToJson(response));
			}
			else {
				
				response.setError(true);
				response.setMessage("You are not a registered User please register first");
				response.setData(retrievedUserRegister);
				response.setStatus("FAILURE");
				jsonUtil.objToJson(response);
				return ResponseEntity.ok(jsonUtil.objToJson(response));
			}
	}
	
		@RequestMapping(value = {"/forget/otp","/otp/resend"}, method = RequestMethod.POST)
		public ResponseEntity<ResponseObject> saveForgetOTP(@RequestParam("userId") int userId){
			
			List<UserRegister> retrievedUserRegister = new ArrayList<UserRegister> ();
			
			if(String.valueOf(userId) != null) {
				
					 retrievedUserRegister = userRegisterService.getById(userId);
					 
					 if(!retrievedUserRegister.isEmpty()) {
						 
						 if((retrievedUserRegister.get(0).getUserOtp().getOtp() == 0) || (String.valueOf(retrievedUserRegister.get(0).getUserOtp().getOtp()) == null) || (String.valueOf(retrievedUserRegister.get(0).getUserOtp().getOtp()) == "")){
							 
							 UserOtp userOtp = new UserOtp();
								int otp = util.getOTP();
								userOtp.setOtp(otp);
								userOtp.setCreateDate(getDate());
								userOtp.setLastModifiedDate(getDate());
								retrievedUserRegister.get(0).setUserOtp(userOtp);
								
								userRegisterService.save(retrievedUserRegister.get(0));
								
								String message = "OTP send successfully enter OTP to verify";
								response.setError(false);
								response.setMessage(message);
								response.setData(retrievedUserRegister.get(0));
								response.setStatus("SUCCESS");
								return ResponseEntity.ok(response);
								
							
						}else {
							String message = "OTP send successfully enter OTP to verify";
							response.setError(false);
							response.setMessage(message);
							response.setData(retrievedUserRegister.get(0));
							response.setStatus("SUCCESS");
							return ResponseEntity.ok(response);
						}
						 
					} else {
						response.setError(true);
						response.setMessage("You are not a registered User please register first");
						response.setData("[]");
						response.setStatus("FAILURE");
						return ResponseEntity.ok(response);
					}
				 }else {
					 
					response.setError(true);
					response.setMessage("wrong userId please enter numeric value");
					response.setData("[]");
					response.setStatus("FAILURE");
					return ResponseEntity.ok(response);
			
				 }
			}
	
	
	@RequestMapping(value = "/otp/verify", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> verifyOTP(@RequestParam("userId") int userId,@RequestParam("otp") int otp, ModelMap map){
		
			
			List<UserRegister> retrievedUserRegister = new ArrayList<UserRegister> ();
			
			if(String.valueOf(userId) != null) {
				
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
								
								response.setError(false);
								response.setMessage("verified");
								response.setData(retrievedUserRegister.get(0));
								response.setStatus("SUCCESS");
								return ResponseEntity.ok(response);
								
							}else {
								
								response.setError(true);
								response.setMessage("Sorry wrong OTP please try again...");
								response.setData("[]");
								response.setStatus("FAILURE");
								return ResponseEntity.ok(response);
									
								}
				
						}else {
							response.setError(true);
							response.setMessage("click on resend OTP");
							response.setData("[]");
							response.setStatus("FAILURE");
							return ResponseEntity.ok(response);
						}
						
				 } else {
						response.setError(true);
						response.setMessage("You are not a registered User please register first");
						response.setData("[]");
						response.setStatus("FAILURE");
						return ResponseEntity.ok(response);
					}
			 }
			else {	
				
				response.setError(true);
				response.setMessage("wrong userId please enter numeric value");
				response.setData("[]");
				response.setStatus("FAILURE");
				return ResponseEntity.ok(response);
			}
		}
	/**
	 * 3. Get All Otps
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllOtps")
	public String getAll(ModelMap map){
		List<UserOtp> list=service.getAll();
		map.addAttribute("otpsList", list);
		return "OtpData";
	}
	
	/**
	 * 4. Delete Otp by Id
	 * @param otpId
	 * @return
	 */
	@GetMapping("/deleteOtp")
	public String delete(@RequestParam int userId){
		service.deleteById(userId);
		return "redirect:getAllOtps";
	}
	
	/**
	 * 5. Show Edit Page
	 * @param otpId
	 * @param map
	 * @return
	 */
	@GetMapping("/editOtp")
	public String edit(@RequestParam int otpId,ModelMap map){
		map.addAttribute("otp", service.getOneById(otpId));
		return "OtpDataEdit";
	}
	
	/**
	 * 6. Update Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updateOtp")
	public String update(@ModelAttribute UserOtp otp,BindingResult errors,ModelMap map){
		String page=null;
		if(errors.hasErrors()){
			map.addAttribute("otp", otp);
			page="OtpDataEdit";
		}else{
			service.update(otp);
			map.addAttribute("otp", new UserOtp());
			page="redirect:getAllOtps";
		}
		return page;
	}

	public LocalDateTime getDate() {
		
	    LocalDateTime now = LocalDateTime.now();  
		   
		return now;
		
	}
}
