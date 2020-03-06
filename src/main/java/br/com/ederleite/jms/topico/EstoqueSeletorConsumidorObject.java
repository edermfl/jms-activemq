package br.com.ederleite.jms.topico;

import br.com.ederleite.jms.model.Pedido;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class EstoqueSeletorConsumidorObject {
    public static void main(String[] args) throws Exception {
	System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
	InitialContext context = new InitialContext();
	ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

	Connection connection = connectionFactory.createConnection();
	connection.setClientID("estoque");

	connection.start();
	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	final Topic topico = (Topic) context.lookup("loja");
	MessageConsumer consumer = session
			.createDurableSubscriber(topico, "assinatura-selector-obj", "ebook is null OR ebook=false", false);

	consumer.setMessageListener(message -> {
	    try {
		Pedido pedido = (Pedido) ((ObjectMessage) message).getObject();
		System.out.println("Msg recebida: " + pedido.getCodigo());
	    } catch (JMSException pE) {
		pE.printStackTrace();
	    }
	});

	new Scanner(System.in).next();

	session.close();
	connection.close();
	context.close();
    }
}
