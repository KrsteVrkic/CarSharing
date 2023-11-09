package carsharing;

public class Main {

    public static void main(String[] args) {

        DbCompanyDao companyDao = new DbCompanyDao();
        DbCarDao carDao = new DbCarDao();
        DbCustomerDao customerDao = new DbCustomerDao();
        UserInterface userInterface = new UserInterface(companyDao, carDao, customerDao);
        userInterface.menu();

    }
}