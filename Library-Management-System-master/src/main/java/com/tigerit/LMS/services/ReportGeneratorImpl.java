package com.tigerit.LMS.services;

import com.tigerit.LMS.entities.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ReportGeneratorImpl implements ReportGenerator{

    private BookManager bookManager;
    private BorrowingManager borrowingManager;
    private static final Logger logger =  LogManager.getLogger(ReportGeneratorImpl.class);
    ;

    public ReportGeneratorImpl(BookManager bookManager, BorrowingManager borrowingManager) {
        this.bookManager = bookManager;
        this.borrowingManager = borrowingManager;
    }

    public String generateAllBooksReport() {
        String reportContent = null;
        FileWriter writer = null;

        try {
            List<Book> allBooks = bookManager.getAllAvailableBooks();
            reportContent = formatBookList(allBooks, "All Books Report");

            writer = new FileWriter("all_books_report.csv");
            writer.write(reportContent);
        } catch (IOException e) {
            // Handle any I/O exceptions gracefully
            logger.error("Failed to generate report:", e); // Log the error
            throw new RuntimeException("Report generation failed", e); // Rethrow as a runtime exception for user-friendly handling
        } finally {
            // Ensure file resources are closed even if exceptions occur
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.warn("Failed to close file writer:", e); // Log the warning
                }
            }
        }

        return reportContent;
    }

    private String formatBookList(List<Book> allBooks, String allBooksReport) {
        StringBuilder sb = new StringBuilder();
        // Header row
        sb.append("Title,Author,tGenre,Publication Date,Number of Copies\n");

        // Data rows
        for (Book book : allBooks) {
            sb.append(book.getTitle()).append(",");
            sb.append(book.getAuthor()).append(",");
            sb.append(book.getGenre()).append(",");
            sb.append(book.getPublicationDate()).append(",");
            sb.append(book.getNumberOfCopies()).append("\n");
        }

        return sb.toString();
    }

}
