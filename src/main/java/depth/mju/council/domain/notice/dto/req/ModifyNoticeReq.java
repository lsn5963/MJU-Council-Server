package depth.mju.council.domain.notice.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class ModifyNoticeReq {

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시 안내", description = "공지사항의 제목")
    @NotBlank
    public String title;

    @Schema(type = "String", example = "2024학년도 2학기 강의평가 실시를 안내합니다.", description = "공지사항의 내용")
    @NotBlank
    public String content;

    @Schema(type = "List<Integer>", example = "[1, 2, 3]", description = "공지사항 수정 시 지우고자 하는 이미지의 리스트")
    public List<Integer> deleteImages;

    @Schema(type = "List<Integer>", example = "[1, 2, 3]", description = "공지사항 수정 시 지우고자 하는 파일의 리스트")
    public List<Integer> deleteFiles;
}
