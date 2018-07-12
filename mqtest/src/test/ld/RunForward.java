package test.ld;

/**
 * Hello world!
 *
 */
public class RunForward
{
    static Producer frame1;
    static Producer frame4;


    public static void main( String[] args )  {

        int MaxVehicle = 36;
        String[] vehicleId = new String[MaxVehicle];

//        frame1 = new Producer("tcp://172.10.31.181:61616");
//        frame1 = new Producer("tcp://21.156.193.159:61616");
        frame1 = new Producer("tcp://24.141.200.34:61616");
        frame1.CreateTopic("real_time_vehicle_status_frame1");

//        frame4 = new Producer("tcp://172.10.31.181:61616");
//        frame4 = new Producer("tcp://21.156.193.159:61616");
        frame4 = new Producer("tcp://24.141.200.34:61616");
        frame4.CreateTopic("real_time_vehicle_status_frame4");


        //仿真车型和编号
        for (int i = 0; i < MaxVehicle; i++) {
            vehicleId[i] = String.format("%08d",i % 7 + 1) + String.format("%08d",i + 1);
            System.out.println(vehicleId[i]);
        }


        //仿真初始位置
        Point[][][] points = new Point[3][3][4];

        for(int k = 0; k < 3; k++) {
            for (int j= 0; j < 3; j++) {
                for (int i = 0; i < 4; i++) {
                    points[k][j][i] = new Point();
                }
            }
        }

        //HCY:1L1P
        points[0][0][0].setPoint(118.028363, 32.531684);
        points[0][0][1].setPoint(118.030030, 32.530388);
        points[0][0][2].setPoint(118.031542, 32.529133);
        points[0][0][3].setPoint(118.026902, 32.527919);

        points[0][1][0].setPoint(118.042978, 32.519539);

        points[0][2][0].setPoint(118.056652, 32.508387);

        System.out.println("1l1p-----------------------");
        for (int i = 0; i < 4; i++) {
            System.out.println(points[0][0][i].getPoint());
        }

        //1L2P
        Point vector_1l2p = new Point();
        vector_1l2p = vector_1l2p.Vector(points[0][0][0], points[0][1][0]);

        System.out.println("vector_1l2p:"+vector_1l2p.getPoint());

        System.out.println("1l2p-----------------------");
        for(int i = 1; i < 4; i++) {
//      points[0][1][i] = new Point();
            points[0][1][i] = points[0][1][i].Add(points[0][0][i],vector_1l2p);

            System.out.println(points[0][1][i].getPoint());
        }

        //1L3P
        Point  vector_1l3p = new Point();
        vector_1l3p = vector_1l3p.Vector(points[0][0][0], points[0][2][0]);

        System.out.println("1l3p-----------------------");
        for(int i = 1; i < 4; i++) {
            //  points[0][2][i] = new Point();
            points[0][2][i] = points[0][2][i].Add(points[0][0][i],vector_1l3p);

            System.out.println(points[0][2][i].getPoint());
        }

        //HCY:2L1P
        points[1][0][0].setPoint(118.162325, 32.476919);
        points[1][0][1].setPoint(118.164414, 32.475886);
        points[1][0][2].setPoint(118.166541, 32.474779);
        points[1][0][3].setPoint(118.162248, 32.472092);

        points[1][1][0].setPoint(118.171805, 32.472003);

        points[1][2][0].setPoint(118.182166, 32.466830);

        //HCY:2L2P

        Point vector_2l2p = new Point();
        vector_2l2p = vector_1l2p.Vector(points[1][0][0], points[1][1][0]);

        System.out.println("vector_2l2p:"+vector_2l2p.getPoint());

        System.out.println("2l2p-----------------------");
        for(int i = 1; i < 4; i++) {
            // points[1][1][i] = new Point();
            points[1][1][i] = points[1][1][i].Add(points[1][0][i],vector_2l2p);

            System.out.println(points[1][1][i].getPoint());
        }


        //2L3P
        Point  vector_2l3p = new Point();
        vector_2l3p = vector_2l3p.Vector(points[1][0][0], points[1][2][0]);
        System.out.println("vector_2l3p:"+vector_2l3p.getPoint());
        System.out.println("2l3p-----------------------");
        for(int i = 1; i < 4; i++) {
//      points[1][2][i] = new Point();
            points[1][2][i] = points[1][2][i].Add(points[1][0][i],vector_2l3p);

            System.out.println(points[1][2][i].getPoint());
        }

        //3L
        points[2][0][0].setPoint(118.129533, 32.444484);
        Point vector_3l = new Point();
        vector_3l = vector_3l.Vector(points[1][0][0], points[2][0][0]);

        System.out.println("3l-----------------------");

        for(int k = 0; k < 3; k++) {
            for(int i = 0; i < 4; i++) {
                points[2][k][i] = points[2][k][i].Add(points[1][k][i], vector_3l);
                System.out.println(points[2][k][i].getPoint());
            }
        }


        //仿真前进方向向量

        Point forward_1l = new Point();
        forward_1l =  forward_1l.Vector(points[0][1][1],  new Point(118.257411,32.641193));
        Point forward_23l = new Point();
        forward_23l =  forward_23l.Vector(points[1][1][1],  new Point(118.261074,32.544208));

        forward_1l.setX(forward_1l.getX()/(500*1000));
        forward_1l.setY(forward_1l.getY()/(500*1000));
        forward_23l.setX(forward_23l.getX()/(500*1000));
        forward_23l.setY(forward_23l.getY()/(500*1000));
        System.out.println(forward_1l.getPoint());
        System.out.println(forward_23l.getPoint());

        //仿真位置发送
        while(Boolean.TRUE){

            try {
                int v = 0;
                for(int k=0; k < 3; k++) {
                    for (int j = 0; j < 3; j++) {
                        for (int i = 0; i < 4; i++,v++) {

                            frame1.SendFrame1(vehicleId[v], String.valueOf(points[k][j][i].getX()),Double.toString(points[k][j][i].getY()));
                            frame4.SendFrame4(vehicleId[v],"0");
                            System.out.println(points[k][j][i].getPoint());
                        }
                    }
                }
                Thread.sleep(1*1000);

                for(int k = 0,j= 0,i = 0; k < 3; k++) {
                    for (j= 0; j < 3; j++) {
                        for (i = 0; i < 4; i++) {
                            if(k == 0) {
                                points[k][j][i].Add(forward_1l);
                            } else {
                                points[k][j][i].Add(forward_23l);
                            }
                        }
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
}
