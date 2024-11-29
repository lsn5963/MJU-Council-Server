package depth.mju.council.domain.orgarnization.repository;

import depth.mju.council.domain.orgarnization.entity.Organization;
import depth.mju.council.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
