package test.ld;

public class consumerRun {
    static Consumer consumer;

    public static void main(String[] args) {

        consumer = new Consumer();
        consumer.InitConsumer();
     //   consumer.ReceiveMessage();
        consumer.ReceiveJSONObject();
        //        consumer.closeConsumer();
    }
}
