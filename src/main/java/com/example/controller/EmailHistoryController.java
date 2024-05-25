package com.example.controller;

import com.example.dto.emailHistory.EmailHistoryDto;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmailHistoryController {

    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/getAllEmail/email")
    public ResponseEntity<List<EmailHistoryDto>> getAllEmail(@RequestParam("email") String email) {
        // todo 2. Get EmailHistory by email (id, email,message,created_date)
        return ResponseEntity.ok(emailHistoryService.getAllEmailService(email));
    }

    @GetMapping("/getByCreatedDate/email")
    public ResponseEntity<List<EmailHistoryDto>> getByCreatedDateEmail(@RequestParam("date") LocalDate date) {
        // todo 3. Get EmailHistory  by given date (id, email,message,created_date)
        return ResponseEntity.ok(emailHistoryService.getByCreatedDateEmailService(date));
    }

    // todo 4. Pagination (ADMIN) (id, email,message,created_date)

    @GetMapping("getAllPage/email")
    public ResponseEntity<Page<EmailHistoryDto>> getAllPageEmail(@RequestParam("page") Integer page,
                                                                 @RequestParam("size") Integer size) {
        return ResponseEntity.ok(emailHistoryService.getAllPageEmailService(page, size));
    }
}
