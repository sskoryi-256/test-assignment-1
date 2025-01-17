package org.skills.testassignment.dto;

import lombok.Data;

@Data
public class VideoDto {

    private Long id;
    private String title;
    private String synopsis;
    private String director;
    private String castMember;
    private String mainActor;
    private String genre;
    private Integer yearOfRelease;
    private Integer runningTime;
    private Boolean softDeleted;

}
