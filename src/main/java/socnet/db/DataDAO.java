package socnet.db;

import socnet.Product;
import socnet.User;

public class DataDAO {
    private static DataDAO instance;
    private DataDAO()
    {

    }

    public static DataDAO instance()
    {
        if (instance == null)
        {
            instance = new DataDAO();
        }
        return instance;
    }

    public User findUser()
    {
        return new User("Aaverage joe");
    }

    public User findUser(String id)
    {
        return new User("Aaverage joe but with id:" + id);
    }

    public Product findProduct()
    {
        return new Product("simple product");
    }

    public User findUserWithProduct()
    {
        return new User("Aaverage joe", new Product("simple product"));
    }
}
