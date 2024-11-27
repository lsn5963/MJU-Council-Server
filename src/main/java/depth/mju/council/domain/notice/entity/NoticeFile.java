package depth.mju.council.domain.notice.entity;

import depth.mju.council.domain.common.BaseEntity;
import depth.mju.council.domain.common.ContentType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "notice_file")
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
    private ContentType contentType;
}