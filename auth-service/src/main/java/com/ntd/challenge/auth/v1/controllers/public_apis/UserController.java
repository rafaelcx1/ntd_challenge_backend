package com.ntd.challenge.auth.v1.controllers.public_apis;

import com.ntd.challenge.auth.v1.controllers.public_apis.documentation.UserControllerDocumentation;
import com.ntd.challenge.auth.v1.controllers.public_apis.requests.UserCreateRequest;
import com.ntd.challenge.auth.v1.controllers.public_apis.responses.UserResponse;
import com.ntd.challenge.auth.v1.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.ntd.challenge.auth.v1.utils.MapperUtils.mapperInstance;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/users")
public class UserController implements UserControllerDocumentation {

	private final UserService userService;

	@Override
	@GetMapping
	public UserResponse get() {
		return mapperInstance().map(userService.get(), UserResponse.class);
	}

	@Override
	@PostMapping("register")
	public void register(@RequestBody @Valid UserCreateRequest request) {
		userService.registerUser(request);
	}
}
