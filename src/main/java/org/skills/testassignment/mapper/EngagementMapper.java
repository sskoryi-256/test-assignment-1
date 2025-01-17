package org.skills.testassignment.mapper;

import org.skills.testassignment.dto.EngagementDto;
import org.skills.testassignment.entity.Engagement;

public class EngagementMapper {

    public static EngagementDto toDto(Engagement engagement) {
        if (engagement == null) {
            return null;
        }
        EngagementDto dto = new EngagementDto();
        dto.setId(engagement.getId());
        dto.setVideoId(engagement.getVideo().getId());
        dto.setImpressions(engagement.getImpressions());
        dto.setViews(engagement.getViews());
        return dto;
    }

}
