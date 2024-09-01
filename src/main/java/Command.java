public class Command {
    private String commandType;
    private String arguments;

    public Command(String commandType) {
        this.commandType = commandType;
        this.arguments = null;
    }

    public Command(String commandType, String arguments) {
        this.commandType = commandType;
        this.arguments = arguments;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getArguments() {
        return arguments;
    }
}
