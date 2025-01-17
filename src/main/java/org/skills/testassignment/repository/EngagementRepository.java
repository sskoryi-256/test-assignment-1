package org.skills.testassignment.repository;

import org.skills.testassignment.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long> {

    Engagement findByVideoId(Long videoId);

    @Modifying
    @Transactional
    @Query("UPDATE Engagement e " +
            "SET e.impressions = e.impressions + :impDelta, " +
            "    e.views       = e.views + :viewsDelta " +
            "WHERE e.video.id = :videoId")
    void incrementCounters(@Param("videoId") Long videoId,
                           @Param("impDelta") int impDelta,
                           @Param("viewsDelta") int viewsDelta);

}
