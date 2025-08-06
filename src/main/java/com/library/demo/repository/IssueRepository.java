package com.library.demo.repository;

import com.library.demo.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByIssueStudent_Usn(String usn);
}
