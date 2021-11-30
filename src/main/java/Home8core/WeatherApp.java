package Home8core;
import Home8core.view.IUserInterface;
import Home8core.view.UserInterface;

public class WeatherApp {
    public static void main(String[] args) {
        IUserInterface ui = new UserInterface();
        ui.showMenu();
    }
}