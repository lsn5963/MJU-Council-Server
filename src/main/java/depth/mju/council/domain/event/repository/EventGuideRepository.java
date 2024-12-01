package depth.mju.council.domain.event.repository;

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
}
