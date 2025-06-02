package command;

import command.CommandInterface;

public class CommandFactory {
    public static CommandInterface getCommand(String action) {
        switch (action) {
            case "login": return new LoginCommand();
            case "home": return new HomeCommand();
            case "detail": return new DetailCommand();
            default: return new UnknownCommand(); // graceful fallback
        }
    }
}
