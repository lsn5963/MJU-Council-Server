package depth.mju.council.domain.alliance.repository;

import depth.mju.council.domain.alliance.entity.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllianceRepository extends JpaRepository<Alliance, Long> {
}
