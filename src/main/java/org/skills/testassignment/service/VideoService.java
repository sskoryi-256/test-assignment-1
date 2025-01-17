package org.skills.testassignment.service;

import lombok.RequiredArgsConstructor;
import org.skills.testassignment.entity.Engagement;
import org.skills.testassignment.entity.Video;
import org.skills.testassignment.filter.VideoSearchFilter;
import org.skills.testassignment.projection.VideoProjection;
import org.skills.testassignment.repository.EngagementRepository;
import org.skills.testassignment.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final EngagementRepository engagementRepository;

    public List<VideoProjection> searchVideos(VideoSearchFilter filter) {
        return videoRepository.searchVideos(
                filter.getDirector(),
                filter.getGenre(),
                filter.getMainActor(),
                filter.getYearOfRelease()
        );
    }

    public Video publishVideo(Video video) {
        Video savedVideo = videoRepository.save(video);
        engagementRepository.save(new Engagement(null, savedVideo, 0, 0));
        return savedVideo;
    }

    public Video editVideo(Long id, Video updatedVideo) {
        Optional<Video> existingVideo = videoRepository.findById(id);
        if (existingVideo.isPresent()) {
            Video video = existingVideo.get();
            video.setTitle(updatedVideo.getTitle());
            video.setSynopsis(updatedVideo.getSynopsis());
            video.setDirector(updatedVideo.getDirector());
            video.setCastMember(updatedVideo.getCastMember());
            video.setGenre(updatedVideo.getGenre());
            video.setYearOfRelease(updatedVideo.getYearOfRelease());
            video.setRunningTime(updatedVideo.getRunningTime());
            return videoRepository.save(video);
        }
        throw new RuntimeException("Video not found");
    }

    public void softlyDeleteVideo(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found"));
        video.setSoftDeleted(true);
        videoRepository.save(video);
    }

    public Video loadVideo(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found"));
    }

    public List<VideoProjection> listAvailableVideos() {
        return videoRepository.findBySoftDeletedFalse();
    }


}
