package parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class CSVWriter {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "status, oldUrl, newUrl,failure, actual";

    HTMLReader html = new HTMLReader();

    public static void writeCsvFile(String fileName, List<Scenario> allScenarios) {
        FileWriter fileWriter = null;

        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            fileWriter = new FileWriter(file);
            fileWriter.append(FILE_HEADER);

            fileWriter.append(NEW_LINE_SEPARATOR);
            for (Scenario scenario : allScenarios) {
                fileWriter.append(scenario.getStatus());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(scenario.getGiven());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(scenario.getThen1());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(scenario.getError1() != null ? scenario.getError1() : scenario.getError2());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(scenario.getError1() != null ? scenario.getUrl(): "");
                fileWriter.append(NEW_LINE_SEPARATOR);

            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }


        }
    }
}


