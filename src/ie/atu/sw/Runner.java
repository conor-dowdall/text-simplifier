package ie.atu.sw;

import java.util.Scanner;

import ie.atu.sw.console.ConsoleInputReader;
import ie.atu.sw.menu.ConsoleMenuPrinter;
import ie.atu.sw.simplifiermenu.SimplifierMainMenu;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.WordReplacer;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

public class Runner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        MenuPrinterInterface menuPrinter = new ConsoleMenuPrinter();

        InputReaderInterface inputReader = new ConsoleInputReader(scanner, menuPrinter);

        WordReplacerAbstract wordReplacer = new WordReplacer();

        new SimplifierMainMenu(inputReader, menuPrinter, wordReplacer)
                .printMenuAndAcceptChoice();

    }

}
