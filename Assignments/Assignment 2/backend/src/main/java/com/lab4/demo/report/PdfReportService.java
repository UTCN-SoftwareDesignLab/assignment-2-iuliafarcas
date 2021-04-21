package com.lab4.demo.report;

import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {

        List<BookDTO> books = bookService.outOfStock();

        try{
            PDDocument pdf = new PDDocument();
            PDPage page = new PDPage();
            pdf.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(pdf, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 700);

            contentStream.showText("OUT OF STOCK: ");
            contentStream.newLine();

            for(BookDTO bookDTO : books)
            {
                contentStream.showText(bookDTO.getId() + ", " + bookDTO.getTitle() + ", " + bookDTO.getAuthor() + ", " + bookDTO.getPrice());
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            pdf.save("Report.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
