package socnet.utils;

public enum Roles {
    ANY("any"),
    NON_AUTH("non_auth"),
    AUTH("auth"),
    ADMIN("admin");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
