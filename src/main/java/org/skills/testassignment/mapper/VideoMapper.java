package org.skills.testassignment.mapper;

import org.skills.testassignment.dto.VideoDto;
import org.skills.testassignment.entity.Video;

public class VideoMapper {

    public static VideoDto toDto(Video video) {
        if (video == null) {
            return null;
        }
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setSynopsis(video.getSynopsis());
        dto.setDirector(video.getDirector());
        dto.setCastMember(video.getCastMember());
        dto.setMainActor(video.getMainActor());
        dto.setGenre(video.getGenre());
        dto.setYearOfRelease(video.getYearOfRelease());
        dto.setRunningTime(video.getRunningTime());
        dto.setSoftDeleted(video.getSoftDeleted());
        return dto;
    }

    public static Video toEntity(VideoDto dto) {
        if (dto == null) {
            return null;
        }
        Video video = new Video();
        video.setId(dto.getId());
        video.setTitle(dto.getTitle());
        video.setSynopsis(dto.getSynopsis());
        video.setDirector(dto.getDirector());
        video.setCastMember(dto.getCastMember());
        video.setMainActor(dto.getMainActor());
        video.setGenre(dto.getGenre());
        video.setYearOfRelease(dto.getYearOfRelease());
        video.setRunningTime(dto.getRunningTime());
        video.setSoftDeleted(dto.getSoftDeleted());
        return video;
    }
}