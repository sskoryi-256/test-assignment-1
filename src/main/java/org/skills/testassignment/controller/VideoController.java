package org.skills.testassignment.controller;

import lombok.RequiredArgsConstructor;
import org.skills.testassignment.dto.EngagementDto;
import org.skills.testassignment.dto.VideoDto;
import org.skills.testassignment.entity.Engagement;
import org.skills.testassignment.entity.Video;
import org.skills.testassignment.filter.VideoSearchFilter;
import org.skills.testassignment.mapper.EngagementMapper;
import org.skills.testassignment.mapper.VideoMapper;
import org.skills.testassignment.projection.VideoProjection;
import org.skills.testassignment.service.EngagementService;
import org.skills.testassignment.service.VideoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final EngagementService engagementService;

    @PostMapping
    public VideoDto publishVideo(@RequestBody VideoDto videoDto) {
        Video video = videoService.publishVideo(VideoMapper.toEntity(videoDto));
        return VideoMapper.toDto(video);
    }

    @PutMapping("/{id}")
    public VideoDto editVideo(@PathVariable Long id, @RequestBody VideoDto updatedVideoDto) {
        Video video = videoService.editVideo(id, VideoMapper.toEntity(updatedVideoDto));
        return VideoMapper.toDto(video);
    }

    @DeleteMapping("/{id}")
    public void softlyDeleteVideo(@PathVariable Long id) {
        videoService.softlyDeleteVideo(id);
    }

    @GetMapping("/{id}")
    public VideoDto loadVideo(@PathVariable Long id) {
        engagementService.incrementImpressions(id);
        return VideoMapper.toDto(videoService.loadVideo(id));
    }

    @PostMapping("/{id}/play")
    public String playVideo(@PathVariable Long id) {
        engagementService.incrementViews(id);
        return "Mock video content for video ID: " + id;
    }

    @GetMapping
    public List<VideoProjection> listAvailableVideos() {
        return videoService.listAvailableVideos();
    }

    @PostMapping("/search")
    public List<VideoProjection> searchVideos(@RequestBody VideoSearchFilter filter) {
        return videoService.searchVideos(filter);
    }

    @GetMapping("/{id}/engagement")
    public EngagementDto getEngagement(@PathVariable Long id) {
        return EngagementMapper.toDto(engagementService.getEngagement(id));
    }

}
