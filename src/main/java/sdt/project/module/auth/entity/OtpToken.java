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
@Table(name = "otpTokens")
@Getter
@Setter
public class OtpToken extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    private String otp;
    private LocalDateTime expiredAt;
    private boolean used;

    public OtpToken() {
    }

    public OtpToken(User user, String otp, LocalDateTime expiredAt, boolean used) {
        this.user = user;
        this.otp = otp;
        this.expiredAt = expiredAt;
        this.used = used;
    }
}
