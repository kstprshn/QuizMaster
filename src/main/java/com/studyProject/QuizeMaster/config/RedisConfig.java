package com.studyProject.QuizeMaster.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    String host;
    @Value("${spring.data.redis.port}")
    Integer port;

    @Bean                                            //конфиг jedis клиента, для работы с redis
    public JedisConnectionFactory jedisConnectionFactory(){
        var redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setConnectionFactory(jedisConnectionFactory());

        return template;
    }

    @Bean(name = "quizCacheManager")                 //бин для управления жизненным циклом кэша
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(7))
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));

        return RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(config)
                .build();
    }
}
