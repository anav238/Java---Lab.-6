import java.util.List;

public class InvalidCommandException extends Exception {
    //Exceptie folosita cand este apelata o comanda care nu este suportata de shell.

    public InvalidCommandException(List<Command> supportedCommands) {
        super(getErrorMessage(supportedCommands));
    }

    private static String getErrorMessage(List<Command> supportedCommands) {
        //Construim un mesaj de eroare ce contine lista de comenzi suportate de shell.
        StringBuilder errorMessage = new StringBuilder("Invalid command name. Supported commands: ");
        for (Command command:supportedCommands)
            errorMessage.append(command.getName() + " ");
        return errorMessage.toString();
    }
}
