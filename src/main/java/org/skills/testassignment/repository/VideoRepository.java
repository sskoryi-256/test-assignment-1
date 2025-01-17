package org.skills.testassignment.repository;

import org.skills.testassignment.entity.Video;
import org.skills.testassignment.projection.VideoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByDirectorContainingIgnoreCase(String director);

    List<VideoProjection> findBySoftDeletedFalse();

    @Query("SELECT v.title AS title, v.director AS director, v.mainActor AS mainActor, " +
            "v.genre AS genre, v.runningTime AS runningTime " +
            "FROM Video v " +
            "WHERE (:director IS NULL OR LOWER(v.director) LIKE LOWER(CONCAT('%', :director, '%'))) " +
            "AND (:genre IS NULL OR LOWER(v.genre) LIKE LOWER(CONCAT('%', :genre, '%'))) " +
            "AND (:mainActor IS NULL OR LOWER(v.mainActor) LIKE LOWER(CONCAT('%', :mainActor, '%'))) " +
            "AND (:yearOfRelease IS NULL OR v.yearOfRelease = :yearOfRelease) " +
            "AND v.softDeleted = false")
    List<VideoProjection> searchVideos(
            @Param("director") String director,
            @Param("genre") String genre,
            @Param("mainActor") String mainActor,
            @Param("yearOfRelease") Integer yearOfRelease
    );

}
