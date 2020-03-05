package br.com.ederleite.jms.fila;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteProdutor {
    public static void main(String[] args) throws Exception {
	InitialContext context = new InitialContext();
	ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

	Connection connection = connectionFactory.createConnection();
	connection.start();

	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	final Destination fila = (Destination) context.lookup("financeiro");

	MessageProducer producer = session.createProducer(fila);
	for (int i = 0; i < 1000; i++) {
	    TextMessage textMessage = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
	    producer.send(textMessage);
	}
	System.out.println("pronto!");

	new Scanner(System.in).next();

	session.close();
	connection.close();
	context.close();
    }
}
