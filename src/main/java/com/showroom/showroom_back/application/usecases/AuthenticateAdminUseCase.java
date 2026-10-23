package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.AuthenticateAdminInputPort;
import com.showroom.showroom_back.application.port.input.dto.AuthenticateAdminCommand;
import com.showroom.showroom_back.application.port.output.AdminRepositoryPort;
import com.showroom.showroom_back.application.port.output.JwtTokenPort;
import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.common.exception.AdminNotFoundException;
import com.showroom.showroom_back.domain.common.exception.InvalidCredentialsException;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateAdminUseCase implements AuthenticateAdminInputPort {

    private final AdminRepositoryPort adminRepository;
    private final JwtTokenPort jwtTokenPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String execute(AuthenticateAdminCommand command) {
        Admin admin;
        try {
            admin = adminRepository.findByEmail(AdminEmail.of(command.email()));
        } catch (AdminNotFoundException e) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(command.password(), admin.password().hashedValue())) {
            throw new InvalidCredentialsException();
        }

        Admin updatedAdmin = admin.recordLogin();
        adminRepository.save(updatedAdmin);
        return jwtTokenPort.generateToken(updatedAdmin);
    }
}