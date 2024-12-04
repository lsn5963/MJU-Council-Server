package depth.mju.council.domain.alliance.repository;

import depth.mju.council.domain.alliance.entity.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllianceRepository extends JpaRepository<Alliance, Long> {
    Optional<Alliance> findByIdAndIsDeleted(Long allianceId, boolean b);

    @Modifying
    @Query("UPDATE Alliance a SET a.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);
}
