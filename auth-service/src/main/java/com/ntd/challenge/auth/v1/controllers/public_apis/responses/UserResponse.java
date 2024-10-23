package com.ntd.challenge.auth.v1.controllers.public_apis.responses;

import com.ntd.challenge.auth.v1.entities.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String email;
    private UserStatusEnum status;
}
