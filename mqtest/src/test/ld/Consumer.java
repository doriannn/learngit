package test.ld;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public ConnectionFactory connectionFactory;
    public Connection connection;
    public Session session;
    public Destination destination;
    public MessageConsumer messageConsumer;
    public TextMessage textMessage;
    public ObjectMessage objectMessage;

    public void InitConsumer(){

        connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,"tcp://24.141.200.34:61616");
        try {
            connection  = connectionFactory.createConnection();

            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            destination = session.createTopic("real_time_vehicle_status_frame1");
            messageConsumer = session.createConsumer(destination);
            System.out.println("start connect");
            connection.start();
            System.out.println(" connect success");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public void ReceiveMessage() {

//        try {
//            messageConsumer.setMessageListener(new MessageListener() {
//                public void onMessage(Message message) {
//                    System.out.println((TextMessage)message);
//                }
//            });
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }

        while(Boolean.TRUE) {

            try {
                textMessage = (TextMessage) messageConsumer.receive();
                System.out.println(textMessage.getText());
                Thread.sleep(100);
            } catch (JMSException e) {
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    public String ReceiveJSONObject() {

        try {
            textMessage = session.createTextMessage();
            System.out.println("try receive...");
            messageConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage.toString();
    }

    public  int closeConsumer() {
        if(connection != null)
        {
            try{
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();

            }
        }
        else
        {
            return 1;
        }
        return 0;
    }

}
