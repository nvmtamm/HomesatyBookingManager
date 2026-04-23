package business;

/**
 * Generic interface for CRUD operations on a list of entities.
 */
public interface Workable<T> {
  void addNew(T x);

  void delete(T x);

  T searchById(String id);

  void showAll();

  void readFromFile(String pathFile);
}
