package FileConverterPackage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import java.util.List;

public class FileConverter {


    public static void ConvertPDFToWord(String pdfFilePath, String docxFilePath) {

        try {
            PDDocument pdfDocument = PDDocument.load(new File(pdfFilePath));
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdfDocument);

            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(text);

            FileOutputStream out = new FileOutputStream(new File(docxFilePath));
            document.write(out);
            out.close();
            pdfDocument.close();

            System.out.println("PDF converted to Word successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ConvertWordToPDF(String docxFilePath, String pdfFilePath) {


        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(docxFilePath));
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            PDDocument pdfDocument = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            pdfDocument.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(25, 700);
                    contentStream.showText(text);
                    contentStream.endText();
                }
            }

            contentStream.close();
            pdfDocument.save(pdfFilePath);
            pdfDocument.close();
            document.close();

            System.out.println("Word converted to PDF successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
