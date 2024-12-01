package depth.mju.council.domain.event.repository;

import depth.mju.council.domain.event.entity.EventDetailFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailFileRepository extends JpaRepository<EventDetailFile, Long> {
    @Modifying
    @Query("UPDATE EventDetailFile edf SET edf.isDeleted = :isDeleted WHERE edf.eventDetail.id = :eventDetailId")
    void updateIsDeletedByEventDetailId(@Param("eventDetailId") Long eventDetailId, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query("UPDATE EventDetailFile edf SET edf.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);

}
