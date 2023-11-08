package carsharing;

import java.util.List;

public class Car {
    String name;
    int companyId;

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public interface CarDao {
        List<Car> findAll(Company company);
        void add(Car car, int companyId);
    }
}
