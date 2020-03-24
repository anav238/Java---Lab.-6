import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ReportHTMLCommand implements Command {
    CatalogManager catalogManager;

    public ReportHTMLCommand(CatalogManager catalogManager) {
        this.catalogManager = catalogManager;
    }

    @Override
    public String getName() {
        return "reportHTML";
    }

    @Override
    public String getSyntax() {
        return getName() + " <catalogName> <pathToSave>";
    }

    @Override
    public void process(String[] args) throws IncorrectCommandSyntaxException, CatalogNotFoundException, IOException {
        if (args.length != 1)
            throw new IncorrectCommandSyntaxException(getSyntax());
        String catalogName = args[0];
        //Gasim catalogul cu numele dat ca argument si cream un raport HTML pe baza lui.
        Catalog catalog = catalogManager.findCatalog(catalogName);
        String report = buildHTMLReport(catalog);

        //Salvam raportul intr-un fisier de tip HTML de la path-ul catalogului.
        Path pathToSave = Paths.get(catalog.getPath() + "/htmlReport.html");
        if (!Files.exists(pathToSave))
            Files.createFile(pathToSave);
        BufferedWriter writer = Files.newBufferedWriter(pathToSave);
        writer.write(report);
        writer.close();

        System.out.println("Report created succesfully at catalog path.");
    }

    private String buildHTMLReport(Catalog catalog) {
        StringBuilder report = new StringBuilder("<html>\n<body>");
        //Adaugam tag-ul pentru catalog, care contine atributul name
        report.append("<catalog name=" + catalog.getName() + ">\n");
        //Adaugam tag-urile pentru documente, ce contin atributele id, name, location
        //si au in interior lista lor de tag-uri (perechile key-value).
        for (Document document:catalog.getDocuments()) {
            report.append("<document id=" + document.getId() + " name=" + document.getName() +
                    " location=" + document.getLocation() + ">\n");
            for (Map.Entry<String, Object> entry : document.getTags().entrySet())
                 report.append("<tag name=" + entry.getKey() + " value=" + entry.getValue() + ">\n");
            report.append("</document>\n");
        }
        //Inchidem tag-urile catalog, body si html
        report.append("</catalog>\n</body></html>");
        return report.toString();
    }
}
