import java.util.ArrayList;
import java.util.List;

public class CatalogManager {
    List<Catalog> catalogList;

    public List<Catalog> getCatalogList() {
        return catalogList;
    }

    public CatalogManager() {
        catalogList = new ArrayList<>();
    }

    //Functie care adauga un catalog in lista de cataloage, daca acesta nu exista deja.
    public void addCatalog(Catalog catalog) throws CatalogAlreadyExistsException {
        if (catalogList.contains(catalog))
            throw new CatalogAlreadyExistsException(catalog.getName());
        catalogList.add(catalog);
    }

    //Functie de cautare a unui catalog in lista dupa nume.
    public Catalog findCatalog(String catalogName) throws CatalogNotFoundException {
        for (Catalog catalog:catalogList) {
            if (catalog.getName().equals(catalogName))
                return catalog;
        }
        throw new CatalogNotFoundException(catalogName);
    }

}
