package com.sdi.monitoring.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonBean {
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
