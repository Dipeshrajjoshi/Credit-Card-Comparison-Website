package com.example.demo.controller;

import com.example.demo.model.Feedback;
import com.example.demo.service.EmailSenderService;
import com.example.demo.util.FeedbackExcelWriter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = {"*"}) // Frontend origin
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private EmailSenderService emailSenderService;

    // Replace with actual team emails
    private final List<String> teamEmails = Arrays.asList(
        "csray1005@gmail.com",
        "member2@example.com",
        "member3@example.com",
        "member4@example.com",
        "member5@example.com"
    );

    @PostMapping
    public ResponseEntity<?> handleFeedback(@Valid @RequestBody Feedback feedback) {
        // Log the received feedback
        System.out.println("Feedback Received:");
        System.out.println("Email: " + feedback.getEmail());
        System.out.println("Mobile: " + feedback.getMobile());
        System.out.println("Complaint: " + feedback.getComplaint());

        // 1. Save feedback to Excel
        FeedbackExcelWriter.writeFeedbackToExcel(feedback);

        // 2. Notify all team members via email
        String subject = "New Feedback Submitted";
        String body = String.format(
            "New feedback received:\n\nEmail: %s\nMobile: %s\nComplaint:\n%s",
            feedback.getEmail(), feedback.getMobile(), feedback.getComplaint()
        );

        for (String email : teamEmails) {
            emailSenderService.sendEmail(email, subject, body);
        }

        return ResponseEntity.ok("Feedback submitted successfully");
    }
}
