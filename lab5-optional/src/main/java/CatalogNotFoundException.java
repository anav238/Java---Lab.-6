public class CatalogNotFoundException extends Exception {
    public CatalogNotFoundException(String catalogName) {
        super("Catalog " + catalogName + " doesn't exist.");
    }
}
