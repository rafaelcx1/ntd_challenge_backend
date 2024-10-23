package com.ntd.challenge.operations.v1.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigurationProperties {
	
	private String secret;
	private Long expiration;
	private String issuer;
}
