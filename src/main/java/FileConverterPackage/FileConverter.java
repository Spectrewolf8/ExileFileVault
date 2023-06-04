package FileConverterPackage;

import java.io.IOException;

public class FileConverter {
    public static void main(String[] args) {
        String pythonScriptPath = "script.py";
        String filePath = "R634080006StickerMuleInvoiceForSpec.pdf";
        String destinationPath = "R634080006StickerMuleInvoiceForSpec.docx";
        String conversionType = "pdf_to_docx";  // or "docx_to_pdf"

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, filePath, destinationPath, conversionType);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Conversion completed successfully.");
            } else {
                System.out.println("Conversion failed.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
