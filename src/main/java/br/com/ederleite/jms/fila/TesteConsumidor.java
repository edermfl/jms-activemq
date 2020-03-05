package br.com.ederleite.jms.fila;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteConsumidor {
    public static void main(String[] args) throws Exception {
	InitialContext context = new InitialContext();
	ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

	Connection connection = connectionFactory.createConnection();
	connection.start();

	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	final Destination fila = (Destination) context.lookup("financeiro");
	MessageConsumer consumer = session.createConsumer(fila);
	
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
