package depth.mju.council.domain.regulation.entity;

import depth.mju.council.domain.BaseEntity;
import depth.mju.council.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "regulation")
public class Regulation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String fileUrl;

    private LocalDate revisionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}