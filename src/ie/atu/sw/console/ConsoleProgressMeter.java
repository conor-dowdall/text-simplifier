package ie.atu.sw.console;

/**
 * A class for displaying a terminal progress meter. The progress meter creates
 * an animated bar that updates in place, providing a visual indication of
 * progress within a loop. The effect works best when System.out.println() is
 * not called until the progress meter is finished.
 * <p>
 * Important notes:
 * </p>
 * <ul>
 * <li>The progress meter will NOT work in the Eclipse console, but will work in
 * Windows (DOS), Mac, and Linux terminals.</li>
 * <li>The meter uses the carriage return character "\r" to overwrite the
 * current line. Any other output between calls to this method will break the
 * animation.</li>
 * <li>If the progress bar size exceeds the terminal width, a new line will be
 * added, which may cause the meter to not display correctly.</li>
 * </ul>
 * 
 * @see ConsoleColor
 */
public class ConsoleProgressMeter {

    /**
     * Prints a progress bar on the terminal with the current completion percentage.
     * This method should be called in a loop to update the progress meter
     * dynamically.
     * 
     * @param index The current progress step (must be between 0 and total).
     * @param total The total number of steps (must be a positive integer).
     * 
     * @throws IllegalArgumentException If {@code index} is greater than
     *                                  {@code total}.
     */
    public static void printProgress(int index, int total) {
        if (index > total)
            return; // Out of range

        int size = 50; // Must be less than console width
        char done = '█'; // Change to whatever you like.
        char todo = '░'; // Change to whatever you like.

        // Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++)
            sb.append(i < completeLen ? done : todo);

        /*
         * The line feed escape character "\r" returns the cursor to the
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        ConsoleColor consoleColor = ConsoleColor.YELLOW;
        if (complete > 80)
            consoleColor = ConsoleColor.GREEN_BOLD_BRIGHT;
        else if (complete > 50)
            consoleColor = ConsoleColor.GREEN_BRIGHT;

        System.out.print(
                "\r" + ConsoleColor.BLACK_BACKGROUND + consoleColor + sb + "] " + complete + "%");

        // Once the meter reaches its max, move to a new line.
        if (index == total)
            System.out.println(ConsoleColor.RESET);
    }

}
