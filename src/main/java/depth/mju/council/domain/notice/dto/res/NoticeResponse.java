package depth.mju.council.domain.notice.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NoticeResponse {

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시 안내", description = "공지사항의 제목")
    public String title;

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시를 안내합니다.", description = "공지사항의 내용")
    public String content;

    @Schema(type = "LocalDate", example = "2024-11-17", description = "공지사항의 작성일자")
    public LocalDate createdDate;

    @Schema(description = "Schemas의 FileRes를 참고해주세요. 이미지의 리스트입니다.")
    public List<FileRes> images;

    @Schema(description = "Schemas의 FileRes를 참고해주세요. 파일의 리스트입니다.")
    public List<FileRes> files;

    @Builder
    public NoticeResponse(String title, String content, LocalDate createdDate, List<FileRes> images, List<FileRes> files) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.images = images;
        this.files = files;
    }
}
