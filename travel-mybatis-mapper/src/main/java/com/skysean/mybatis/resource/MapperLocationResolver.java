package com.skysean.mybatis.resource;

import org.springframework.core.io.Resource;

public interface MapperLocationResolver {
	Resource[] getMapperLocations(String locations);
}
