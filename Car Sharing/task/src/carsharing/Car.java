package carsharing;

import java.util.List;

public class Car {
    String name;
    int companyId;
    int id;

    public Car(String name, int companyId, int id) {
        this.name = name;
        this.companyId = companyId;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public interface CarDao {
        List<Car> findAll(Company company);
        void add(String carName, int companyId);
    }
}
