package com.electron.mfs.pg.pg8583client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Pg 8583 Client.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
