import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LMS.entity.Enrollment;
import com.example.LMS.entity.Role;

import lombok.RequiredArgsConstructor;

pac

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;


    @GetMapping("/catalog")
    public ResponseEntity<List<CourseDto>> getCourseCatalog() {
        List<CourseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal LmsUserPrincipal principal) {
        if (principal.getRole() == Role.STUDENT) {
            if (!enrollmentService.isEnrolled(principal.getUserId(), id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.ok(courseService.findById(id));
    }
    @PostMapping
    public ResponseEntity<CourseDto> create(
            @RequestBody CreateCourseRequest request,
            @AuthenticationPrincipal LmsUserPrincipal principal) {
        if (principal.getRole() != Role.INSTRUCTOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        CreateCourseRequest withInstructor = new CreateCourseRequest(
                request.title(),
                request.description(),
                request.price(),
                principal.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(withInstructor));
    }

}
