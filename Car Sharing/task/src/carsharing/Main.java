package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        DbCompanyDao companyDao = new DbCompanyDao();
        UserInterface userInterface = new UserInterface(companyDao);
        userInterface.menu();

    }
}