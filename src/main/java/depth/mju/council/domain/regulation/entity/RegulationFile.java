package depth.mju.council.domain.regulation.entity;

import depth.mju.council.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "regulation_file")
public class RegulationFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "regulation_id", nullable = false)
    private Regulation regulation;

    private String fileName;
}