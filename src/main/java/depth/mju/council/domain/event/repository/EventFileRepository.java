package depth.mju.council.domain.event.repository;

import depth.mju.council.domain.event.entity.EventFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventFileRepository extends JpaRepository<EventFile, Long> {
    @Modifying
    @Query("UPDATE EventFile ef SET ef.isDeleted = :isDeleted WHERE ef.event.id = :eventId")
    void updateIsDeletedByEventId(@Param("eventId") Long eventId, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query("UPDATE EventFile ef SET ef.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);

}
