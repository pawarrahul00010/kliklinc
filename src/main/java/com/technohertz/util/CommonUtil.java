package com.technohertz.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.technohertz.model.UserContact;
import com.technohertz.model.UserRegister;

@Component
public class CommonUtil {

	
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
			
				for(String contact : contactList) {
					
					for(UserContact userContact : retrivedContactList) {
						
						if(contact == userContact.getContactNumber() || userContact.getContactNumber().equals(contact)) {
							
							userContactList.put(contact, userContact);
					}
					
				}
			
			}
			return userContactList;
		}

	}
