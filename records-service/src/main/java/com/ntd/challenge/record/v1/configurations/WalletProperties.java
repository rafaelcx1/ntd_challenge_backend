package com.ntd.challenge.record.v1.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Component
@ConfigurationProperties("wallets")
public class WalletProperties {

    private BigDecimal initialBalance;
}
