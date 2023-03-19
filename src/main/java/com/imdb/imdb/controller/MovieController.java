package com.imdb.imdb.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.imdb.entity.NetflixEntity;
import com.imdb.imdb.payload.NetflixDto;
import com.imdb.imdb.service.FileService;
import com.imdb.imdb.service.NetflixService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonParser.Feature;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;


@CrossOrigin(origins = {"http://localhost:3000"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.OPTIONS})
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private FileService fileService;


    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NetflixService netflixService;


    @Value("${project.image}")
    private String path;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public NetflixDto createNetflix(@RequestBody NetflixDto netflixDto) {
        NetflixDto netflixDto1 = this.netflixService.createNetflix(netflixDto);

        return netflixDto1;
    }

    @GetMapping("/get")

    public List<NetflixDto> getAllNetflix() {
        System.out.println("data");
        List<NetflixDto> allNetflixs = this.netflixService.getAllNetflix();

        return allNetflixs;
    }


    @GetMapping("/get-id/{netflixId}")
    public NetflixDto getByIdNetflix(
            @PathVariable Integer netflixId) {
        NetflixDto netflixById = this.netflixService.getNetflixById(netflixId);
        return netflixById;
    }

    @PutMapping("/update/{netflixId}")
    public NetflixDto updateNetflix(
            @RequestBody NetflixDto netflixDto,
            @PathVariable Integer netflixId
    ) {

        NetflixDto netflixDto1 = this.netflixService.updateNetflix(netflixDto, netflixId);

        return netflixDto1;
    }

    @DeleteMapping("/delete/{netflixId}")
    public void deleteNetflix(
            @PathVariable Integer netflixId
    ) {
        this.netflixService.deleteNetflix(netflixId);
    }


    @PostMapping("/image/")
    public ResponseEntity<NetflixDto> uploadPostImage(
            @RequestParam(value = "image") MultipartFile imageName,

            @RequestParam("userData") String userData
//            @ModelAttribute NetflixDto netflixDto
    ) throws IOException {

        System.out.println("image and data1");
        NetflixDto netflixDto = null;
        System.out.println("image and data2");
        netflixDto = mapper.readValue(userData, NetflixDto.class);
//        netflixDto = mapper.readValue( netDto.getDescription(), NetflixDto.class);

        System.out.println("image and data3");
        String fileName = this.fileService.uploadImage(path, imageName);
//        netflixDto.setTitle(netflixDto.getTitle());
//        netflixDto.setDescription(netflixDto.getDescription());
        System.out.println("image and data4");
        netflixDto.setImageName(fileName);
        System.out.println("image and data5");
        NetflixDto postNetflix = this.netflixService.createNetflix(netflixDto);
        System.out.println("image and data6");
        return new ResponseEntity<NetflixDto>(postNetflix, HttpStatus.OK);
    }


    //method to serve image
    @GetMapping(value = "image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @PutMapping("/image/{netflixId}")
    public ResponseEntity<NetflixDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userData") String userData
            , @PathVariable Integer netflixId
    ) throws IOException {
        NetflixDto netflixDto = this.netflixService.getNetflixById(netflixId);

        String fileName = this.fileService.uploadImage(path, image);
        netflixDto.setImageName(fileName);
        NetflixDto updateNetflix = this.netflixService.updateNetflix(netflixDto, netflixId);

        return new ResponseEntity<NetflixDto>(updateNetflix, HttpStatus.OK);

    }

    @DeleteMapping("/image/{netflixId}")
    public ResponseEntity<?> uploadPostImage(
            @PathVariable Integer netflixId
    ) throws IOException {
        this.netflixService.deleteNetflix(netflixId);


        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/upload")
    public ResponseEntity<?> PostImage(
            @RequestParam(value = "image") MultipartFile imageName,

            @RequestParam("userData") String userData
    ) {
        System.out.println("image=" + imageName);
        System.out.println("hello");
        System.out.println(userData);
//        System.out.println(userData.getTitle());
//        System.out.println(userData.getDescription());
        return null;
    }


    @PostMapping(value = "/upload/data",
            consumes = {"multipart/form-data"})
    public void upload(@RequestParam("image") MultipartFile imageName
,
                       @RequestParam("userData") String userData
//                       @ModelAttribute("userData") String  userData
//                       ,@RequestBody NetflixDto userData
    ) throws IOException {

        NetflixDto netflixDto=null;

        netflixDto = mapper.readValue( userData,NetflixDto.class);
        System.out.println(imageName.getOriginalFilename());
        String fileName = this.fileService.uploadImage(path, imageName);
        System.out.println(fileName);
        netflixDto.setImageName(fileName);
     NetflixDto postNetflix = this.netflixService.createNetflix(netflixDto);

        System.out.println(postNetflix);
    }


    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<NetflixDto>> searchProductByTitle(
            @PathVariable("keywords") String keywords
    ){

        List<NetflixDto> result= this.netflixService.searchMovie(keywords);

        return new ResponseEntity<List<NetflixDto>>(result,HttpStatus.OK);
    }


}