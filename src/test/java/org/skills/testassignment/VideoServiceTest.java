package org.skills.testassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skills.testassignment.entity.Engagement;
import org.skills.testassignment.entity.Video;
import org.skills.testassignment.repository.EngagementRepository;
import org.skills.testassignment.repository.VideoRepository;
import org.skills.testassignment.service.VideoService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private EngagementRepository engagementRepository;

    @InjectMocks
    private VideoService videoService;

    private Video mockVideo;

    @BeforeEach
    void setUp() {
        mockVideo = new Video();
        mockVideo.setId(1L);
        mockVideo.setTitle("Test Movie");
        mockVideo.setDirector("Test Director");
        mockVideo.setSoftDeleted(false);
    }

    @Test
    void publishVideo_success() {
        when(videoRepository.save(any(Video.class))).thenReturn(mockVideo);

        Video result = videoService.publishVideo(mockVideo);

        assertEquals("Test Movie", result.getTitle());
        verify(videoRepository, times(1)).save(mockVideo);
        verify(engagementRepository, times(1)).save(any(
                Engagement.class));
    }

    @Test
    void editVideo_success() {
        Video updatedVideo = new Video();
        updatedVideo.setTitle("Updated Title");
        updatedVideo.setDirector("Updated Director");

        when(videoRepository.findById(1L)).thenReturn(Optional.of(mockVideo));
        when(videoRepository.save(any(Video.class))).thenAnswer(inv -> inv.getArgument(0));

        Video result = videoService.editVideo(1L, updatedVideo);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Director", result.getDirector());
        verify(videoRepository).save(mockVideo);
    }

    @Test
    void editVideo_notFound() {
        when(videoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            videoService.editVideo(2L, new Video());
        });
    }

    @Test
    void softlyDeleteVideo_success() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(mockVideo));

        videoService.softlyDeleteVideo(1L);

        assertTrue(mockVideo.getSoftDeleted());
        verify(videoRepository).save(mockVideo);
    }

    @Test
    void loadVideo_success() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(mockVideo));

        Video result = videoService.loadVideo(1L);

        assertNotNull(result);
        assertEquals("Test Movie", result.getTitle());
    }

    @Test
    void loadVideo_notFound() {
        when(videoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            videoService.loadVideo(2L);
        });
    }
}