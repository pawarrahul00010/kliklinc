package com.technohertz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.technohertz.common.Constant;
import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.Empty;
import com.technohertz.model.GroupProfile;
import com.technohertz.model.LikedUsers;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserContact;
import com.technohertz.model.UserRegister;
import com.technohertz.payload.UploadFileResponse;
import com.technohertz.repo.MediaFileRepo;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.IGroupProfileService;
import com.technohertz.service.IMediaFileService;
import com.technohertz.service.IUserContactService;
import com.technohertz.service.impl.FileStorageService;
import com.technohertz.util.CommonUtil;
import com.technohertz.util.ResponseObject;

@RestController
@RequestMapping("/groupRest")
public class GroupProfileController {
	
	
	@Autowired
	private Empty empty;
	
	@Autowired
	private IGroupProfileService groupProfileService;
	
	@Autowired
	private IUserContactService	userContactService;

	@Autowired
	private IMediaFileService mediaFileService;
	
	@Autowired
	private UserRegisterRepository registerRepository;

	@Autowired
	private MediaFileRepo mediaFileRepo;

	@Autowired
	private ResponseObject response;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private Constant constant;

	@Autowired
	private FileStorageService fileStorageService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseObject> createGroup(@RequestParam("contactList") String contacts,
			@RequestParam("file") MultipartFile file,@RequestParam("groupName") String groupName,
			@RequestParam("userId") Integer userId) {

		if (contacts.equals("") || contacts == null || String.valueOf(userId).equals("") || String.valueOf(userId) == null
				|| String.valueOf(file).equals("") || String.valueOf(file) == null
				|| String.valueOf(groupName).equals("") || String.valueOf(groupName) == null) {

			response.setError("1");
			response.setMessage("wrong userId, file, contactList and groupName please enter correct value");
			response.setData(empty);
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		} else {
			
			List<UserContact> retrivedContactList = userContactService.getAll();//get all user from database
			
			List<UserContact> contactlist = new ArrayList<UserContact>();
			
			List<String> contactList = getContactList(contacts);
			
			Map<String, UserContact> contactProfileList = commonUtil.getContactProfileDetails(contactList, retrivedContactList);
			
			GroupProfile groupProfile = new GroupProfile();
			
				for(String contact : contactList) {
					
					UserContact userContact = contactProfileList.get(contact); 	
				
					contactlist.add(userContact);
				}
				
				@SuppressWarnings("static-access")
				GroupProfile mediaFiles= fileStorageService.saveGroupProfile(file, userId, constant.GROUPPROFILE);

				groupProfile.setGroupMember(contactlist);
				groupProfile.setCurrentProfile(mediaFiles.getFiles().get(0).getFilePath());
				groupProfile.setDisplayName(groupName);
				groupProfile.setCreatedBy(userId);
				groupProfileService.save(groupProfile);

				response.setStatus("Success");
				response.setMessage("Group Created successfully");
				response.setError("0");
				response.setData(groupProfile);

				return ResponseEntity.ok(response);
				
				}	
			
		}

	@PostMapping("/update/contact")
	public ResponseEntity<ResponseObject> updateGroup(@RequestParam("contactList") String contacts,@RequestParam("groupId") Integer groupId) {

		if (contacts.equals("") || contacts == null 
				|| String.valueOf(groupId).equals("") || String.valueOf(groupId) == null) {

			response.setError("1");
			response.setMessage("wrong contactList and groupId please enter correct value");
			response.setData(empty);
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		} else {
			
			List<UserContact> retrivedContactList = userContactService.getAll();//get all user from database
			
			List<String> contactList = getContactList(contacts);
			
			Map<String, UserContact> contactProfileList = commonUtil.getContactProfileDetails(contactList, retrivedContactList);
			
			List<GroupProfile> getGroupUserList = groupProfileService.findById(groupId);
			
			
			
			List<String> conlist = groupProfileService.getGroupContactListById(groupId);
			
			GroupProfile groupProfile = getGroupUserList.get(0);
			
			List<UserContact> contactlist = getContactListTosave(contactList, conlist, contactProfileList, groupProfile );
			
				groupProfile.setGroupMember(contactlist);
				groupProfile.setGroupId(groupId);
				groupProfileService.save(groupProfile);

				response.setStatus("Success");
				response.setMessage("Group Created successfully");
				response.setError("0");
				response.setData(groupProfile);

				return ResponseEntity.ok(response);
				
				}	
			
		}

	
	private List<UserContact> getContactListTosave(List<String> contactList, List<String> conlist,
			Map<String, UserContact> contactProfileList, GroupProfile groupProfile) {
		
			for(String contact : contactList) {
				
				if(!conlist.contains(contact)) {
					
					UserContact userContact = contactProfileList.get(contact); 	
					
					groupProfile.getGroupMember().add(userContact);
				}
			}
			
		return groupProfile.getGroupMember();
	}

