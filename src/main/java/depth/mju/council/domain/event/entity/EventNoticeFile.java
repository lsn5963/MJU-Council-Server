package depth.mju.council.domain.event.entity;

import depth.mju.council.domain.common.BaseEntity;
import depth.mju.council.domain.common.FileType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "event_notice_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventNoticeFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "event_notice_id", nullable = false)
    private EventNotice eventNotice;

    private String fileName;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    @Builder
    public EventNoticeFile(String fileUrl, String fileName, EventNotice eventNotice, FileType fileType) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.eventNotice = eventNotice;
        this.fileType = fileType;
    }
}