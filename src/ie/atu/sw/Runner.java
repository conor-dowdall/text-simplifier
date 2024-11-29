package ie.atu.sw;

import java.util.Scanner;

import ie.atu.sw.menu.ConsoleMenuPrinter;
import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.simplifiermenu.SimplifierMainMenu;
import ie.atu.sw.wordreplacer.WordReplacer;

public class Runner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MenuPrinter menuPrinter = new ConsoleMenuPrinter();
        WordReplacer wordReplacer = new WordReplacer();

        new SimplifierMainMenu(scanner, menuPrinter, wordReplacer)
                .printMenuAndAcceptChoice();

    }

}
