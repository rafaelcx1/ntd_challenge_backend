package com.ntd.challenge.auth.v1.utils;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public final class MapperUtils {

    public static ModelMapper mapperInstance() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
