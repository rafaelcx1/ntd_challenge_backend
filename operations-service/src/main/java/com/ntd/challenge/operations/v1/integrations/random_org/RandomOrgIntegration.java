package com.ntd.challenge.operations.v1.integrations.random_org;

import com.ntd.challenge.operations.v1.integrations.random_org.config.RandomOrgFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "random-org", url = "${integrations.random-org.base-url}", configuration = RandomOrgFeignConfig.class)
public interface RandomOrgIntegration {

    @GetMapping("/strings/?num=1&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new")
    String getRandomString(@RequestParam(name = "len") Integer length);
}
