package com.skysean.mybatis.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

public class StringMapperLocationResolver implements MapperLocationResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(StringMapperLocationResolver.class);

  @Override
  public Resource[] getMapperLocations(String locations) {
    String[] mappingLocations = StringUtils.tokenizeToStringArray(locations, ",; \t\n");
    Resource[] mappingResources = null;
    for (String mappingLocation : mappingLocations) {
      Resource[] resources = null;
      try {
        resources = new PathMatchingResourcePatternResolver().getResources(mappingLocation);
      } catch (Exception e) {
        LOGGER.warn("获取mapper xml资源失败", e);
      }

      if (null == resources || resources.length == 0) {
        continue;
      }

      if (null == mappingResources) {
        mappingResources = resources;
        continue;
      }

      Resource[] temp = mappingResources;
      mappingResources = new Resource[temp.length + resources.length];
      System.arraycopy(temp, 0, mappingResources, 0, temp.length);
      System.arraycopy(resources, 0, mappingResources, temp.length, resources.length);
    }

    return mappingResources;
  }

}
