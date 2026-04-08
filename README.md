# LMS (Learning Management System) - Backend

This is my LMS backend practice project built with Java and Spring Boot.

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

I added `CourseService` with common business logic:
- get all courses
- get course by id
- get courses by instructor
- create course
- update course
- delete course

It also converts `Course` entity data into `CourseDto`.

---

### 3) Controller Layer (API Endpoints)

I added controller classes for:
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

I also started role-based access checks in controllers using authenticated user role and id.

---

## Security Work Started

I started JWT-based authentication flow in `AuthController`:
- login authenticates user and generates token
- register path is drafted

Some classes used in auth/controller flow are referenced but not fully present yet (for example request/response DTOs, user principal, utility/helper classes).

---

## Current Project Status

This project is **still in development**.

### Done
- project setup with Maven + Spring Boot dependencies
- basic LMS data model
- first version of services/controllers
- basic access control checks in endpoints

### Pending / Needs Fixing
- missing imports in multiple files
- missing classes/interfaces (DTOs, services, repositories, security helpers)
- no main application class detected yet (`@SpringBootApplication`)
- `application.properties` has only app name; DB/security config still needed
- registration flow in `AuthController` is incomplete

Because of the above, the project may not compile/run until these are completed.

---

## Planned Next Steps

1. Add missing Spring Boot application main class.
2. Create all missing DTOs, repositories, and service classes.
3. Complete JWT utility + user principal + security configuration.
4. Add database configuration in `application.properties`.
5. Fix compile issues and test all endpoints.
6. Add integration tests for auth, courses, lessons, and enrollments.

---

## Project Goal

The goal is to build a clean LMS backend with:
- authentication + authorization
- course management
- enrollment management
- lesson access control

This project is mainly for learning Spring Boot architecture and real-world API design.
