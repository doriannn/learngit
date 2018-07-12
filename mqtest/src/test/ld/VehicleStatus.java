package test.ld;

import org.json.JSONObject;

public  class VehicleStatus {

    public JSONObject frame1;
    public JSONObject frame2;
    public JSONObject frame3;
    public JSONObject frame4;

    VehicleStatus() {
        super();
        frame1 = new JSONObject();
        frame2 = new JSONObject();
        frame3 = new JSONObject();
        frame4 = new JSONObject();
    }

    void setFrame1(String vehicleID, String date, String lon, String lat) {

        frame1.put("vehicle_no", vehicleID  );  //1234567812345678
        frame1.put("time_stamp",date);
        frame1.put("longitude", lon);
        frame1.put("latitude", lat);

        frame1.put("course_angle", "19");
        frame1.put("turret_angle", "0");
        frame1.put("gun_angle", "30");

        frame1.put("vehicle_speed", "20");
        frame1.put("engine_speed", "1000");

    }

    public void setFrame2(String vehicleID, String breakdown) {

        frame2.put("vehicle_no", vehicleID  );  //1234567812345678
        frame2.put("engine_selfcheck" ,breakdown);
        frame2.put("outfire_system","0");
    //3435973836
    }

    public void setFrame3(String vehicleID) {

        frame3.put("vehicle_no", vehicleID  );  //1234567812345678
        frame3.put("center_charging", "3435973836");
        frame3.put("gun_control_system" ,"3435973836");
        frame3.put("fire_control_system","3435973836");
        //3435973836
    }
    public void setFrame4(String vehicleID, String breakdown) {

        frame4.put("vehicle_no", vehicleID  );  //1234567812345678
        frame4.put("fuel_tank_middle" ,"80");
        frame4.put("fuel_tank_rear","60");

        frame4.put("smoke_shell",breakdown);
        frame4.put("oxygengenerator","0");

    }
    public String Frame1ToString() {
        return frame1.toString();
    }
    public String Frame2ToString() {
        return frame2.toString();
    }
    public String Frame3ToString() {
        return frame3.toString();
    }
    public String Frame4ToString() {
        return frame4.toString();
    }
}
