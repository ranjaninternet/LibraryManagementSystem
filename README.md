# LMS (Learning Management System) - Backend

LMS backend practice project built with Java and Spring Boot.

The idea is to support a simple learning platform where:
- instructors can create courses
- students can enroll in courses
- students can access lessons for courses they joined

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Maven
- Lombok

---

## What I Have Implemented

### 1) Core Domain Models (Entities)

I created these main entities:
- `User` - stores username, password, email, and role
- `Course` - stores title, description, price, and instructor
- `Lesson` - stores lesson title and course relation
- `Enrollment` - links student and course
- `Role` enum - `STUDENT` and `INSTRUCTOR`

I also added a common `BaseEntity` with shared fields like:
- `id`
- `createdAt`
- `updatedAt`

---

### 2) Service Layer

Added `CourseService` with common business logic:
- get all courses
- get course by id
- get courses by instructor
- create course
- update course
- delete course

---

### 3) Controller Layer (API Endpoints)

Added controller classes for:
- `AuthController` (`/api/auth`)
  - login
  - register (currently partial/in progress)
- `CourseController` (`/api/courses`)
  - view catalog
  - view single course
  - create course (instructor only)
- `LessonController` (`/api/lessons`)
  - get lessons by course
  - get lesson by id
- `EnrollmentController` (`/api/enrollments`)
  - list enrollments by student
  - enroll student into course

---

