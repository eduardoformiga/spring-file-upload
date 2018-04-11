package com.example.videoapi.demo.repository;

import com.example.videoapi.demo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Video, Long> {
}
