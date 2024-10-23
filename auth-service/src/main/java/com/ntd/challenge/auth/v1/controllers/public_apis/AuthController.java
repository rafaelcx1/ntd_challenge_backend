package com.ntd.challenge.auth.v1.controllers.public_apis;

import com.ntd.challenge.auth.v1.controllers.public_apis.documentation.AuthControllerDocumentation;
import com.ntd.challenge.auth.v1.controllers.public_apis.requests.LoginRequest;
import com.ntd.challenge.auth.v1.controllers.public_apis.responses.LoginResponse;
import com.ntd.challenge.auth.v1.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/auth")
public class AuthController implements AuthControllerDocumentation {

	private final AuthService authService;


	@PostMapping
	public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
		String loginToken = authService.login(loginRequest);
		return LoginResponse.builder().token(loginToken).build();
	}
}
