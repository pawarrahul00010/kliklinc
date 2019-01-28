package com.technohertz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.technohertz.model.UserContact;
import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserContactService;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.DateUtil;

@RestController
@RequestMapping("/contactRest")
public class ContactRestController {
	
	@Autowired
	private IUserContactService service;
	
	@Autowired
	private IUserRegisterService userRegisterService;
	
	@Autowired
	private DateUtil dateUtil;
	
	/**
	 * 2. Save Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public void saveContact( @Valid @RequestBody UserRegister user,ModelMap map){
		
		List<UserContact> contactList = new ArrayList<UserContact>();
		
		List<UserRegister> retrivedUserList =(List<UserRegister>) userRegisterService.getAllMobileAndProfile();
		
		List<Long> retrievedContactList = userRegisterService.getAllMobile();
		
		for(UserContact userContact : user.getUserContactList()){
			
			if(retrievedContactList.contains(userContact.getContactNumber())) {//check user registered or not
				
				for(UserRegister userRegister: retrivedUserList) {//
					
					if( userRegister.getMobilNumber() == userContact.getContactNumber()) {
						
						UserContact contact = new UserContact();
						contact.setContactNumber(userContact.getContactNumber());
						contact.setContactName(userContact.getContactName());
						contact.setProfilePic(userRegister.getProfile().getCurrentProfile());
						contact.setCreateDate(dateUtil.getDate());
						contactList.add(contact);
					}
				}
			}
		}
		
		
		String userName = user.getUserName();
		
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
		
		retrievedUserRegister.setUserContactList(contactList);
		userRegisterService.save(retrievedUserRegister);
		
			
	}
	
	
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String getOTP(@RequestParam("mobilNumber") String userData, ModelMap map){
		
		
		return "You are not a registered User please register first";
	}
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String verifyOTP(@Valid @RequestBody UserRegister userRegister, ModelMap map){
		
			return "You are not a registered User please register first";
		
	}
	
	

	/**
	 * 3. Get All Otps
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllContacts")
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
	@GetMapping("/deleteContact")
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
	@GetMapping("/editContact")
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
	@PostMapping("/updateContact")
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
