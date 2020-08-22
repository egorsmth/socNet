package socnet.utils;

public class Either<D,E> {
    private final D data;
    private final E error;

    public Either(D data, E error) {
        this.data = data;
        this.error = error;
    }

    public D left() {
        return data;
    }

    public E right() {
        return error;
    }

    public boolean isLefty() {
        return this.left() != null;
    }

    public boolean isRighty() {
        return this.right() != null;
    }
}
