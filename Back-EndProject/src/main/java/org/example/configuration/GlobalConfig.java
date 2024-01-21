package org.example.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

@Slf4j
@Data
public class GlobalConfig {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ResourceLoader resourceLoader;

    @Getter
    private  String uploadFilePath;
    @Getter
    private String uploadResourcePath;
    @Getter
    private String schedulerCronExample1;

    @Getter
    private boolean local;

    @Getter
    private boolean dev;

    @Getter
    private boolean prod;

    @PostConstruct
    public void init(){
        logger.info("init");
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        String activeProfile =  "local";

        if(ObjectUtils.isNotEmpty(activeProfiles)){
            activeProfile = activeProfiles[0];
        }

        String resourcePath = String.format("classpath:globals/global-%s.properties",activeProfile);

        try{
            Resource resource = resourceLoader.getResource(resourcePath);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            this.uploadFilePath = properties.getProperty("uploadFile.path");
            this.uploadResourcePath = properties.getProperty("uploadFile.resourcePath");
            this.schedulerCronExample1 = properties.getProperty("scheduler.cron.example1");
            this.local = activeProfile.equals("local");
            this.dev = activeProfile.equals("dev");
            this.prod = activeProfile.equals("prod");
        }catch (Exception e){
            logger.error("e",e);
        }
    }

}