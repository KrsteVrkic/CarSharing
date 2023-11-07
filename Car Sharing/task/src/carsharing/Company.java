package carsharing;

import java.util.List;

public class Company {
    private int id;
    private String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public interface CompanyDao {
        List<Company> findAll();
        void add(Company company);
    }
}
