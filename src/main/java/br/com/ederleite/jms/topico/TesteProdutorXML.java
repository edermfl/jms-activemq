package br.com.ederleite.jms.topico;

import br.com.ederleite.jms.model.Pedido;
import br.com.ederleite.jms.model.PedidoFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import java.io.StringWriter;
import java.util.Scanner;

public class TesteProdutorXML {
    public static void main(String[] args) throws Exception {
	InitialContext context = new InitialContext();
	ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");

	Connection connection = connectionFactory.createConnection();
	connection.start();

	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	final Destination topic = (Destination) context.lookup("loja");

	MessageProducer producer = session.createProducer(topic);
	for (int i = 0; i < 10; i++) {
	    Pedido pedido = new PedidoFactory().geraPedidoComValores();
	    pedido.setCodigo(i);

	    StringWriter writer = new StringWriter();
	    JAXB.marshal(pedido, writer);
	    String xml = writer.toString();

	    TextMessage textMessage = session.createTextMessage(xml);
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
