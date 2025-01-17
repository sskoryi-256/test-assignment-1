package org.skills.testassignment.service;

import lombok.RequiredArgsConstructor;
import org.skills.testassignment.entity.Engagement;
import org.skills.testassignment.repository.EngagementRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EngagementService {

    private final EngagementRepository engagementRepository;

    public Engagement getEngagement(Long videoId) {
        return engagementRepository.findByVideoId(videoId);
    }

    public void incrementImpressions(Long videoId) {
        engagementRepository.incrementCounters(videoId, 1, 0);
    }

    public void incrementViews(Long videoId) {
        engagementRepository.incrementCounters(videoId, 0, 1);
    }

}
