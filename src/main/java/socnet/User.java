package socnet;

public class User {
    private String id;
    private String name;
    private Product product;
    public User(String name, Product p) {
        this.setName(name);
        this.setProduct(p);
    }

    public User(String name) {
        this.setName(name);
    }

    public User(String name, String id) {
        this.setName(name);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Object getId() {
        return id;
    }
}
