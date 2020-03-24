import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ViewCommand implements Command {
    //Ne folosim de instanta de catalogManager pentru a identifica catalogul transmis ca argument.
    CatalogManager catalogManager;

    public ViewCommand(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    //Functie folosita pt a identifica, in shell, comanda.
    @Override
    public String getName() {
        return "view";
    }

    //Functie folosita in exceptii, pentru a-i prezenta utilizatorului sintaxa corecta a comenzii.
    @Override
    public String getSyntax() {
        return getName() + " <catalogName>";
    }

    @Override
    public void process(String[] args) throws IncorrectCommandSyntaxException, CatalogNotFoundException, IOException, URISyntaxException {
        if (args.length != 1)
            throw new IncorrectCommandSyntaxException(getSyntax());
        String catalogName = args[0];
        Catalog catalog = catalogManager.findCatalog(catalogName);
        view(catalog);
    }

    private void view(Catalog catalog) throws IOException, URISyntaxException {
        //Functia de vizualizare catalog deschide toate documentele din acel catalog, prin metoda view
        //ce ia ca parametru un document.
        for (Document doc:catalog.getDocuments())
            view(doc);
    }

    private void view(Document doc) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        if (doc.getLocation().startsWith("http")) {
            //Daca documentul este o pagina web, il deschidem folosind browserul implicit.
            System.out.println("deschis pagina web " + doc.getLocation());
            desktop.browse(new URI(doc.getLocation()));
        }
        else {
            //In caz contrar, folosim functia open, care deschide fisierul cu programul implicit
            //pentru deschiderea fisierelor de tipul sau.
            System.out.println("deschis fisier " + doc.getLocation());
            desktop.open(new File(doc.getLocation()));
        }
    }


}
