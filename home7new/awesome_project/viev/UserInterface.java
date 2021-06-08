package ru.vikamanu.home7new.awesome_project.viev;

import ru.vikamanu.home7new.awesome_project.GlobalState;
import ru.vikamanu.home7new.awesome_project.controller.IWeatherController;
import ru.vikamanu.home7new.awesome_project.controller.WeatherController;

import java.util.Scanner;

public class UserInterface implements IUserInterface {
    private IWeatherController controller = new WeatherController();

    @Override
    public void showUI() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введи название города или exit для выхода");
            String userInput = scanner.nextLine();
            checkIsExit(userInput);
            GlobalState.getInstance().setSelectedCity(userInput);
            System.out.println("Введите команду: \n1. - погода на 5 дней");
            String command = scanner.nextLine();
            try {
                validateUserInput(command);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Неверный ввод");
                continue;
            }
            controller.onUserInput(Integer.parseInt(command));
        }
    }

    private void checkIsExit(String input) {
        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("выход")) {
            System.out.println("Завершаю работу");
            System.exit(0);
        }
    }
    private void validateUserInput(String command) throws Exception {
if (command == null || command.length() != 1){
    throw new Exception("Невалидный ввод");
}
int answer = 0;
try {
    answer = Integer.parseInt(command);
} catch (NumberFormatException e){
    throw new Exception("Невалидный ввод");
}
    }
    }

