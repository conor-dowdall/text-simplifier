package ie.atu.sw.simplifiermenu;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import ie.atu.sw.menu.Menu;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimplifierMainMenu extends Menu {

    private WordReplacer wordReplacer = new WordReplacer();
    private SimplifierSettingsMenu settingsMenu;

    public SimplifierMainMenu() {
        super("Simplifier", new Scanner(System.in));
        settingsMenu = new SimplifierSettingsMenu(this.scanner, this.wordReplacer);

        List<MenuItem> itemList = List.of(
                new MenuItem(
                        "1",
                        "Launch Simplify File",
                        () -> {
                            this.launchSimplifyTextFile();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "2",
                        "Launch Simplify Text",
                        () -> {
                            this.launchSimplifyText();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "3",
                        "Load Word-Embeddings File",
                        this::loadWordEmbeddingsFileAndPrintMenu),
                new MenuItem(
                        "4",
                        "Load Replacement-Words File",
                        this::loadReplacementWordsFileAndPrintMenu),
                new MenuItem(
                        "s",
                        "Settings",
                        () -> {
                            this.launchSettingsMenu();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "q",
                        "Quit",
                        () -> this.quitProgram()));

        this.addMenuItemList(itemList);

        this.printMenuAndAcceptChoice();
    }

    private void loadWordEmbeddingsFileAndPrintMenu() {
        try {
            this.settingsMenu.loadWordEmbeddingsFile();
        } catch (IOException e) {
            this.printError(e.getMessage());
        } finally {
            this.printMenuAndAcceptChoice();
        }
    }

    private void loadReplacementWordsFileAndPrintMenu() {
        try {
            this.settingsMenu.loadReplacementWordsFile();
        } catch (IOException e) {
            this.printError(e.getMessage());
        } finally {
            this.printMenuAndAcceptChoice();
        }
    }

    private void launchSimplifyTextFile() {

        try {
            this.printTitle("Simplify File");

            if (this.wordReplacer.isWordEmbeddingMapNull()) {
                this.settingsMenu.loadWordEmbeddingsFile();
            }

            if (this.wordReplacer.isReplacementWordSetNull()) {
                this.settingsMenu.loadReplacementWordsFile();
            }

            String inputTextFileName = this.settingsMenu.scanInputTextFileName();
            String outputTextFileName = this.settingsMenu.scanOutputTextFileName();

            this.wordReplacer
                    .writeReplacedFile(
                            inputTextFileName,
                            outputTextFileName);

            this.printSuccess("OUTPUT TEXT file written (" + outputTextFileName + ").");
        } catch (Exception e) {
            this.printError(e.getMessage());
        }
    }

    private void launchSimplifyText() {

        try {
            this.printTitle("Simplify Text");

            if (this.wordReplacer.isWordEmbeddingMapNull()) {
                this.settingsMenu.loadWordEmbeddingsFile();
            }

            if (this.wordReplacer.isReplacementWordSetNull()) {
                this.settingsMenu.loadReplacementWordsFile();
            }

            System.out.println();
            this.printWithUnderline("Enter some text to simplify:");
            System.out.println();

            String text = this.scanner.nextLine();
            String simplified = this.wordReplacer.replaceString(text);

            System.out.println();
            this.printWithUnderline("Simplified Text:");
            System.out.println();
            System.out.println(simplified);

        } catch (Exception e) {
            this.printError(e.getMessage());
        }
    }

    private void launchSettingsMenu() {
        this.settingsMenu.printMenuAndAcceptChoice();
    }

    private void quitProgram() {
        this.scanner.close();
        this.printInfo("Quitting");
        System.exit(0);
    }
}
