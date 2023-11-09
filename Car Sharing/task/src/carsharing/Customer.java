package carsharing;

import java.util.List;

public class Customer {

    int id;
    int rentedCarId;
    String name;

    boolean returnedCar = false;

    public Customer(int id, int rentedCarId, String name) {
        this.id = id;
        this.name = name;
        this. rentedCarId = rentedCarId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public boolean hasReturnedCar() {
        return returnedCar;
    }

    public void setReturnedCar(boolean returnedCar) {
        this.returnedCar = returnedCar;
    }

    public interface CustomerDao {
        void add(String customer);
        List<Customer> findAll();
        String dudeWheresMyCar(int customerId);
        String findMyCompany(int customerId);
        void returnMyCar(int customerId);
        void rentACar(int customerId, int rentedCarId);
        boolean didIRentACar(int customerId);

    }
}
