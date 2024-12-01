package depth.mju.council.domain.event.repository;

import depth.mju.council.domain.event.entity.EventGuideFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventGuideFileRepository extends JpaRepository<EventGuideFile, Long> {
    @Modifying
    @Query("UPDATE EventGuideFile egf SET egf.isDeleted = :isDeleted WHERE egf.eventGuide.id = :eventGuideId")
    void updateIsDeletedByEventGuideId(@Param("eventGuideId") Long eventGuideId, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query("UPDATE EventGuideFile egf SET egf.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);

}
