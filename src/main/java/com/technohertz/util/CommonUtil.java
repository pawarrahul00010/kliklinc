package com.technohertz.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.technohertz.model.UserContact;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserRegisterService;

@Component
public class CommonUtil {


	@Autowired
	private IUserRegisterService userRegisterService;

	
	public Map<String, UserRegister> getContactWithDetails(List<String> contactList, List<UserRegister> retrivedUserList){
		
		Map<String, UserRegister> userList = new TreeMap<String, UserRegister>();
		
		for(String contactNumber : contactList){
			
			for(UserRegister userRegister : retrivedUserList) {
					
					if(contactNumber == userRegister.getMobilNumber() || userRegister.getMobilNumber().equals(contactNumber)) {
						
						userList.put(contactNumber, userRegister);
				}
			}
		}
		
		return userList;
	}

		public Map<String, UserContact> getContactProfileDetails(List<String> contactList,
				List<UserContact> retrivedContactList) {
			
			Map<String, UserContact> userContactList = new TreeMap<String, UserContact>();
			
			Map<String, String> profileList = updateProfilePics(contactList);
			
				for(String contact : contactList) {
					
					for(UserContact userContact : retrivedContactList) {
						
						if(contact == userContact.getContactNumber() || userContact.getContactNumber().equals(contact)) {
							
							userContact.setProfilePic(profileList.get(contact));
							
							userContactList.put(contact, userContact);
					}
					
				}
			
			}
			return userContactList;
		}

		public Map<String, String> updateProfilePics(List<String> contactList){
			
			List<UserRegister> retrivedUserList= userRegisterService.getAll();
			
			Map<String, UserRegister> userList = getContactWithDetails(contactList, retrivedUserList);
			
			Map<String, String> userCurrentProfileList = new TreeMap<String, String>();
		
			for(String contact : contactList) {
				
						userCurrentProfileList.put(contact, userList.get(contact).getProfile().getCurrentProfile());
				}
				
			return userCurrentProfileList;
		}
		
	}
