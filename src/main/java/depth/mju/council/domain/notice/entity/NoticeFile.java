package depth.mju.council.domain.notice.entity;

import depth.mju.council.domain.common.BaseEntity;
import depth.mju.council.domain.common.FileType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "notice_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    private String fileName;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    @Builder
    public NoticeFile(String fileUrl, String fileName, Notice notice, FileType fileType) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.notice = notice;
        this.fileType = fileType;
    }
}