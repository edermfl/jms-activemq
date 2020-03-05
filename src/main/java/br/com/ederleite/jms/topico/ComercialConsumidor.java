package br.com.ederleite.jms.topico;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class ComercialConsumidor {
    public static void main(String[] args) throws Exception {
	InitialContext context = new InitialContext();
	ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

	Connection connection = connectionFactory.createConnection();
	connection.setClientID("comercial");

	connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	final Topic topico = (Topic) context.lookup("loja");
	MessageConsumer consumer = session.createDurableSubscriber(topico,"assinatura");
	
	consumer.setMessageListener(message -> {
	    try {
		System.out.println("Msg recebida: " + ((TextMessage) message).getText());
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
