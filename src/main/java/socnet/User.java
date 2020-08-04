package socnet;

public class User {
    private String name;
    private Product product;
    public User(String name, Product p) {
        this.setName(name);
        this.setProduct(p);
    }

    public User(String name) {
        this.setName(name);
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
}
