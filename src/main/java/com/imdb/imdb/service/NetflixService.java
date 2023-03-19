package com.imdb.imdb.service;

import com.imdb.imdb.payload.NetflixDto;

import java.util.List;

public interface NetflixService {

    NetflixDto createNetflix(NetflixDto netflixDto);
    NetflixDto updateNetflix(NetflixDto netflixDto,Integer netflixId);
    NetflixDto getNetflixById(Integer netflixId);
    List<NetflixDto> getAllNetflix();
    void deleteNetflix (Integer netflixId);

    List<NetflixDto> searchMovie(String keyword);


}
