package sdt.project.module.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sdt.project.common.base.BaseEntity;
import sdt.project.module.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "refreshTokens")
@Getter
@Setter
public class RefreshToken extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    private LocalDateTime expiredAt;
    private LocalDateTime revokedAt;

    public RefreshToken() {
    }

    public RefreshToken(User user, String token, LocalDateTime expiredAt, LocalDateTime revokedAt) {
        this.user = user;
        this.token = token;
        this.expiredAt = expiredAt;
        this.revokedAt = revokedAt;
    }
}
