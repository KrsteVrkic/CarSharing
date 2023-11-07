package carsharing;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner = new Scanner(System.in);

    Company.CompanyDao companyDao;

    public UserInterface(Company.CompanyDao companyDao) {
        this.companyDao = companyDao;
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
            System.out.println("Company list:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.println((i + 1) + ". " + companies.get(i).getName());
            }
        }
    }

    public void createCompany() {
        System.out.print("Enter the company name: ");
        String companyName = scanner.nextLine();
        Company newCompany = new Company(0, companyName);
        companyDao.add(newCompany);
    }
}
