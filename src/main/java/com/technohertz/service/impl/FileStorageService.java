package com.technohertz.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.technohertz.exception.FileStorageException;
import com.technohertz.exception.MyFileNotFoundException;
import com.technohertz.model.GroupProfile;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserProfile;
import com.technohertz.repo.DBFileRepository;
import com.technohertz.repo.GroupProfileRepository;
import com.technohertz.repo.MediaFileRepo;
import com.technohertz.repo.UserProfileRepository;
import com.technohertz.util.DateUtil;
import com.technohertz.util.FileStorageProperties;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Autowired
	private DBFileRepository dbFileRepository;

	@Autowired
	private MediaFileRepo mediaFileRepo;
	
	
	@Autowired
	private UserProfileRepository userprofileRepo;
	@Autowired
	private GroupProfileRepository groupProfileRepository;
	
	
	@Autowired
	FileStorageProperties fileStorageProperty;
	
	@Autowired
	DateUtil dateUtil = new DateUtil();

	@Autowired
	public FileStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public MediaFiles storeFile(MultipartFile file, int userId) {
		String fileName = StringUtils.cleanPath(String.valueOf(userId)+System.currentTimeMillis()+getFileExtension(file));
	
		
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			MediaFiles mediaFile = new MediaFiles();
			mediaFile.setFilePath(fileName);
			mediaFile.setCreateDate(dateUtil.getDate());
			mediaFile.setLastModifiedDate(dateUtil.getDate());
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return dbFileRepository.save(mediaFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public UserProfile saveProfile(MultipartFile file, int userId) {
		String fileName = StringUtils.cleanPath(String.valueOf(userId)+System.currentTimeMillis()+getFileExtension(file));
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file);
			}
			List<UserProfile> userprofile = null;
			MediaFiles mfile = new MediaFiles();
			
			userprofile = userprofileRepo.findById(userId);
			mfile.setFilePath(fileName);
			mfile.setIsLiked(false);
			mfile.setIsRated(false);
			mfile.setCreateDate(dateUtil.getDate());
			mfile.setLastModifiedDate(dateUtil.getDate());
			
			userprofile.get(0).setProfileId(userId);
			
			userprofile.get(0).setCurrentProfile(fileName);
			userprofile.get(0).getFiles().add(mfile);
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return userprofileRepo.save(userprofile.get(0));
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
	public GroupProfile savegroupProfile(MultipartFile file, int userId) {
		String fileName = StringUtils.cleanPath(String.valueOf(userId)+System.currentTimeMillis()+getFileExtension(file));
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file);
			}
			List<GroupProfile> groupProfile = null;
			MediaFiles mfile = new MediaFiles();
			groupProfile = groupProfileRepository.findById(userId);
			mfile.setFilePath(fileName);
			//mfile.setFileId(id);
			mfile.setCreateDate(dateUtil.getDate());
			mfile.setLastModifiedDate(dateUtil.getDate());
			mfile.setIsLiked(false);
			mfile.setIsRated(false);
			groupProfile.get(0).setProfileId(userId);
			
			groupProfile.get(0).setCurrentProfile(fileName);
			groupProfile.get(0).getFiles().add(mfile);
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return groupProfileRepository.save(groupProfile.get(0));
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
	public Resource loadFile(String filename) {
		try {
			final Path rootLocation = Paths.get(fileStorageProperty.getUploadDir()).toAbsolutePath().normalize();
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		final Path rootLocation = Paths.get(fileStorageProperty.getUploadDir()).toAbsolutePath().normalize();
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public Resource loadFileAsResource(int fileId) {
		try {
			//Path filePath = this.fileStorageLocation.resolve(fileName).normalize(); /
			MediaFiles mediaFiles = mediaFileRepo.getOne(fileId);
			Path filePath = this.fileStorageLocation.resolve(mediaFiles.getFilePath());
			
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileId);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileId, ex);
		}
	}
	
	
	private String getFileExtension(MultipartFile file) {
	    String name = file.getOriginalFilename();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}
}
