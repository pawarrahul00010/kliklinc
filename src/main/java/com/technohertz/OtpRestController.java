package com.technohertz;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserOtpService;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.OtpUtil;

@RestController
@RequestMapping("/otpRest")
public class OtpRestController {
	
	@Autowired
	private IUserOtpService service;
	
	@Autowired
	private IUserRegisterService userRegisterService;
	
	@Autowired
	private OtpUtil util;
	
	
	/**
	 * 2. Save Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/otp", method = RequestMethod.POST)
	public void saveOTP(@Valid @RequestBody UserRegister userRegister,ModelMap map){
		
			long mobilNumber = userRegister.getMobilNumber();
			String userName = userRegister.getUserName();
			UserRegister retrievedUserRegister = userRegisterService.findByMobileOrUserName(mobilNumber, userName).get(0);
			
			if(retrievedUserRegister != null) {
			
				UserOtp userOtp = new UserOtp();
				int otp = util.getOTP();
				userOtp.setOtp(otp);
				userOtp.setCreateDate(getDate());
				userOtp.setLastModifiedDate(getDate());
				retrievedUserRegister.setUserOtp(userOtp);
				
				userRegisterService.save(retrievedUserRegister);
				map.addAttribute("message", otp);
			}
			else {
				map.addAttribute("message", "You are not a registered User please register first");
			}
		
	}
	
		@RequestMapping(value = "/forget/otp", method = RequestMethod.POST)
		public String saveForgetOTP(@Valid @RequestBody UserRegister userRegister, ModelMap map){
			
			String userName = userRegister.getUserName();
			
			UserRegister retrievedUserRegister = new UserRegister ();
			
			if(userName != null) {
				try {
				Long mobilNumber = Long.parseLong(userName);
				 retrievedUserRegister = userRegisterService.findByMobileOrUserName(mobilNumber, null).get(0);
				}catch (Exception e) {
					
					 try {
						retrievedUserRegister = userRegisterService.findByMobileOrUserName(null, userName).get(0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}	
				
			
		/*
		 * if(retrievedUserRegister != null) {
		 * 
		 * UserOtp userOtp = new UserOtp(); int otp = util.getOTP();
		 * userOtp.setOtp(otp); userOtp.setCreateDate(getDate());
		 * userOtp.setLastModifiedDate(getDate());
		 * retrievedUserRegister.setUserOtp(userOtp);
		 * 
		 * userRegisterService.save(retrievedUserRegister); map.addAttribute("message",
		 * otp); } else { map.addAttribute("message",
		 * "You are not a registered User please register first"); }
		 */
		
			
			if(retrievedUserRegister.getUserOtp()!= null){
				if(getDate().minusMinutes(1).isBefore(retrievedUserRegister.getUserOtp().getCreateDate()) ){
					
					map.addAttribute("message", retrievedUserRegister.getUserOtp().getOtp());
					
					return String.valueOf(retrievedUserRegister.getUserOtp().getOtp());
					
				}else{
					UserOtp saveUserOTP = new UserOtp();
					int otp = util.getOTP();
					saveUserOTP.setOtp(otp);
					saveUserOTP.setOtpId(retrievedUserRegister.getUserOtp().getOtpId());
					saveUserOTP.setCreateDate(getDate());
					saveUserOTP.setLastModifiedDate(getDate());
					retrievedUserRegister.setUserOtp(saveUserOTP);
					userRegisterService.save(retrievedUserRegister);
					map.addAttribute("message", otp);
					
					return String.valueOf(otp);
				}
				
			}else {
				map.addAttribute("message", "You are not a registered User please register first");
				
				return "You are not a registered User please register first";
			}
			
		}
	
	@RequestMapping(value = "/otp/resend", method = RequestMethod.GET)
	public String getOTP(@RequestParam("mobilNumber") String userData, ModelMap map){
		
		UserRegister retrievedUserRegister = new UserRegister ();
		
		if(userData != null) {
			try {
				Long mobilNumber = Long.parseLong(userData);
				retrievedUserRegister = userRegisterService.findByMobileOrUserName(mobilNumber, null).get(0);
			}catch (Exception e) {
				
				try {
					retrievedUserRegister = userRegisterService.findByMobileOrUserName(null, userData).get(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
			if(retrievedUserRegister.getUserOtp()!= null){
					if(getDate().minusMinutes(30).isBefore(retrievedUserRegister.getUserOtp().getCreateDate()) ){
						
						map.addAttribute("message", retrievedUserRegister.getUserOtp().getOtp());
						
						return String.valueOf(retrievedUserRegister.getUserOtp().getOtp());
						
					}else{
						UserOtp saveUserOTP = new UserOtp();
						int otp = util.getOTP();
						saveUserOTP.setOtp(otp);
						saveUserOTP.setOtpId(retrievedUserRegister.getUserOtp().getOtpId());
						saveUserOTP.setCreateDate(getDate());
						saveUserOTP.setLastModifiedDate(getDate());
						retrievedUserRegister.setUserOtp(saveUserOTP);
						userRegisterService.save(retrievedUserRegister);
						map.addAttribute("message", otp);
						
						return String.valueOf(otp);
					}
					
				}else {
					map.addAttribute("message", "You are not a registered User please register first");
					
					return "You are not a registered User please register first";
				}
			
	}
	
	@RequestMapping(value = "/otp/verify", method = RequestMethod.POST)
	public String verifyOTP(@Valid @RequestBody UserRegister userRegister, ModelMap map){
		
		String userName = userRegister.getUserName();
		Integer otp = userRegister.getUserOtp().getOtp();
		
		UserRegister retrievedUserRegister = new UserRegister ();
		
		if(userName != null) {
			try {
			Long mobilNumber = Long.parseLong(userName);
			 retrievedUserRegister = userRegisterService.findByMobileOrUserName(mobilNumber, null).get(0);
			}catch (Exception e) {
				
				 try {
					retrievedUserRegister = userRegisterService.findByMobileOrUserName(null, userName).get(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}	
			
		
	/*
	 * if(retrievedUserRegister != null) {
	 * 
	 * UserOtp userOtp = new UserOtp(); int otp = util.getOTP();
	 * userOtp.setOtp(otp); userOtp.setCreateDate(getDate());
	 * userOtp.setLastModifiedDate(getDate());
	 * retrievedUserRegister.setUserOtp(userOtp);
	 * 
	 * userRegisterService.save(retrievedUserRegister); map.addAttribute("message",
	 * otp); } else { map.addAttribute("message",
	 * "You are not a registered User please register first"); }
	 */
	
		
		if(retrievedUserRegister.getUserOtp()!= null){
			if(getDate().minusMinutes(1).isBefore(retrievedUserRegister.getUserOtp().getCreateDate()) ){
				
				if(retrievedUserRegister.getUserOtp().getOtp() == otp) {
					
					UserOtp saveUserOTP = new UserOtp();
					saveUserOTP.setOtp(otp);
					saveUserOTP.setOtpId(retrievedUserRegister.getUserOtp().getOtpId());
					saveUserOTP.setCreateDate(retrievedUserRegister.getUserOtp().getCreateDate());
					saveUserOTP.setLastModifiedDate(getDate());
					retrievedUserRegister.setUserOtp(saveUserOTP);
					userRegisterService.save(retrievedUserRegister);
					
					map.addAttribute("message", "verified");
					return "verified";
					
				}else {
					UserOtp saveUserOTP = new UserOtp();
					int newOtp = util.getOTP();
					saveUserOTP.setOtp(newOtp);
					saveUserOTP.setOtpId(retrievedUserRegister.getUserOtp().getOtpId());
					saveUserOTP.setCreateDate(getDate());
					saveUserOTP.setLastModifiedDate(getDate());
					retrievedUserRegister.setUserOtp(saveUserOTP);
					userRegisterService.save(retrievedUserRegister);
					map.addAttribute("message", " Sorry wrong OTP please try again new OTP is: "+newOtp);
					
					return String.valueOf(newOtp);
					
					
				}
				
				
				
			}else{
				UserOtp saveUserOTP = new UserOtp();
				int newOtp = util.getOTP();
				saveUserOTP.setOtp(newOtp);
				saveUserOTP.setOtpId(retrievedUserRegister.getUserOtp().getOtpId());
				saveUserOTP.setCreateDate(getDate());
				saveUserOTP.setLastModifiedDate(getDate());
				retrievedUserRegister.setUserOtp(saveUserOTP);
				userRegisterService.save(retrievedUserRegister);
				map.addAttribute("message", " Your OTP is expired please try again new OTP is: "+newOtp);
				
				return String.valueOf(newOtp);
			}
			
		}else {
			map.addAttribute("message", "You are not a registered User please register first");
			
			return "You are not a registered User please register first";
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
