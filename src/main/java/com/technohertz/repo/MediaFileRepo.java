package com.technohertz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.technohertz.model.MediaFiles;

@Repository
public interface MediaFileRepo extends JpaRepository<MediaFiles, Integer> {
	@Query(value="SELECT  *  FROM media_files  WHERE usr_det_id=?1",nativeQuery=true)
	
	List<MediaFiles>	findById(int id);
	
	@Query(value="SELECT  r from MediaFiles r WHERE r.fileId=?1")
	MediaFiles getById(int userId);
}
