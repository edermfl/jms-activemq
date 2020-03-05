package br.com.ederleite.jms.topico;

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

	final Destination topic = (Destination) context.lookup("loja");

	MessageProducer producer = session.createProducer(topic);
	for (int i = 0; i < 10; i++) {
	    TextMessage textMessage = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
	    textMessage.setBooleanProperty("ebook", i % 2 == 0);
	    producer.send(textMessage);
	}
	System.out.println("pronto!");

	new Scanner(System.in).next();

	session.close();
	connection.close();
	context.close();
    }
}
