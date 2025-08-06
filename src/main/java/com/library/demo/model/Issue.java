package com.library.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issues")
@Getter
@Setter
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueId;

    private String issueDate;
    private String returnDate;

    @ManyToOne
    @JoinColumn(name = "student_usn", referencedColumnName = "usn")
    private Student issueStudent;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book issueBook;

    public Issue(String issueDate, String returnDate, Student issueStudent, Book issueBook) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issueStudent = issueStudent;
        this.issueBook = issueBook;
    }
}
