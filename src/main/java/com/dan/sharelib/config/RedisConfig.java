package com.dan.sharelib.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Slf4j
@Configuration
@Setter
@Getter
public class RedisConfig {

    @Value("${redis.host:localhost}")
    private String host;

    @Value("${redis.password:}")
    private String password;

    @Value("${redis.port:6379}")
    private Integer port;

    @Value("${redis.dbIndex:0}")
    private Integer dbIndex;

    @Value("${redis.maxIdle:50}")
    private Integer maxIdle;

    @Value("${redis.minIdle:50}")
    private Integer minIdle;

    @Value("${redis.maxTotal:50}")
    private Integer maxTotal;

    @Value("${redis.timeout:5}")
    private Long timeout;

    @Value("${redis.maxWait:10}")
    private Long maxWait;

    @Value("${redis.shutdown:100}")
    private Long shutdown;

    @Bean(name="redisClientFactory")
    public LettuceConnectionFactory getRedisClientFactory(){
        log.info("redisClientFactory is created...");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(this.getHost());
        redisStandaloneConfiguration.setPort(this.getPort());
        redisStandaloneConfiguration.setPassword(this.getPassword());
        redisStandaloneConfiguration.setDatabase(ObjectUtils.isEmpty(this.dbIndex) ? 0 : this.dbIndex);
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(ObjectUtils.isEmpty(this.maxIdle) ? 10 : this.maxIdle);
        poolConfig.setMinIdle(ObjectUtils.isEmpty(this.minIdle) ? 1 : this.minIdle);
        poolConfig.setMaxTotal(ObjectUtils.isEmpty(this.maxTotal) ? 10 : this.maxTotal);
        poolConfig.setMaxWait(ObjectUtils.isEmpty(this.maxWait) ? Duration.ofMillis(5000) : Duration.ofMillis(this.maxWait));
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(ObjectUtils.isEmpty(this.timeout) ? Duration.ofSeconds(10) : Duration.ofSeconds(this.timeout))
                .shutdownTimeout(ObjectUtils.isEmpty(this.shutdown) ? Duration.ZERO : Duration.ofSeconds(this.shutdown))
                .poolConfig(poolConfig)
                .build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
        lettuceConnectionFactory.setShareNativeConnection(false);
        return lettuceConnectionFactory;
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        log.info("redisTemplate is created...");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(this.getRedisClientFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setEnableDefaultSerializer(true);
        return redisTemplate;
    }

}
