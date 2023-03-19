package com.imdb.imdb.service.impl;

import com.imdb.imdb.entity.NetflixEntity;
import com.imdb.imdb.exception.ResourceNotFoundException;
import com.imdb.imdb.payload.NetflixDto;
import com.imdb.imdb.repo.NetflixRepo;
import com.imdb.imdb.service.NetflixService;
import jakarta.persistence.Access;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NetflixServiceImpl implements NetflixService {

@Autowired
    private NetflixRepo netflixRepo;

@Autowired
private ModelMapper modelMapper;
    @Override
    public NetflixDto createNetflix(NetflixDto netflixDto) {

        NetflixEntity netflixs = this.modelMapper.map(netflixDto, NetflixEntity.class);
        NetflixEntity netflixsave = this.netflixRepo.save(netflixs);

        return this.modelMapper.map(netflixsave,NetflixDto.class);

    }



    @Override
    public NetflixDto updateNetflix(NetflixDto netflixDto, Integer netflixId) {

        NetflixEntity netflix = this.netflixRepo.findById(netflixId)
                .orElseThrow(() -> new ResourceNotFoundException("NetflixEntity", "netflixId", netflixId));


       netflix.setTitle(netflixDto.getTitle());
       netflix.setDescription(netflixDto.getDescription());
       netflix.setImageName(netflixDto.getImageName());
       NetflixEntity updateNetflix=this.netflixRepo.save(netflix);
        return this.modelMapper.map(updateNetflix,NetflixDto.class);


    }

    @Override
    public NetflixDto getNetflixById(Integer netflixId) {

        NetflixEntity netflix = this.netflixRepo.findById(netflixId)
                .orElseThrow(() -> new ResourceNotFoundException("NetflixEntity", "netflixId", netflixId));


        return this.modelMapper.map(netflix,NetflixDto.class);
    }



    @Override
    public List<NetflixDto> getAllNetflix() {

        List<NetflixEntity> allNetflix = this.netflixRepo.findAll();
        List<NetflixDto> collectAll = allNetflix.stream().map((cs) -> this.modelMapper.map(cs, NetflixDto.class)).collect(Collectors.toList());

        return collectAll;
    }

    @Override
    public void deleteNetflix(Integer netflixId) {

        NetflixEntity netflixs = this.netflixRepo.findById(netflixId)
                .orElseThrow(() -> new ResourceNotFoundException("NetflixEntity", "netflixId", netflixId));

        this.netflixRepo.delete(netflixs);
    }

    @Override
    public List<NetflixDto> searchMovie(String keyword) {
        List<NetflixEntity> netflix=this.netflixRepo.findByTitleContaining(keyword);

        List<NetflixDto> netflixDtos = netflix.stream().map((net) -> this.modelMapper.map(net, NetflixDto.class))
                .collect(Collectors.toList());

        return netflixDtos;
    }

}
