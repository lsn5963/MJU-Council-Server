package depth.mju.council.domain.business.repository;

import depth.mju.council.domain.business.entity.BusinessFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessFileRepository extends JpaRepository<BusinessFile, Long> {

    @Modifying
    @Query("UPDATE BusinessFile bf SET bf.isDeleted = :isDeleted WHERE bf.business.id = :businessId")
    void updateIsDeletedByBusinessId(@Param("businessId") Long businessId, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query("UPDATE BusinessFile bf SET bf.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);
}
