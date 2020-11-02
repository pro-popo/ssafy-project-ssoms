package com.sdi.monitoring.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

private ModelMapper modelMapper;
    
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper=modelMapper;
    }

	public <D, E> D convertToDTO(E from, Class<? extends D> to) {
        return modelMapper.map(from, to);
    }
}
