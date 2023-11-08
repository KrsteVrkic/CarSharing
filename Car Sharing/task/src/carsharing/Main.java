package carsharing;

public class Main {

    public static void main(String[] args) {

        DbCompanyDao companyDao = new DbCompanyDao();
        DbCarDao carDao = new DbCarDao();
        UserInterface userInterface = new UserInterface(companyDao, carDao);
        userInterface.menu();

    }
}