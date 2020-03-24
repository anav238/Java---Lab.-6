import java.io.IOException;
import java.net.URISyntaxException;

public class ListCommand implements Command {
    //Managerul de cataloage are aici rolul de a identifica catalogul al carui nume este dat ca argument,
    //in vederea afisarii documentelor sale.
    CatalogManager catalogManager;

    public ListCommand(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getSyntax() {
        return getName() + " <catalogName>";
    }

    @Override
    public void process(String[] args) throws IncorrectCommandSyntaxException, CatalogNotFoundException {
        if (args.length != 1)
            throw new IncorrectCommandSyntaxException(getSyntax());
        String catalogName = args[0];
        //Cautam catalogul dupa nume in catalogManager, dupa care, daca este gasit (nu a fost aruncata
        //CatalogNotFoundException), ii afisam lista de documente (nume si tag-uri).
        Catalog catalog = catalogManager.findCatalog(catalogName);
        System.out.println(listDocuments(catalog));
    }

    private String listDocuments(Catalog catalog) {
        StringBuilder listResult = new StringBuilder("Catalog " + catalog.getName() + ".");
        listResult.append("Documents: ");
        for (Document document:catalog.getDocuments())
            listResult.append(document.getName() + " " + document.getTags() + "\n");
        return listResult.toString();
    }
}
