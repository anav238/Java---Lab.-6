public class CatalogAlreadyExistsException extends Exception {
    public CatalogAlreadyExistsException(String catalogName) {
        super("Catalog with name " + catalogName + " already exists.");
    }
}
