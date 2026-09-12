package com.showroom.showroom_back.domain.admin;

import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminId;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminPassword;

import java.time.LocalDateTime;
import java.util.Objects;


public record Admin(
        AdminId id,
        AdminEmail email,
        AdminPassword password,
        LocalDateTime createdAt,
        LocalDateTime lastLogin
) {
    // Constructor compacto valida
    public Admin {
        Objects.requireNonNull(id, "Admin id cannot be null");
        Objects.requireNonNull(email, "Admin email cannot be null");
        Objects.requireNonNull(password, "Admin password cannot be null");
        Objects.requireNonNull(createdAt, "Admin createdAt cannot be null");
    }

    // Factory method para crear nuevo admin
    public static Admin create(String email, String hashedPassword) {
        Objects.requireNonNull(email);
        Objects.requireNonNull(hashedPassword);

        LocalDateTime now = LocalDateTime.now();
        return new Admin(
                AdminId.generate(),
                AdminEmail.of(email),
                new AdminPassword(hashedPassword),
                now,
                null
        );
    }

    // Registrar un login exitoso
    public Admin recordLogin() {
        return new Admin(
                this.id,
                this.email,
                this.password,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    // Cambiar password
    public Admin changePassword(String newHashedPassword) {
        Objects.requireNonNull(newHashedPassword);
        return new Admin(
                this.id,
                this.email,
                new AdminPassword(newHashedPassword),
                this.createdAt,
                this.lastLogin
        );
    }
}