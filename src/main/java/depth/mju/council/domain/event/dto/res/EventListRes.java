package depth.mju.council.domain.event.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class EventListRes {
    @Schema(type = "Long", example = "1", description = "행사의 id")
    public Long eventId;

    @Schema(type = "String", example = "2024년 명지대학교 축제", description = "행사의 제목")
    public String title;

    @Schema(type = "String", example = "https://council-s3-bucket.s3.amazonaws.com/image/79fdc2c6-02e42b-ffb2fde8b189.png", description = "커버 이미지의 주소")
    public String cover;

    @Schema(type = "LocalDate", example = "2024-11-17", description = "행사의 시작일자")
    public LocalDate startDate;

    @Schema(type = "LocalDate", example = "2024-11-17", description = "행사의 종료일자")
    public LocalDate endDate;

    @Builder
    public EventListRes(Long eventId, String title, String cover, LocalDate startDate, LocalDate endDate) {
        this.eventId = eventId;
        this.title = title;
        this.cover = cover;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
