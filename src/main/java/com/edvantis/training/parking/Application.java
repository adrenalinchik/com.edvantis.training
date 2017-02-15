package com.edvantis.training.parking;

import com.edvantis.training.parking.jdbc.ParkingJdbcServiceImpl;

/**
 * Created by taras.fihurnyak on 2/9/2017.
 */
public class Application {
    public static void main(String[] args) {

        ParkingJdbcServiceImpl jdbcService = new ParkingJdbcServiceImpl();
        //jdbcService.createDb("test","root","root");

        if (args.length > 0) {
            jdbcService.createDb(args[0],args[1],args[2]);

        }
    }

}
