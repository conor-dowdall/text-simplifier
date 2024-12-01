package ie.atu.sw.util;

import java.io.FileNotFoundException;

/**
 * Interface for reading various types of input from the user.
 * Provides methods for retrieving strings, integers, and file names, along with
 * handling default values.
 */
public interface InputReaderInterface {

        /**
         * Prompts the user to enter a file name, with an optional default and
         * validation for file existence.
         * 
         * @param fileNameDescription a description of the file to be entered, shown to
         *                            the user
         * @param defaultFileName     the default file name to use if the user presses
         *                            ENTER without input
         * @param checkIfFileExists   whether to check if the file exists before
         *                            accepting the input
         * @return the file name entered by the user, or the default file name if input
         *         is empty
         * @throws FileNotFoundException if the file does not exist and
         *                               checkIfFileExists is true
         */
        String getFileName(
                        String fileNameDescription,
                        String defaultFileName,
                        boolean checkIfFileExists)
                        throws FileNotFoundException;

        /**
         * Prompts the user to enter a string.
         * 
         * @return the string entered by the user
         */
        String getString();

        /**
         * Prompts the user to enter a string, with a default value to use if the user
         * presses ENTER without input.
         * 
         * @param stringDescription a description of the string to be entered, shown to
         *                          the user
         * @param defaultString     the default string to use if the user presses ENTER
         *                          without input
         * @return the string entered by the user, or the default string if input is
         *         empty
         */
        String getString(
                        String stringDescription,
                        String defaultString);

        /**
         * Prompts the user to enter an integer, with a default value to use if the user
         * presses ENTER without input.
         * 
         * @param intDescription a description of the integer to be entered, shown to
         *                       the user
         * @param defaultInt     the default integer to use if the user presses ENTER
         *                       without input
         * @return the integer entered by the user, or the default integer if input is
         *         empty
         * @throws NumberFormatException if the user enters an invalid integer
         */
        int getInt(
                        String intDescription,
                        int defaultInt)
                        throws NumberFormatException;

        /**
         * Closes any resources used by the input reader (e.g., the scanner).
         */
        void close();

}
