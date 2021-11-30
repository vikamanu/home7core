package Home8core.view;
import Home8core.controller.Controller;
import Home8core.controller.IController;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class UserInterface implements IUserInterface {
    IController controller = new Controller();
    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\nПрограмма начала работу.");
            System.out.println("Введите 1 или 2 для получения желаемых данных:");
            System.out.println("\n1 - хочу знать о погоде в каком-то городе\n2 - хочу получить информацию из предыдущих обращений к программе\n \nвведите 'exit' для выхода");
            Scanner scanner = new Scanner(System.in);
            String menuChoice = scanner.nextLine();
            checkIsExit(menuChoice);

            if (menuChoice.equalsIgnoreCase("2")) {
                try {
                    controller.onCommandChosen(3);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Введите имя города на латинице:");
                String userResponse = scanner.nextLine();
                try {
                    controller.onCityInput(userResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                System.out.println("Введите команду\n1 - для получения погоды на текущий день\n2 - для получения погоды на 5 дней");

                System.out.println("* * * * * * * * * *");
                int selectedCommand = scanner.nextInt();
                try {
                    controller.onCommandChosen(selectedCommand);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void checkIsExit(String userResponse) {
        if (userResponse.equalsIgnoreCase("exit") ||
                userResponse.equalsIgnoreCase("выход")) {

            System.out.println("Программа завершает работу. Хорошего дня!");
            System.exit(0);
        }
    }
}
