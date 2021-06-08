package ru.vikamanu.home7new.awesome_project;

import ru.vikamanu.home7new.awesome_project.viev.IUserInterface;
import ru.vikamanu.home7new.awesome_project.viev.UserInterface;

public class Main {
    public static void main(String[] args) {
        IUserInterface userInterface = new UserInterface();
        userInterface.showUI();
    }
}
