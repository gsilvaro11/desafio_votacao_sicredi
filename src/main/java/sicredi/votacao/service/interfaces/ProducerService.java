package sicredi.votacao.service.interfaces;

public interface ProducerService {
    <T> void send(String topic, Object menssage);
}
