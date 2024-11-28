package depth.mju.council.domain.promise.repository;

import depth.mju.council.domain.promise.entity.Promise;
import depth.mju.council.domain.promise.entity.PromiseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromiseRepository extends JpaRepository<Promise,Long> {
    List<Promise> findByPromiseCategory(PromiseCategory promiseCategory);
}
