package ee.nutikas.games.redis;

import ee.nutikas.games.engine.memorycards.dto.MemoryCardGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, MemoryCardGame> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, MemoryCardGame> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Configure the key and value serializers
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