	@PostMapping("/delete/contact")
	public ResponseEntity<ResponseObject> deleteContactFromGroup(@RequestParam("contactidList") String contacts,
																 @RequestParam("groupId") String groupId) {

		if (contacts.equals("") || contacts == null 
				|| groupId.equals("") || groupId == null) {

			response.setError("1");
			response.setMessage("wrong contactList and groupId please enter correct value");
			response.setData(empty);
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		} else {
			int groupid;
			try {
				 groupid = Integer.parseInt(groupId);
			} catch (NumberFormatException e) {
				
				response.setError("1");
				response.setMessage("wrong contactList and groupId please enter correct value");
				response.setData(empty);
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);
			}
			
			 groupProfileService.deleteContactsById(groupid, contacts);
			
			 List<GroupProfile> getGroupUserList = groupProfileService.findById(groupid);
			 
			 GroupProfile groupProfile = getGroupUserList.get(0);
			
				response.setStatus("Success");
				response.setMessage("Group Created successfully");
				response.setError("0");
				response.setData(groupProfile);

				return ResponseEntity.ok(response);
				
				}	
			
		}

	
	@SuppressWarnings("unused")
	@PostMapping("/displayName/{groupId}")
	public ResponseEntity<ResponseObject> updateDisplayName(@RequestParam("displayName") String displayName,
			@PathVariable(value = "groupId") String profileid) throws ResourceNotFoundException {

		if (displayName.equals("") && displayName == null && profileid.equals("") && profileid == null) {

			response.setError("1");
			response.setMessage(" please enter correct value");
			response.setData(empty);
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		} else {

			int id = 0;
			try {
				id = Integer.parseInt(profileid);
			} catch (NumberFormatException e) {

				response.setError("1");
				response.setMessage("wrong profileId please enter numeric value");
				response.setData(empty);
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);

			}

			List<GroupProfile> profile = null;
			GroupProfile updateDisplayName = null;

			profile = groupProfileService.findById(id);
			if (!profile.isEmpty()) {

				profile.get(0).setDisplayName(displayName);
				profile.get(0).setCurrentProfile(profile.get(0).getFiles().get(0).getFilePath());
				System.out.println(profile.get(0).getCreatedBy());
				profile.get(0).setCreatedBy(profile.get(0).getCreatedBy());
				profile.get(0).setAboutGroup(profile.get(0).getAboutGroup());
				profile.get(0).setGroupId(id);
				updateDisplayName = groupProfileService.save(profile.get(0));

				response.setMessage("your Display name updated successfully");
				response.setData(profile.get(0));
				response.setError("");
				response.setStatus("success");

				return ResponseEntity.ok(response);
			} else {
				response.setMessage("user not available");
				response.setData(empty);
				response.setError("1");
				response.setStatus("fail");
				return ResponseEntity.ok(response);
			}
		}
	}

	@PostMapping("/like")
	public ResponseEntity<ResponseObject> totalLikes(@RequestParam("fileid") int fileid,
			@RequestParam("isLiked") boolean isLiked, @RequestParam(value = "userId") int userId) {
		MediaFiles mediaFiles = mediaFileRepo.getById(fileid);
		
		UserRegister userRegister = registerRepository.getOne(userId);
		
		List<LikedUsers> likedUsersList= mediaFileService.getUserLikesByFileId(fileid, userRegister.getUserName());
		
		long count = 0;

		if (mediaFiles.getLikes() == null || mediaFiles.getLikes() == 0) {
			count = 0;
		} else {
			count = mediaFiles.getLikes();
		}
		
		if(likedUsersList.isEmpty()) {
			LikedUsers likedUsers = new LikedUsers();
			count = count + 1;
			mediaFiles.setLikes(count);
			mediaFiles.setIsRated(true);
			
			likedUsers.setUserName(userRegister.getUserName());
			likedUsers.setMarkType(Constant.LIKE);
			mediaFiles.setIsLiked(true);
			mediaFiles.getLikedUsers().add(likedUsers);
			mediaFileRepo.save(mediaFiles);

			response.setError("0");
			response.setMessage("user liked successfully");
			response.setData(mediaFiles);
			response.setStatus("SUCCESS");
			return ResponseEntity.ok(response);
			
		}else {
			LikedUsers likedUsers = likedUsersList.get(0);
		
		if (likedUsers.getMarkType() == Constant.UNLIKED || likedUsers.getMarkType().equals(Constant.UNLIKED)) {

			count = count + 1;
			mediaFiles.setLikes(count);
			mediaFiles.setIsRated(true);
			likedUsers.setUserName(userRegister.getUserName());
			likedUsers.setMarkType(Constant.LIKE);
			likedUsers.setUserId(userId);
			mediaFiles.getLikedUsers().add(likedUsers);
			mediaFileRepo.save(mediaFiles);

			response.setError("0");
			response.setMessage("user liked successfully");
			response.setData(mediaFiles);
			response.setStatus("SUCCESS");
			return ResponseEntity.ok(response);

		} else {

			count = count - 1;
			likedUsers.setMarkType(Constant.UNLIKED);
			likedUsers.setUserId(userId);
			likedUsers.setUserName(userRegister.getUserName());
			mediaFiles.setLikes(count);
			mediaFiles.setIsRated(true);
			mediaFiles.getLikedUsers().add(likedUsers);
			mediaFileRepo.save(mediaFiles);
			
			response.setError("0");
			response.setMessage("user unliked successfully");
			response.setData(mediaFiles);
			response.setStatus("SUCCESS");
			return ResponseEntity.ok(response);

		}
	  }

	}

	@SuppressWarnings("unused")
	@PostMapping("/rating")
	public ResponseEntity<ResponseObject> totalRating(@RequestParam("fileid") String userfileid,
			@RequestParam("isRated") String isRated, @RequestParam("rateCount") String rateCounts,
			@RequestParam(value = "userId") int userId) {

		int cRate = Integer.parseInt(rateCounts);
		if (userfileid.equals("") && userfileid == null && isRated.equals("") && isRated == null
				&& rateCounts.equals("") && rateCounts == null) {

			response.setError("1");
			response.setMessage("wrong fileid, rateCount and isRated please enter correct value");
			response.setData(empty);
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		} else {

			int fileid = 0;
			int rateCount = 0;
			boolean isRate = false;
			try {
				isRate = Boolean.parseBoolean(isRated);
				fileid = Integer.parseInt(userfileid);
				rateCount = Integer.parseInt(rateCounts);
			} catch (Exception e) {

				response.setError("1");
				response.setMessage("wrong fileid and rateCount please enter numeric value");
				response.setData(empty);
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);

			}

			UserRegister userRegister = registerRepository.getOne(userId);
			List<LikedUsers> likedUsersList= mediaFileService.getUserRatingByFileId(userfileid, userId);


			MediaFiles mediaFiles = mediaFileRepo.getById(fileid);

			long rate=0;
			System.out.println(mediaFiles.getLikes());
			
			
			if(mediaFiles.getRating() == null || mediaFiles.getRating() == 0) {

				rate=0;

			} else{

				rate=mediaFiles.getRating();
			}
			
				if(likedUsersList.isEmpty()) {
				
				LikedUsers likedUsers=new LikedUsers();
				likedUsers.setUserName(userRegister.getUserName());
				likedUsers.setMarkType(Constant.RATE);
				likedUsers.setUserId(userId);
				likedUsers.setRating(rateCount);
				likedUsers.setTypeId(0);
				
				rate = rate+rateCount;
				mediaFiles.setRating(rate);
				mediaFiles.setIsRated(true);
				mediaFiles.getLikedUsers().add(likedUsers); 
				mediaFileRepo.save(mediaFiles);

				response.setError("0");
				response.setMessage("user rated with : "+cRate);
				response.setData(mediaFiles);
				response.setStatus("SUCCESS");
				return ResponseEntity.ok(response);

			}
			else {
				
				LikedUsers likedUsers=likedUsersList.get(0);
				
				/*
				 * if(isRate==true&& mediaFiles.getIsRated()==false ) {
				 * 
				 * 
				 * rate = rate+rateCount; mediaFiles.setRating(rate);
				 * mediaFiles.setIsRated(isRate); mediaFileRepo.save(mediaFiles);
				 * 
				 * response.setError("0"); response.setMessage("user rated with : "+rateCount);
				 * response.setData(mediaFiles); response.setStatus("SUCCESS"); return
				 * ResponseEntity.ok(response);
				 * 
				 * 
				 * } else {
				 */
						if(rate>=0) {
							rate = rate-likedUsers.getRating();
							rate = rate+rateCount;
							likedUsers.setUserName(userRegister.getUserName());
							likedUsers.setMarkType(Constant.RATE);
							likedUsers.setUserId(userId);
							likedUsers.setRating(rateCount);
							mediaFiles.getLikedUsers().add(likedUsers); 
							
						}

				mediaFiles.setIsRated(true);
				mediaFiles.setRating(rate);
				mediaFileRepo.save(mediaFiles);

				response.setError("0");
				response.setMessage("rating updated with "+cRate);
				response.setData(mediaFiles);
				response.setStatus("SUCCESS");
				return ResponseEntity.ok(response);
			}
		}
	}

	@SuppressWarnings("unused")
	@PutMapping("/aboutUs/{id}")
	public ResponseEntity<ResponseObject> updateStatus(@RequestParam("aboutUs") String aboutUs,
			@PathVariable(value = "id") String userid) throws ResourceNotFoundException {

		if (aboutUs.equals("") && aboutUs == null && userid.equals("") && userid == null) {

			response.setError("1");
			response.setMessage("wrong aboutUs and userid please enter correct value");
			response.setData(empty);
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		} else {

			int id = 0;
			int rateCount = 0;
			boolean isRated = false;
			try {
				id = Integer.parseInt(userid);
			} catch (NumberFormatException e) {

				response.setError("1");
				response.setMessage("wrong fileid and rateCount please enter numeric value");
				response.setData(empty);
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);

			}

			List<GroupProfile> profile = null;
			GroupProfile updateDisplayName = null;

			profile = groupProfileService.findById(id);
			if (!profile.isEmpty()) {
				profile.get(0).setAboutGroup(aboutUs);
				profile.get(0).setGroupId(id);
				updateDisplayName = groupProfileService.save(profile.get(0));

				response.setMessage("your status updated successfully");
				response.setData(updateDisplayName);
				response.setError("");
				response.setStatus("success");

				return ResponseEntity.ok(response);

			} else {

				response.setMessage("user not available");
				response.setData(empty);
				response.setError("1");
				response.setStatus("fail");
				return ResponseEntity.ok(response);
			}
		}
	}

	@PutMapping("/uploadFile/{userId}")
	public ResponseEntity<ResponseObject> updateProfile(@RequestParam("file") MultipartFile file,
			@PathVariable(value = "userId") Integer userId) {
		GroupProfile groupProfile = fileStorageService.savegroupProfile(file, userId);
		MediaFiles files = mediaFileRepo.getOne(Integer
				.valueOf(String.valueOf(groupProfile.getFiles().get(groupProfile.getFiles().size() - 1).getFileId())));
		System.out.println(files);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(String.valueOf(files.getFilePath())).toUriString();
		Object obj = new UploadFileResponse(groupProfile.getCurrentProfile(), fileDownloadUri, file.getContentType(),
				file.getSize());
		if (!file.isEmpty()||userId!=null) {
			response.setMessage("your Profile Image updated successfully");

			response.setData(obj);
			response.setError("");
			response.setStatus("success");

			return ResponseEntity.ok(response);
		} else {
			response.setMessage("your Profile Image not updated");

			response.setData(empty);
			response.setError("");
			response.setStatus("success");

			return ResponseEntity.ok(response);
		}
	}

	private List<String> getContactList(String userContactList) {
		
		List<String> contactList = new ArrayList<String>();
		String sContact[] = userContactList.split(",");
		
		for(String userContact : sContact ) {
		
			try {
				contactList.add(userContact);
			} catch (NumberFormatException e) {
				continue;
			}
		
		}
		return contactList;
	}
	
	private List<Integer> getContactIdList(String userContactList) {
		
		List<Integer> contactList = new ArrayList<Integer>();
		String sContact[] = userContactList.split(",");
		
		for(String userContact : sContact ) {
		
			try {
				contactList.add(Integer.parseInt(userContact));
			} catch (NumberFormatException e) {
				continue;
			}
		
		}
		return contactList;
	}

}
