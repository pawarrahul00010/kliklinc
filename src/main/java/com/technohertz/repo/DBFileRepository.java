package com.technohertz.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technohertz.model.MediaFiles;

@Repository
public interface DBFileRepository extends JpaRepository<MediaFiles, String> {

}
