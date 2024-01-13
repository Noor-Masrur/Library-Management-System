package com.tigerit.LMS.entities;

import java.time.LocalDate;

public class Borrowing {
    private Long issuedBookId;
    private Long issueId;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Long getIssuedBookId() {
        return issuedBookId;
    }

    public void setIssuedBookId(Long issuedBookId) {
        this.issuedBookId = issuedBookId;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Borrowing(Long issuedBookId, Long issueId, LocalDate issueDate) {
        long monthsToAdd = 6;
        this.issuedBookId = issuedBookId;
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusMonths(monthsToAdd);
    }

    public Borrowing(Long issuedBookId, Long issueId, LocalDate issueDate, LocalDate dueDate) {
        this.issuedBookId = issuedBookId;
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "issuedBookId=" + issuedBookId +
                ", issueId=" + issueId +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
