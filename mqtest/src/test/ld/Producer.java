package test.ld;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Producer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public Connection connection;
    public Session session;
    public MessageProducer messageProducer;
    public TextMessage textMessage;
    public TextMessage jsonMessage;

    public VehicleStatus vehicleStatus;

    public Producer(String hostUrl) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, hostUrl);
        vehicleStatus = new VehicleStatus();
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            jsonMessage = session.createTextMessage();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void CreateTopic(String topic)  {
        try{
            //创建第topic
            Destination destination = session.createTopic(topic);
            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        } catch (JMSException e) {
            e.printStackTrace();
            logger.error("createTopic error");
        }

    }

    public int SendMsg( String msg) {
        try {
            textMessage = session.createTextMessage();
            textMessage.setText(msg);
            messageProducer.send(textMessage);
            logger.info("send---");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println( "send-println" );

        return 0;
    }

   // {rnssLongitude:118.32,rnssLatitude:36.56, pitchingAngle:95, inclinationAngle:30, armourPiercingProjectile:50, grenade :30, speed :20}”

    public void SendFrame1(String vehicleID, String lon, String lat) {
        //lon=118.32, lat=36.56
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        vehicleStatus.setFrame1(vehicleID, date, lon, lat);

        try {

            jsonMessage.setText(vehicleStatus.Frame1ToString());

            messageProducer.send(jsonMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println(vehicleStatus.Frame1ToString());

}
    public void SendFrame2(String vehicleID, String breakdown) {
        vehicleStatus.setFrame2(vehicleID, breakdown);
        try {

            jsonMessage.setText(vehicleStatus.Frame2ToString());

            messageProducer.send(jsonMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println(vehicleStatus.Frame2ToString());
    }

    public void SendFrame3(String vehicleID) {
        vehicleStatus.setFrame3(vehicleID);
        try {

            jsonMessage.setText(vehicleStatus.Frame3ToString());

            messageProducer.send(jsonMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println(vehicleStatus.Frame2ToString());
    }
    public void SendFrame4(String vehicleID, String breakdown) {
        vehicleStatus.setFrame4(vehicleID, breakdown);
        try {

            jsonMessage.setText(vehicleStatus.Frame4ToString());

            messageProducer.send(jsonMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println(vehicleStatus.Frame4ToString());
    }

    public  int closeProducer() {
        if(connection != null)
        {
            try{
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
                logger.error("close connection error");
            }
        }
        else
        {
            return 1;
        }
        return 0;
    }
}
