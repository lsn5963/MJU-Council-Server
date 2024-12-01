package depth.mju.council.domain.event.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class EventDetailListRes {
    @Schema(type = "Long", example = "1", description = "행사 세부사항의 id")
    public Long eventDetailId;

    @Schema(type = "String", example = "2024년 명지대학교 축제 세부사항01", description = "행사 세부사항의 제목")
    public String title;

    @Schema(type = "String", example = "https://council-s3-bucket.s3.amazonaws.com/image/79fdc2c6-02e42b-ffb2fde8b189.png", description = "커버 이미지의 주소")
    public String cover;

    @Schema(type = "LocalDate", example = "2024-11-17", description = "행사 세부사항의 작성일자")
    public LocalDate createdAt;

    @Builder
    public EventDetailListRes(Long eventDetailId, String title, String cover, LocalDateTime createdAt) {
        this.eventDetailId = eventDetailId;
        this.title = title;
        this.cover = cover;
        this.createdAt = createdAt.toLocalDate();
    }
}
