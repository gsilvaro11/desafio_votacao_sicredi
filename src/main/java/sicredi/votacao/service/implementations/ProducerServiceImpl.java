package sicredi.votacao.service.implementations;

import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import sicredi.votacao.service.interfaces.ProducerService;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void send(String topic, Object message) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(message));

        } catch (JsonProcessingException e) {
            throw new ValidateException(e.getMessage());
        }
    }

}
