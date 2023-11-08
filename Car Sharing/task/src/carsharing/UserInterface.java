package carsharing;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner = new Scanner(System.in);
    Company.CompanyDao companyDao;
    Car.CarDao carDao;

    public UserInterface(Company.CompanyDao companyDao, Car.CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    public void menu() {

        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");

            int userInput = scanner.nextInt();
            scanner.nextLine();

            switch (userInput) {
                case 1:
                    managerMenu();
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

        Car newCar = new Car(carName, companyId);
        carDao.add(newCar, companyId);
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
