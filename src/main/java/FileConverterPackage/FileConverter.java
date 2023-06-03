package FileConverterPackage;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

public class FileConverter {
    public static void main(String[] args) {

    }

    public void ConvertPDFToWord(String wordFilePath, String pdfFilePath) {
        System.out.println("Converting " + wordFilePath + " to " + pdfFilePath);
        //Create a PdfDocument object
        PdfDocument doc = new PdfDocument();
        //Load a sample PDF document
        doc.loadFromFile(wordFilePath);
        //Convert PDF to Doc and save it to a specified path
        doc.saveToFile(pdfFilePath, FileFormat.DOC);
        doc.close();
    }
    public void ConvertWordToPDF(String wordFilePath, String pdfFilePath) {
        System.out.println("Converting " + wordFilePath + " to " + pdfFilePath);
        //Create a PdfDocument object
        PdfDocument doc = new PdfDocument(wordFilePath);

        //Convert PDF to Doc and save it to a specified path
        doc.saveToFile(pdfFilePath, FileFormat.PDF);
        doc.close();
    }

}