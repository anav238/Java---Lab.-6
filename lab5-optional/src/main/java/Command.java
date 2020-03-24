import java.io.IOException;
import java.net.URISyntaxException;

public interface Command {
    public void process(String args[]) throws IncorrectCommandSyntaxException, CatalogNotFoundException, IOException, URISyntaxException, CatalogAlreadyExistsException;
    public String getName();
    public String getSyntax();
}
