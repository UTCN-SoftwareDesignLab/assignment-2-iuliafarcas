package com.lab4.demo.report;

import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final BookService bookService;
    private final String header = "Id,Title,Author,Price\n";

    @Override
    public String export() {

        List<BookDTO> books = bookService.outOfStock();

        File csv = new File("Report.csv");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);

        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Report.csv"), false));

            for(BookDTO bookDTO : books)
            {
                stringBuilder.append(bookDTO.getId());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getTitle());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getAuthor());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getPrice());
                stringBuilder.append("\n");
            }

            writer.write(stringBuilder.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed";
        }

        return "Report.csv";
    }



    @Override
    public ReportType getType() {
        return CSV;
    }
}
