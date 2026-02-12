package sdt.project.module.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sdt.project.common.base.BaseEntity;
import sdt.project.module.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "passwordResetTokens")
@Getter
@Setter
public class PasswordResetToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    private String token;
    private LocalDateTime expiredAt;
    private boolean used;

    public PasswordResetToken() {
    }

    public PasswordResetToken(User user, String token, LocalDateTime expiredAt, boolean used) {
        this.user = user;
        this.token = token;
        this.expiredAt = expiredAt;
        this.used = used;
    }
}
