import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LoadCommand implements Command {
    //Avem nevoie de o instanta catalogManager pentru a tine evidenta cataloagelor incarcate.
    CatalogManager catalogManager;

    public LoadCommand(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    @Override
    public String getName() {
        return "load";
    }

    @Override
    public String getSyntax() {
        return getName() + " <catalogPath>";
    }

    @Override
    public void process(String[] args) throws IncorrectCommandSyntaxException, IOException, CatalogAlreadyExistsException {
        if (args.length != 1)
            throw new IncorrectCommandSyntaxException(getSyntax());
        //Incarcam catalogul in memorie, dupa care incercam sa il adaugam in catalogManager cu addCatalog, apel ce
        //poate arunca CatalogAlreadyExistsException.
        Catalog catalog = loadCatalog(args[0]);
        catalogManager.addCatalog(catalog);

        System.out.println("Catalog loaded successfully.");
    }

    private Catalog loadCatalog(String path) throws IOException {
        Catalog catalog;
        String catalogName;

        //Respecta formatul de salvare a catalogului, cu un fisier catalogData cu date despre cataloage
        //si fisiere text pentru fiecare document.
        Path catalogDataFile = Paths.get(path + "/catalogData.txt");
        List<String> catalogData = Files.readAllLines(catalogDataFile);
        //Pe prima linie a catalogData se gaseste numele catalogului.
        catalogName = catalogData.get(0);

        //Cream un catalog nou, cu numele gasit mai sus si path-ul primit ca argument.
        catalog = new Catalog(catalogName, path);

        //Parcurgem directorul din path, incarcand toate documentele din acesta (fisierele cu format txt).
        File dir = Paths.get(path).toFile();
        for (File fileEntry:dir.listFiles()) {
            if (!fileEntry.getName().equals("catalogData.txt")
                    && fileEntry.getName().endsWith(".txt")) {
                Document documentFound = loadDocument(fileEntry.toPath());
                catalog.add(documentFound);
            }
        }
        return catalog;
    }

    private Document loadDocument(Path file) throws IOException {
        //Parsam fisierul text conform formatului in care a fost salvat, dupa care cream un document nou
        // cu informatiile obtinute.
        List<String> fileLines = Files.readAllLines(file);
        String documentId = fileLines.get(0);
        String documentName = fileLines.get(1);
        String documentPath = fileLines.get(2);
        Document document = new Document(documentId, documentName, documentPath);

        //Identificam si tag-urile in fisier si le adaugam la document.
        int numberOfTags = Integer.parseInt(fileLines.get(3));
        //Primul tag se va gasi pe linia a patra, dupa celelalte informatii care sunt stocate cate una pe linie.
        for (int i = 4; i < 4 + numberOfTags; i++) {
            String[] keyValuePair = fileLines.get(i).split(" ");
            document.addTag(keyValuePair[0], keyValuePair[1]);
        }
        return document;
    }
}
