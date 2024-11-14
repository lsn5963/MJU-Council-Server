package depth.mju.council.domain.promise.entity;

import depth.mju.council.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "promise")
public class Promise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String progress;

    @ManyToOne
    @JoinColumn(name = "promise_category_id", nullable = false)
    private PromiseCategory promiseCategory;
}