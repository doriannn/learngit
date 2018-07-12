package test.ld;

public class RunXingLi {

    static Producer frame1;
    static Producer frame2;
    static Producer frame3;
    static Producer frame4;

    public static void main( String[] args ) {

        String[] vehicleId = new String[5];

//        frame1 = new Producer("tcp://172.10.31.181:61616");
//        frame1 = new Producer("tcp://21.156.193.159:61616");
        frame1 = new Producer("tcp://24.141.200.34:61616");
        frame1.CreateTopic("real_time_vehicle_status_frame1");

//        frame4 = new Producer("tcp://172.10.31.181:61616");
//        frame4 = new Producer("tcp://21.156.193.159:61616");
        frame2 = new Producer("tcp://24.141.200.34:61616");
        frame2.CreateTopic("real_time_vehicle_status_frame2");

        frame3 = new Producer("tcp://24.141.200.34:61616");
        frame3.CreateTopic("real_time_vehicle_status_frame3");

        frame4 = new Producer("tcp://24.141.200.34:61616");
        frame4.CreateTopic("real_time_vehicle_status_frame4");


        vehicleId[0] = "0000000100000067";
        vehicleId[1] = "0000000100000087";
        vehicleId[2] = "0000000100000099";
        vehicleId[3] = "0000000100000104";
        vehicleId[4] = "0000000900000913";

        Point[] points = new Point[5];

        for(int i = 0; i < 5; i++) {
            points[i] = new Point();
            System.out.println(points[i].toString());
        }
        points[0].setPoint(118.104347, 32.409816);
        points[1].setPoint(118.104002, 32.407919);
        points[2].setPoint(118.104556, 32.411022);
        points[3].setPoint(118.109906, 32.408809);
        points[4].setPoint(118.110816, 32.405407);



        Point forward_1l = new Point();
        forward_1l =  forward_1l.Vector(points[1], points[0]);

        forward_1l.setX(forward_1l.getX()/(10*10000));
        forward_1l.setY(forward_1l.getY()/(10*10000));


        //仿真位置发送
        while(Boolean.TRUE){

            try {

                    for (int i = 0; i < 5; i++) {
                        frame1.SendFrame1(vehicleId[i], String.valueOf(points[i].getX()),Double.toString(points[i].getY()));
                        frame2.SendFrame2(vehicleId[i], "0");
                        frame3.SendFrame3(vehicleId[i]);
                        frame4.SendFrame4(vehicleId[i], "0");

                        points[i].Add(forward_1l);
                        System.out.println(points[i].getPoint());
                    }

                Thread.sleep(1*1000);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
