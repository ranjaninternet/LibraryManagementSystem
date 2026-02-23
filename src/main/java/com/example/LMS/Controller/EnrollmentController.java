package com.example.LMS.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    
    private final EnrollmentService enrollmentService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDto>> listByStudent(
            @PathVariable Long studentId,
            @AuthenticationPrincipal LmsUserPrincipal principal) {
        if (principal.getRole() == Role.STUDENT && !studentId.equals(principal.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(enrollmentService.findByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<EnrollmentDto> enroll(
            @Valid @RequestBody EnrollRequest request,
            @AuthenticationPrincipal LmsUserPrincipal principal) {
        Long currentUserId = principal.getUserId();
        if (principal.getRole() == Role.STUDENT && !request.studentId().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        EnrollmentDto dto = enrollmentService.enrollStudent(request.studentId(), request.courseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
