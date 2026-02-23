package com.example.LMS.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LMS.entity.Enrollment;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonDto>> getLessonsByCourse(
            @PathVariable Long courseId,
            @AuthenticationPrincipal LmsUserPrincipal principal) {
        if (!enrollmentService.isEnrolled(principal.getUserId(), courseId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal LmsUserPrincipal principal) {
        LessonDto lesson = lessonService.findById(id);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }
        if (!enrollmentService.isEnrolled(principal.getUserId(), lesson.getCourseId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(lesson);
    }

}