package com.imdb.imdb.repo;

import com.imdb.imdb.entity.RegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<RegisterEntity,Integer> {

}
