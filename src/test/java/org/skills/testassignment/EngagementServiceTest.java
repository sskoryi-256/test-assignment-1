package org.skills.testassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skills.testassignment.entity.Engagement;
import org.skills.testassignment.entity.Video;
import org.skills.testassignment.repository.EngagementRepository;
import org.skills.testassignment.service.EngagementService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EngagementServiceTest {

    @Mock
    private EngagementRepository engagementRepository;

    @InjectMocks
    private EngagementService engagementService;

    private Engagement mockEngagement;

    @BeforeEach
    void setUp() {
        Video video = new Video();
        video.setId(1L);

        mockEngagement = new Engagement();
        mockEngagement.setId(100L);
        mockEngagement.setVideo(video);
        mockEngagement.setImpressions(5);
        mockEngagement.setViews(10);
    }

    @Test
    void getEngagement_success() {
        when(engagementRepository.findByVideoId(1L)).thenReturn(mockEngagement);

        Engagement result = engagementService.getEngagement(1L);

        assertEquals(5, result.getImpressions());
        assertEquals(10, result.getViews());
        verify(engagementRepository).findByVideoId(1L);
    }

    @Test
    void incrementImpressions_success() {
        engagementService.incrementImpressions(1L);

        verify(engagementRepository, times(1))
                .incrementCounters(1L, 1, 0);
    }

    @Test
    void incrementViews_success() {
        // Act
        engagementService.incrementViews(1L);

        // Assert
        verify(engagementRepository, times(1))
                .incrementCounters(1L, 0, 1);
    }
}
