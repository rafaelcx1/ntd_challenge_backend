package com.ntd.challenge.operations.v1.services.impl;

import com.ntd.challenge.operations.v1.exceptions.types.InvalidRandomStringLengthException;
import com.ntd.challenge.operations.v1.integrations.random_org.RandomOrgIntegration;
import com.ntd.challenge.operations.v1.services.RandomStringGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomStringGeneratorServiceImpl implements RandomStringGeneratorService {

    private final RandomOrgIntegration randomStringIntegration;

    @Override
    public synchronized String generateRandomString(Integer length) {
        if (length < 1 || length > 32) {
            throw new InvalidRandomStringLengthException();
        }

        return randomStringIntegration.getRandomString(length);
    }
}
