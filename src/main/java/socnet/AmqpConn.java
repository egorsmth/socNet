package socnet;

import com.rabbitmq.client.*;
import socnet.chat.enteties.Message;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class AmqpConn {
    Connection connection;

    @PostConstruct
    void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public boolean send(String queueName, Message message) {
        return true;
    }

    public boolean listen(String queueName, Consumer consumer) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueBind()
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicConsume(queueName, true, consumer);
        return true;
    }
}
