package fr.android.project_vyas_manaranche.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseService {

    Connection connection;

    public DatabaseService()  throws Exception {
        connection =  DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/efrei_fight", "root", "");
    }

}
