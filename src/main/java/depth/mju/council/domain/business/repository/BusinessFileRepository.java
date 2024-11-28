package depth.mju.council.domain.business.repository;

import depth.mju.council.domain.business.entity.BusinessFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessFileRepository extends JpaRepository<BusinessFile, Long> {
}
