package com.imdb.imdb.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NetflixDto {


    private int id;
    private String title;

    private String description;
    private String imageName;

//    private String movieLinks;
}
