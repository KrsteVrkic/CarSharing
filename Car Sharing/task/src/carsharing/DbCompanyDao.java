package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCompanyDao implements Company.CompanyDao {

    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS COMPANY(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(50) UNIQUE NOT NULL );";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT_COMPANY = "SELECT ID FROM COMPANY WHERE NAME = ?";
    private static final String INSERT_DATA = "INSERT INTO COMPANY (NAME) VALUES ('%s')";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private Connection connection;

    public DbCompanyDao() {
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
    public void add(Company company) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(INSERT_DATA, company.getName()));
            System.out.println("The company was created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public int findCompanyId(String companyName) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_COMPANY)) {
            statement.setString(1, companyName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // company not found
    }
}
