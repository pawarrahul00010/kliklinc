package com.technohertz;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import com.technohertz.model.UserContact;
import com.technohertz.model.UserOtp;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserContactService;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.DateUtil;
import com.technohertz.util.ResponseObject;

@RestController
@RequestMapping("/contactRest")
public class ContactRestController {
	
	@Autowired
	private IUserContactService service;
	
	@Autowired
	private IUserRegisterService userRegisterService;
	
	@Autowired
	private IUserContactService userContactService;
	
	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private ResponseObject response;
	
	/**
	 * 2. Save Otp
	 * @param otp
	 * @param errors
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> saveContact( @RequestParam("contactList") String userContactList,
			@RequestParam("userId") String userid, ModelMap map){
		
		
				List<Long> contactlist = getContactList(userContactList);//convert from string to array
				
				List<UserContact> contactList = new ArrayList<UserContact>();
				
				List<UserRegister> retrivedUserList =(List<UserRegister>) userRegisterService.getAll();//get all user from database
				
				List<Long> retrievedContactList = userRegisterService.getAllMobile();//get all mobile no from database
				
				List<Long> craziContact = new ArrayList<Long>();
				
				
				Map<Long, UserRegister> userList = new TreeMap<Long, UserRegister>();
				
				for(long contactNumber : contactlist){
					
					for(UserRegister userRegister : retrivedUserList) {
						
						if(retrievedContactList.contains(contactNumber)) {//check user is craziapp user or not
							
							if(contactNumber == userRegister.getMobilNumber()) {
								
								userList.put(contactNumber, userRegister);
								craziContact.add(contactNumber);
							}
						}
					}
				}
					
				
				List<UserRegister> retrievedUserRegister = new ArrayList<UserRegister> ();
				
				UserRegister tosaveuserRegister = new UserRegister();
				
				int userId = 0;
				try {
					userId = Integer.parseInt(userid);
				} catch (NumberFormatException e) {
					
					response.setError("1");
					response.setMessage("wrong userId please enter numeric value");
					response.setData("[]");
					response.setStatus("FAIL");
					return ResponseEntity.ok(response);
					
				}
				
				try {
					 retrievedUserRegister = userRegisterService.getById(userId);
					  tosaveuserRegister = retrievedUserRegister.get(0);
						
					}catch (Exception e) {
						response.setError("1");
						response.setMessage("wrong userId please enter numeric value");
						response.setData("[]");
						response.setStatus("FAIL");
						return ResponseEntity.ok(response);
					}

				
				 List<UserContact>  delContactList= tosaveuserRegister.getUserContactList();
				 
				 List<Integer> contList = new ArrayList<Integer>();
				 
				 for(UserContact userContact: delContactList) {
					 
					 contList.add(userContact.getContactId());
					 
				 }
				 userContactService.deleteByUserId(userId, contList);
				
				for(long contactNumber : craziContact){	
					
						UserRegister userRegister = userList.get(contactNumber);
						
						UserContact contact = new UserContact();
						
						contact.setContactNumber(contactNumber);
						contact.setContactName(userRegister.getUserName());
						contact.setProfilePic(userRegister.getProfile().getCurrentProfile());
						contact.setCreateDate(dateUtil.getDate());
						contactList.add(contact);
					
				}		
								
				tosaveuserRegister.setUserContactList(contactList);
				
	//		 retrievedUserRegister.get(0).getUserContactList().add(contactList.iterator().next());
			 
			userRegisterService.save(tosaveuserRegister);
			
			String message = "Your CraziApp contacts are searched successfully";
			response.setError("0");
			response.setMessage(message);
			response.setData(tosaveuserRegister);
			response.setStatus("SUCCESS");
			return ResponseEntity.ok(response);
				
		}
		
	
	
	private List<Long> getContactList(String userContactList) {
		// TODO Auto-generated method stub
		List<Long> contactList = new ArrayList<Long>();
		String sContact[] = userContactList.split(",");
		
		for(String userContact : sContact ) {
		
			try {
				long contact = Long.parseLong(userContact);
				contactList.add(contact);
			} catch (NumberFormatException e) {
				continue;
			}
		
		}
		return contactList;
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
