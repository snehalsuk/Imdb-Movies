package com.imdb.imdb.service;


import com.imdb.imdb.payload.RegisterDto;

import java.util.List;

public interface RegisterService {

    RegisterDto createRegister(RegisterDto registerDto);
    RegisterDto updateRegister(RegisterDto registerDto,Integer registerId);
    RegisterDto getRegisterById(Integer registerId);
    List<RegisterDto> getAllRegister();
    void deleteRegister (Integer registerId);
}
