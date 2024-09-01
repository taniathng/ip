public class Parser {

    public Command parse(String userInput) {
        String[] parts = userInput.split(" ", 2);
        String commandType = parts[0];

        switch (commandType) {
            case "bye":
                return new Command("bye");
            case "list":
                return new Command("list");
            case "mark":
                return new Command("mark", parts.length > 1 ? parts[1] : null);
            case "unmark":
                return new Command("unmark", parts.length > 1 ? parts[1] : null);
            case "delete":
                return new Command("delete", parts.length > 1 ? parts[1] : null);
            case "todo":
                return new Command("todo", parts.length > 1 ? parts[1] : null);
            case "deadline":
                return new Command("deadline", parts.length > 1 ? parts[1] : null);
            case "event":
                return new Command("event", parts.length > 1 ? parts[1] : null);
            default:
                return new Command("unknown");
        }
    }
}
