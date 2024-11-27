package depth.mju.council.domain.notice.repository;


import depth.mju.council.domain.notice.dto.res.NoticeListResponse;
import depth.mju.council.domain.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Modifying
    @Query("UPDATE Notice n SET n.isDeleted = :isDeleted")
    void updateIsDeletedForAll(@Param("isDeleted") boolean isDeleted);

    Optional<Notice> findByIdAndIsDeleted(Long noticeId, boolean b);

    @Query("SELECT new depth.mju.council.domain.notice.dto.res.NoticeListResponse(n.id, n.title, n.createdAt) " +
            "FROM Notice n " +
            "WHERE n.title LIKE %:keyword% AND n.isDeleted = false")
    Page<NoticeListResponse> findByTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT new depth.mju.council.domain.notice.dto.res.NoticeListResponse(n.id, n.title, n.createdAt) " +
            "FROM Notice n " +
            "WHERE n.isDeleted = false")
    Page<NoticeListResponse> findAllNotices(Pageable pageable);
}
