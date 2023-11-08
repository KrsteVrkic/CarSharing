package carsharing;

import java.util.List;

public class Company {

    private int id;
    private String name;

    public Company(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public interface CompanyDao {
        List<Company> findAll();
        void add(Company company);
        int findCompanyId(String companyId);
    }
}
