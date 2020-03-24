import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String args[]) {
        //Cream managerul de cataloage, unde vor fi retinute cataloagele incarcate.
        CatalogManager catalogManager = buildCatalogManager();
        //Cream lista de comenzi suportate de shell.
        List<Command> supportedCommands = buildSupportedCommandList(catalogManager);
        Shell shell = new Shell(supportedCommands);
        shell.processCommands();
    }

    private static CatalogManager buildCatalogManager() {
        String testPath = "C:/Users/anava/Desktop/JavaResources";
        CatalogManager catalogManager = new CatalogManager();
        Catalog catalog = new Catalog("JavaResources", testPath);
        Document doc = new Document("java1", "Java Course 1",
                "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        doc.addTag("type", "Slides");
        doc.addTag("author", "Teacher");
        catalog.add(doc);
        try {
            catalogManager.addCatalog(catalog);
        } catch (CatalogAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return catalogManager;
    }

    private static List<Command> buildSupportedCommandList(CatalogManager catalogManager) {
        List<Command> supportedCommands = new ArrayList<>();
        Command listCommand = new ListCommand(catalogManager);
        Command loadCommand = new LoadCommand(catalogManager);
        Command saveCommand = new SaveCommand(catalogManager);
        Command viewCommand = new ViewCommand(catalogManager);
        Command reportHTMLCommand = new ReportHTMLCommand(catalogManager);
        supportedCommands.addAll(Arrays.asList(listCommand, loadCommand, saveCommand, viewCommand, reportHTMLCommand));
        return supportedCommands;
    }
}
