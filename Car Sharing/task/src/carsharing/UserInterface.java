package carsharing;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);
    Company.CompanyDao companyDao;
    Car.CarDao carDao;
    Customer.CustomerDao customerDao;

    public UserInterface(Company.CompanyDao companyDao, Car.CarDao carDao, Customer.CustomerDao customerDao) {

        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    public void menu() {

        while (true) {

            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            switch (userInput) {
                case 1:
                    managerMenu();
                    break;
                case 2:
                    listCustomers();
                    break;
                case 3:
                    createCustomer();
                    break;
                case 0:
                    System.out.println("Exit program");
                    return;
                default:
                    System.out.println("WRONG");
            }
        }
    }

    public void managerMenu() {

        while (true) {

            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            switch (userInput) {
                case 1:
                    listCompanies();
                    break;
                case 2:
                    createCompany();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("WRONG");
            }
        }
    }

    public void customerMenu(Customer customer) {

        while (true) {

            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            switch (userInput) {
                case 1:
                    rentCar(customer);
                    break;
                case 2:
                    returnCar(customer);
                    break;
                case 3:
                    myCar(customer);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("WRONG");
            }
        }
    }

    private void myCar(Customer customer) {

        int rentedCarId = customer.getRentedCarId();

        if (customerDao.dudeWheresMyCar(rentedCarId).equals("bruh")) {
            System.out.println("You didn't rent a car!");
            return;
        }

        String car = customerDao.dudeWheresMyCar(rentedCarId);
        String company = customerDao.findMyCompany(rentedCarId);

        System.out.println("Your rented car:");
        System.out.println(car);
        System.out.println("Company:");
        System.out.println(company);

    }

    private void returnCar(Customer customer) {

        int rentedCarId = customer.getRentedCarId();
        int customerId = customer.getId();

        if (customer.returnedCar) {
            System.out.println("You've returned a rented car!");
            return;
        }

        if (customerDao.dudeWheresMyCar(rentedCarId).equals("bruh")) {
            System.out.println("You didn't rent a car!");
        } else {
            customerDao.returnMyCar(customerId);
            customer.setReturnedCar(true);
        }
    }

    private void rentCar(Customer customer) {

        List<Company> companies = companyDao.findAll();

        if (!customerDao.didIRentACar(customer.getId())) {
            System.out.println("You've already rented a car!");
            return;
        }

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose a company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println((i + 1) + ". " + companies.get(i).getName());
            }
            System.out.println("0. Back");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            if (userInput == 0) {
                return;
            } else {
                Company selectedCompany = companies.get(userInput - 1);
                rentMenu(selectedCompany, customer);
            }
        }
    }

    public void createCustomer() {

        System.out.println("Enter the customer name:");
        String userInput = scanner.nextLine();
        customerDao.add(userInput);
    }

    public void listCustomers() {

        List<Customer> customers = customerDao.findAll();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println("Customer list:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i + 1) + ". " + customers.get(i).getName());
            }
            System.out.println("0. Back");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            if (userInput == 0) {
                return;
            } else {
                Customer selectedCustomer = customers.get(userInput - 1);
                customerMenu(selectedCustomer);
            }
        }
    }

    public void listCompanies() {

        List<Company> companies = companyDao.findAll();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose a company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println((i + 1) + ". " + companies.get(i).getName());
            }
            System.out.println("0. Back");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            if (userInput == 0) {
                return;
            } else {
                Company selectedCompany = companies.get(userInput - 1);
                companyMenu(selectedCompany);
            }
        }
    }

    public void rentMenu(Company company, Customer customer) {

        while (true) {

            List<Car> cars = carDao.findAll(company);
            if (cars.isEmpty()) {
                System.out.println("The car list is empty!");
            } else {
                System.out.println("Choose a car:");
                for (int i = 0; i < cars.size(); i++) {
                    System.out.println((i + 1) + ". " + cars.get(i).getName());
                }
                System.out.println("0. Back");

                int userInput = scanner.nextInt();
                scanner.nextLine();

                if (userInput == 0) {
                    return;
                }

                int customerId = customer.getId();
                int rentedCarId = cars.get(userInput - 1).getId();
                customerDao.rentACar(customerId, rentedCarId);

                customer = customerDao.findAll().stream()
                        .filter(c -> c.getId() == customerId)
                        .findFirst()
                        .orElse(null);


                String carName = cars.get(userInput - 1).getName();
                System.out.printf("You rented '%s'\n", carName);
                customer.setReturnedCar(false);
                customerMenu(customer);

            }
        }
    }

    public void companyMenu(Company company) {

        while (true) {

            System.out.println(company.getName() + " company:");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            switch (userInput) {
                case 0:
                    return;
                case 1:
                    listCars(company);
                    break;
                case 2:
                    createCar(company.getName());
                    break;
                default:
                    System.out.println("WRONG");
            }
        }
    }

    public void listCars(Company company) {

        List<Car> cars = carDao.findAll(company);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Cars list:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println((i + 1) + ". " + cars.get(i).getName());
            }
        }
    }

    public void createCar(String company) {

        System.out.print("Enter the car name: ");
        String carName = scanner.nextLine();
        int companyId = companyDao.findCompanyId(company);
        carDao.add(carName, companyId);
    }

    public void createCompany() {

        List<Company> companies = companyDao.findAll();
        int id = companies.size();
        System.out.print("Enter the company name: ");
        String companyName = scanner.nextLine();
        Company newCompany = new Company(id, companyName);
        companyDao.add(newCompany);
    }
}
