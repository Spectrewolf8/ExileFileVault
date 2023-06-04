package FileConverterPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileConverter {
    public static void main(String[] args) {
        String pythonScriptPath = "D:\\My Codes\\Java\\OOSE Semester Project FileHub\\ExileFileVault\\src\\main\\java\\FileConverterPackage\\script.py";
        String filePath = "TestWordFile.docx";
        String destinationPath = "TestWordFile.pdf";
        // 0 for docx_to_pdf, 1 for pdf_to_docx
        int conversionType = 0;  // or "docx_to_pdf"

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, filePath, destinationPath, conversionType + "");
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Conversion completed successfully.");
            } else {
                System.out.println("Conversion failed.");

                // Print the error stream
                InputStream errorStream = process.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
