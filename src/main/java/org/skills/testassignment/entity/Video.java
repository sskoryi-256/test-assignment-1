package org.skills.testassignment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String synopsis;

    private String director;

    @Column(name = "cast_member")
    private String castMember;

    private String mainActor;

    private String genre;

    @Column(name = "year_of_release")
    private Integer yearOfRelease;

    @Column(name = "running_time")
    private Integer runningTime;

    @Column(name = "soft_deleted")
    private Boolean softDeleted = false;

}
