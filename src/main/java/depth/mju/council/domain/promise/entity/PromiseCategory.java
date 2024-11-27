package depth.mju.council.domain.promise.entity;

import depth.mju.council.domain.common.BaseEntity;
import depth.mju.council.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "promise_category")
public class PromiseCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}