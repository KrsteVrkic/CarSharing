package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCustomerDao implements Customer.CustomerDao {

    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(50) UNIQUE NOT NULL," +
            "RENTED_CAR_ID INT DEFAULT NULL," +
            "CONSTRAINT FK_RENTID FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
            " );";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private Connection connection;

    public DbCustomerDao() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            Statement s = connection.createStatement();
            s.execute(CREATE_DB);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(String customer) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMER (NAME) VALUES (?)")) {
            ps.setString(1, customer);
            ps.executeUpdate();
            System.out.println("The customer was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMER")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int rentedCarId = resultSet.getInt("RENTED_CAR_ID");
                String name = resultSet.getString("NAME");
                customers.add(new Customer(id, rentedCarId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public String dudeWheresMyCar(int rentedCarId) {

        try (PreparedStatement ps = connection.prepareStatement("SELECT (NAME) FROM CAR WHERE (ID) = (?)")) {
            ps.setInt(1, rentedCarId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "bruh";
    }

    public String findMyCompany(int rentedCarId) {

        try (PreparedStatement ps = connection.prepareStatement("SELECT NAME FROM COMPANY WHERE ID = (?)")) {
            ps.setInt(1, rentedCarId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "bruh";
    }

    public void returnMyCar(int customerId) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = (?)")) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
            System.out.println("You've returned a rented car!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rentACar(int customerId, int rentedCarId) {

        try (PreparedStatement ps = connection.prepareStatement("UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?")) {
            ps.setInt(1, rentedCarId);
            ps.setInt(2, customerId);
            ps.executeUpdate();
            System.out.println("Rented a car!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean didIRentACar(int customerId) {
        String sqlQuery = "SELECT RENTED_CAR_ID IS NULL AS DID_I_RENT_A_CAR FROM CUSTOMER WHERE ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, customerId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("DID_I_RENT_A_CAR");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}