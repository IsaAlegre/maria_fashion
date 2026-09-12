package com.showroom.showroom_back.application.port.input;

import com.showroom.showroom_back.application.port.input.dto.AuthenticateAdminCommand;

public interface AuthenticateAdminInputPort {
    String execute(AuthenticateAdminCommand command);
}
