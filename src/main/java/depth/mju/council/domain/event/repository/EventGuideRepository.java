package depth.mju.council.domain.event.repository;

import depth.mju.council.domain.event.dto.res.EventGuideListRes;
import depth.mju.council.domain.event.entity.EventGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventGuideRepository extends JpaRepository<EventGuide, Long> {
    @Modifying
    @Query("UPDATE EventGuide eg SET eg.isDeleted = :isDeleted WHERE eg.event.id = :eventId")
    void updateIsDeletedByEventId(@Param("eventId") Long eventId, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query("UPDATE EventGuide eg SET eg.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);

    List<EventGuide> findByEventId(Long eventId);

    @Query(value = "SELECT e.id AS eventGuideId, e.title AS title, " +
            "(SELECT egf.file_url FROM event_guide_file egf WHERE egf.event_guide_id = e.id ORDER BY egf.created_at ASC LIMIT 1) AS cover, " +
            "e.created_at AS createdAt " +
            "FROM event_guide e " +
            "WHERE e.event_id = :eventId AND e.is_deleted = :isDeleted", nativeQuery = true)
    List<EventGuideListRes> findEventGuidesByEventId(@Param("eventId") Long eventId, @Param("isDeleted") boolean isDeleted);


}
