package com.example.spontecular.service.utility;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TextExtractor {

    public String extractTextFromPdf(MultipartFile pdfFile) {
        String text;

        try(PDDocument document = Loader.loadPDF(new RandomAccessReadBuffer(pdfFile.getInputStream())))
        {
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read PDF file", e);
        }
        return text;
    }

    public String extractTextFromWord(MultipartFile wordFile) {
        StringBuilder sb = new StringBuilder();

        try (InputStream inputStream = wordFile.getInputStream()) {
            XWPFDocument document = new XWPFDocument(inputStream);

            // Extract text from paragraphs
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                sb.append(para.getText()).append("\n");
            }

            // Extract text from tables
            List<XWPFTable> tables = document.getTables();
            for (XWPFTable table : tables) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        sb.append(cell.getText()).append("\t");
                    }
                    sb.append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Word file", e);
        }
        return sb.toString();
    }
}


