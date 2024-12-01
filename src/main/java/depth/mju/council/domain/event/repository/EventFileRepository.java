package depth.mju.council.domain.event.repository;

import depth.mju.council.domain.event.entity.EventFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventFileRepository extends JpaRepository<EventFile, Long> {
}
