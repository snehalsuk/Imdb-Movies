package com.imdb.imdb.repo;

import com.imdb.imdb.entity.NetflixEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NetflixRepo extends JpaRepository<NetflixEntity,Integer> {


    List<NetflixEntity> findByTitleContaining(String title);
}
