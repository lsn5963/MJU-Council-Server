package depth.mju.council.domain.promise.repository;

import depth.mju.council.domain.promise.entity.PromiseCategory;
import depth.mju.council.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromiseCategoryRepository extends JpaRepository<PromiseCategory,Long> {

    List<PromiseCategory> findByUser(User user);

    PromiseCategory findByUserAndTitle(User user, String policyTitle);
}
