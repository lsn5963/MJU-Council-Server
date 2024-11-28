package depth.mju.council.domain.banner.repository;

import depth.mju.council.domain.banner.entity.Banner;
import depth.mju.council.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {
    List<Banner> findByUser(User user);
}
