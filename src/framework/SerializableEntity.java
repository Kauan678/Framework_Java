package framework;

public interface SerializableEntity<T> {
    String serialize();
    T deserialize(String data);
}
