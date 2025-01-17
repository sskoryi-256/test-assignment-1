package org.skills.testassignment.filter;

import lombok.Data;

@Data
public class VideoSearchFilter {

    private String director;
    private String genre;
    private String mainActor;
    private Integer yearOfRelease;

}
