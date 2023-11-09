package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbCarDao implements Car.CarDao {

    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS CAR(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "COMPANY_ID INT NOT NULL," +
            "CONSTRAINT FK_ID FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)," +
            "NAME VARCHAR(50) UNIQUE NOT NULL );";
    private static final String SELECT_ALL = "SELECT * FROM CAR";
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private Connection connection;

    public DbCarDao() {
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
    public void add(String car, int companyId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?)")) {
            preparedStatement.setString(1, car);
            preparedStatement.setInt(2, companyId);
            preparedStatement.executeUpdate();
            System.out.println("The car was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> findAll(Company company) {
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CAR WHERE COMPANY_ID = ?")) {
            statement.setInt(1, company.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int companyId = resultSet.getInt("COMPANY_ID");
                cars.add(new Car(name, companyId, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
