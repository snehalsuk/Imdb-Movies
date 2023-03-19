package com.imdb.imdb.service.impl;


import com.imdb.imdb.entity.NetflixEntity;
import com.imdb.imdb.entity.RegisterEntity;
import com.imdb.imdb.exception.ResourceNotFoundException;
import com.imdb.imdb.payload.NetflixDto;
import com.imdb.imdb.payload.RegisterDto;
import com.imdb.imdb.repo.RegisterRepo;
import com.imdb.imdb.service.RegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepo registerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegisterDto createRegister(RegisterDto registerDto) {
        RegisterEntity registers = this.modelMapper.map(registerDto, RegisterEntity.class);
        RegisterEntity registersave = this.registerRepo.save(registers);

        return this.modelMapper.map(registersave, RegisterDto.class);
    }

    @Override
    public RegisterDto updateRegister(RegisterDto registerDto, Integer registerId) {
        RegisterEntity register = this.registerRepo.findById(registerId)
                .orElseThrow(() -> new ResourceNotFoundException("RegisterEntity", "registerId", registerId));

        register.setName(registerDto.getName());
        register.setEmail(registerDto.getEmail());
        register.setPassword(registerDto.getPassword());

        RegisterEntity updateRegister=this.registerRepo.save(register);
        return this.modelMapper.map(updateRegister, RegisterDto.class);
    }

    @Override
    public RegisterDto getRegisterById(Integer registerId) {
        RegisterEntity register = this.registerRepo.findById(registerId)
                .orElseThrow(() -> new ResourceNotFoundException("RegisterEntity", "registerId", registerId));


        return this.modelMapper.map(register,RegisterDto.class);
    }

    @Override
    public List<RegisterDto> getAllRegister() {
        List<RegisterEntity> allRegister = this.registerRepo.findAll();
        List<RegisterDto> collectAll = allRegister.stream().map((cs) ->
                this.modelMapper.map(cs, RegisterDto.class)).collect(Collectors.toList());

        return collectAll;
    }

    @Override
    public void deleteRegister(Integer registerId) {
        RegisterEntity registers = this.registerRepo.findById(registerId)
                .orElseThrow(() -> new ResourceNotFoundException("RegisterEntity", "registerId", registerId));

        this.registerRepo.delete(registers);
    }
}
