package com.edvantis.training.parking.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by taras.fihurnyak on 3/31/2017.
 */
public class AppProperty {

    public Properties getApplicationProperties() {
        InputStream inputStream = null;
        Properties prop = new Properties();
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
