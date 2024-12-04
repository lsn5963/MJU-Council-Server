package depth.mju.council.domain.alliance.repository;

import depth.mju.council.domain.alliance.entity.Alliance;
import depth.mju.council.domain.alliance.entity.AllianceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllianceFileRepository extends JpaRepository<AllianceFile, Long> {

    @Modifying
    @Query("DELETE FROM AllianceFile af WHERE af.alliance = :alliance")
    void deleteFilesByAlliance(@Param("alliance") Alliance alliance);
}
