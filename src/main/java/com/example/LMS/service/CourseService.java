package com.example.LMS.service;

import com.example.LMS.dto.CourseDto;
import com.example.LMS.dto.CreateCourseRequest;
import com.example.LMS.dto.UpdateCourseRequest;
import com.example.LMS.entity.Course;
import com.example.LMS.entity.User;
import com.example.LMS.exception.ResourceNotFoundException;
import com.example.LMS.repository.CourseRepository;
import com.example.LMS.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDto findById(Long id) {
        return toDto(getEntityById(id));
    }

    @Transactional(readOnly = true)
    public List<CourseDto> findByInstructorId(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::toDto)
                .toList();
    }


    @Transactional
    public CourseDto create(CreateCourseRequest request) {
        User instructor = userService.getEntityById(request.instructorId());
        Course course = new Course();
        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setPrice(request.price() != null ? request.price() : java.math.BigDecimal.ZERO);
        course.setInstructor(instructor);
        return toDto(courseRepository.save(course));
    }

    @Transactional
    public CourseDto update(Long id, UpdateCourseRequest request) {
        Course course = getEntityById(id);
        if (request.title() != null) course.setTitle(request.title());
        if (request.description() != null) course.setDescription(request.description());
        if (request.price() != null) course.setPrice(request.price());
        if (request.instructorId() != null) {
            course.setInstructor(userService.getEntityById(request.instructorId()));
        }
        return toDto(courseRepository.save(course));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", id);
        }
        courseRepository.deleteById(id);
    }

    /** Loads Course entity by id; throws if not found. Used by LessonService. */
    Course getEntityById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", id));
    }

    private CourseDto toDto(Course course) {
        User instructor = course.getInstructor();
        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                instructor.getId(),
                instructor.getName(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}
