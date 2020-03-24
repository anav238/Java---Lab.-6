import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;

public class Shell {
    //Un shell suporta o lista de comenzi, pe care le primeste ca argument in constructor.
    List<Command> supportedCommands;
    BufferedReader reader;

    public Shell(List<Command> supportedCommands) {
        this.supportedCommands = supportedCommands;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void processCommands() {
        while (true) {
            //Pentru a procesa o comanda, dupa citirea din consola, separam numele comenzii (care este mereu
            //primul cuvant din linie) de argumentele comenzii.
            try {
                String commandLine = reader.readLine();
                String commandName = commandLine.substring(0, commandLine.indexOf(" "));
                String[] args = commandLine.substring(commandLine.indexOf(" ")).split(" \\n");
                //Eliminam spatiile de la inceputul argumentelor.
                for (int i = 0; i < args.length; i++)
                    args[i] = args[i].trim();
                //Identificam comanda care trebuie executata, cautand in lista de comenzi suportate.
                Command command = findCommand(commandName);
                command.process(args);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (CatalogAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            } catch (CatalogNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectCommandSyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Command findCommand(String commandName) throws InvalidCommandException {
        for (Command command:supportedCommands) {
            if (command.getName().equals(commandName))
                return command;
        }
        throw new InvalidCommandException(supportedCommands);
    }
}
