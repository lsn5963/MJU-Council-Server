package depth.mju.council.domain.user.entity;

import depth.mju.council.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String encryptedPwd;
    private String refreshToken;
    private String generation;
    private String name;
    private String email;
    private String snsUrl;

    @Column(columnDefinition = "TEXT")
    private String logoUrl;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}