package depth.mju.council.domain.alliance.repository;

import depth.mju.council.domain.alliance.entity.AllianceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllianceFileRepository extends JpaRepository<AllianceFile, Long> {
}
