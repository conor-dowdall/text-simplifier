package ie.atu.sw.util;

import java.io.FileNotFoundException;

public interface InputReaderInterface {

        String getFileName(
                        String fileNameDescription,
                        String defaultFileName,
                        boolean checkIfFileExists)
                        throws FileNotFoundException;

        String getString();

        int getInt(
                        String intDescription,
                        int defaultInt)
                        throws NumberFormatException;

        void close();

}
