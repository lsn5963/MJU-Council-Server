package depth.mju.council.domain.promise.repository;

import depth.mju.council.domain.promise.entity.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromiseRepository extends JpaRepository<Promise,Long> {
}
