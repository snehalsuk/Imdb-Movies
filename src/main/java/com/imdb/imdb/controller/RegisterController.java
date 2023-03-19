package com.imdb.imdb.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.imdb.payload.RegisterDto;
import com.imdb.imdb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/add")
    public RegisterDto createRegister(@RequestBody RegisterDto registerDto) {
        RegisterDto registerDto1 = this.registerService.createRegister(registerDto);

        return registerDto1;
    }

    @GetMapping("/get")
    public List<RegisterDto> getAllRegister() {

        List<RegisterDto> allRegisters = this.registerService.getAllRegister();

        return allRegisters;
    }


    @GetMapping("/get-id/{registerId}")
    public RegisterDto getByIdRegister(
            @PathVariable Integer registerId) {
        RegisterDto registerById = this.registerService.getRegisterById(registerId);
        return registerById;
    }

    @PutMapping("/update/{registerId}")
    public RegisterDto updateRegister(
            @RequestBody RegisterDto registerDto,
            @PathVariable Integer registerId
    ) {

        RegisterDto registerDto1 = this.registerService.updateRegister(registerDto, registerId);

        return registerDto1;
    }

    @DeleteMapping("/delete/{registerId}")
    public void deleteRegister(
            @PathVariable Integer registerId
    ) {
        this.registerService.deleteRegister(registerId);
    }
}