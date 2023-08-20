package ee.nutikas.games.rabbitmq.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.nutikas.games.api.game.memorycards.CreateGameRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

	@Value("${rabbitmq.exchange.name}")
	private String exchange;

	@Value("${rabbitmq.routing.key.name}")
	private String routingKey;

	private final ObjectMapper objectMapper;

	private final RabbitTemplate template;

	public void createGame(CreateGameRequest request) {
		try {
			var json = objectMapper.writeValueAsString(request);
			log.info("JSON output: {}", json);
			template.convertAndSend(exchange, routingKey, json);
			log.info("Sent message");
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "GAME_CREATION_FAILED");
		}
	}
}
