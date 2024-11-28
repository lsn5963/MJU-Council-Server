package depth.mju.council.domain.notice.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class NoticeListRes {

    @Schema(type = "Long", example = "1", description = "공지사항의 id")
    public Long noticeId;

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시 안내", description = "공지사항의 제목")
    public String title;

    @Schema(type = "LocalDateTime", example = "2024-11-17", description = "공지사항의 작성일자")
    public LocalDate createdAt;

    @Builder
    public NoticeListRes(Long noticeId, String title, LocalDateTime createdAt) {
        this.noticeId = noticeId;
        this.title = title;
        this.createdAt = createdAt.toLocalDate();
    }
}
