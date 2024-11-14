package depth.mju.council.domain.user.entity;

import depth.mju.council.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String generation;
    private String name;
    private String email;
    private String snsUrl;

    @Column(columnDefinition = "TEXT")
    private String logoUrl;
}