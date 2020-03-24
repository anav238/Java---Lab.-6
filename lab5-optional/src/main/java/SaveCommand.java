import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class SaveCommand implements Command {
    //Managerul de cataloage are rolul de a identifica catalogul ce urmeaza a fi salvat dupa numele lui (dat ca argument).
    CatalogManager catalogManager;

    public SaveCommand(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getSyntax() {
        return getName() + " <catalogName>";
    }

    @Override
    public void process(String[] args) throws IncorrectCommandSyntaxException, CatalogNotFoundException, IOException {
        if (args.length != 1)
            throw new IncorrectCommandSyntaxException(getSyntax());
        String catalogName = args[0];
        Catalog catalog = catalogManager.findCatalog(catalogName);
        //Daca nu apar exceptii in functie (IncorrectCommandSyntaxException sau CatalogNotFound), vom salva
        //catalogul la calea indicata: catalog.getPath().
        saveCatalog(catalog);
    }

    private void saveCatalog(Catalog catalog) throws IOException {
        //Pentru a salva catalogul, cream intai un director nou la calea indicata de obiectul catalog
        //(daca directorul nu exista deja).
        String pathString = catalog.getPath();
        Path catalogPath = Paths.get(pathString);
        if (!Files.exists(catalogPath) || !Files.isDirectory(catalogPath))
            Files.createDirectory(catalogPath);

        //Cream si un fisier ce va retine informatii despre catalogul in sine (daca acesta nu exista deja).
        Path filePath = Paths.get(pathString + "/catalogData.txt");
        if (!Files.exists(filePath))
            Files.createFile(filePath);
        BufferedWriter writer = Files.newBufferedWriter(filePath);
        writer.write(catalog.getName());
        writer.close();

        //Salvam in directorul creat fisiere text ce contin informatii despre toate documentele din catalog.
        for (Document document:catalog.getDocuments())
            saveDocument(document, pathString);

        System.out.println("Catalog saved succesfully.");
    }

    private void saveDocument(Document document, String pathString) throws IOException {
        //Cream fisierul text corespunzator documentului, daca acesta nu exista deja.
        pathString += "/" + document.getName() + ".txt";
        Path filePath = Paths.get(pathString);
        if (!Files.exists(filePath))
            Files.createFile(filePath);

        //Scriem in fisier toate informatiile despre document.
        BufferedWriter writer = Files.newBufferedWriter(filePath);
        writer.write(document.getId() + "\n");
        writer.write(document.getName() + "\n");
        writer.write(document.getLocation() + "\n");

        //Scriem si numarul de tag-uri, pentru ca acestea sa poata fi citite mai usor la load.
        writer.write(document.getTags().size() + "\n");
        //Pentru fiecare intrare din map-ul cu tag-uri, afisam cheia (numele tag-ului) si valoarea.
        for (Map.Entry<String, Object> entry : document.getTags().entrySet())
            writer.write(entry.getKey() + " " + entry.getValue() + "\n");

        writer.close();
    }
}
