package com.tigerit.LMS.entities;

import java.sql.Date;
import java.util.Calendar;


public class Borrowing {
    private Long issuedBookId;
    private Long issueId;
    private Date issueDate;
    private Date dueDate;

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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Borrowing(Long issueId,Long issuedBookId, Date issueDate) {

        this.issuedBookId = issuedBookId;
        this.issueId = issueId;
        this.issueDate = issueDate;


        int monthsToAdd = 6;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        // Add 6 months
        calendar.add(Calendar.MONTH, monthsToAdd);
        this.dueDate = new Date(calendar.getTimeInMillis());
    }

    public Borrowing( Long issueId, Long issuedBookId, Date issueDate, Date dueDate) {
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
