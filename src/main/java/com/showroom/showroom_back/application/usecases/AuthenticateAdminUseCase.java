package com.showroom.showroom_back.application.usecases;

import com.showroom.showroom_back.application.port.input.AuthenticateAdminInputPort;
import com.showroom.showroom_back.application.port.input.dto.AuthenticateAdminCommand;
import com.showroom.showroom_back.application.port.output.AdminRepositoryPort;
import com.showroom.showroom_back.application.port.output.JwtTokenPort;
import com.showroom.showroom_back.domain.admin.Admin;
import com.showroom.showroom_back.domain.shared.valueobjects.AdminEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateAdminUseCase implements AuthenticateAdminInputPort {

    private final AdminRepositoryPort adminRepository;
    private final JwtTokenPort jwtTokenPort;

    @Override
    public String execute(AuthenticateAdminCommand command) {
        Admin admin = adminRepository.findByEmail(AdminEmail.of(command.email()));
        Admin updatedAdmin = admin.recordLogin();
        adminRepository.save(updatedAdmin);
        return jwtTokenPort.generateToken(updatedAdmin);
    }
}