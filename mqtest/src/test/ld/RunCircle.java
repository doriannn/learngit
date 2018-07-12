package test.ld;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class RunCircle
{
    static Producer frame1;
    static Producer frame4;


    public static void main( String[] args )  {

        double[] Lon;
        double[] Lat;

        frame1 = new Producer("tcp://21.156.193.159:61616");
        frame1.CreateTopic("real_time_vehicle_status_frame1");

        frame4 = new Producer("tcp://21.156.193.159:61616");
        frame4.CreateTopic("real_time_vehicle_status_frame4");

        Lon = new double[360];
        Lat = new double[360];
        double r = 0.01;
        int MaxVehicle = 7;

        Random random = new Random();
        String[] vehicleId = new String[MaxVehicle];
        Double[] Lon0 = new Double[MaxVehicle];
        Double[] Lat0 = new Double[MaxVehicle];


        for (int i = 0; i < MaxVehicle; i++) {
            vehicleId[i] = String.format("%08d",i % 7 + 1) + String.format("%08d",i + 1);
            System.out.println(vehicleId[i]);
        }

        for (int i = 0; i < MaxVehicle ;i++) {

            Lon0[i] = 118.110289 + (random.nextFloat() * 0.3 / 3);
            Lat0[i] = 32.40475 + (random.nextFloat() * 0.3 / 3);
            System.out.println(Lon0[i]+","+Lat0[i]);

        }

        while(Boolean.TRUE){

            try {

                for(int i = 0; i< 360; i++){
                    for (int j = 0; j < MaxVehicle; j++) {
                        Lon[i] = Lon0[j] + r * Math.cos(i * 3.14 / 180);
                        Lat[i] = Lat0[j] + r * Math.sin(i * 3.14 / 180);
                        frame1.SendFrame1(vehicleId[j], String.valueOf(Lon[i]),Double.toString(Lat[i]));
                        frame4.SendFrame4(vehicleId[j], "0");
                    }
                    Thread.sleep(1*1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
           // producer.closeProducer();


    }
}
