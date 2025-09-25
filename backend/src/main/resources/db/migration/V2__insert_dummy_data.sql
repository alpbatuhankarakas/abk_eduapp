-- ==== STUDENTS ====
INSERT INTO students (student_number, first_name, last_name, email, date_of_birth, gender, phone, address, enrollment_year, department, status)
VALUES
    ('S1001', 'Alice', 'Johnson', 'alice.johnson@example.com', '2000-05-12', 'FEMALE', '555-1234', '123 Main St', 2018, 'Computer Science', 'ACTIVE'),
    ('S1002', 'Bob', 'Smith', 'bob.smith@example.com', '1999-08-21', 'MALE', '555-5678', '456 Oak Ave', 2017, 'Mathematics', 'GRADUATED'),
    ('S1003', 'Charlie', 'Brown', 'charlie.brown@example.com', '2001-02-03', 'OTHER', '555-9999', '789 Pine Rd', 2019, 'Physics', 'ACTIVE'),
    ('S1004', 'Diana', 'Evans', 'diana.evans@example.com', '2002-07-14', 'FEMALE', '555-4444', '321 Maple St', 2020, 'Computer Science', 'ACTIVE'),
    ('S1005', 'Ethan', 'Taylor', 'ethan.taylor@example.com', '2000-12-30', 'MALE', '555-5555', '654 Elm St', 2018, 'Mathematics', 'SUSPENDED');

-- ==== TEACHERS ====
INSERT INTO teachers (teacher_number, first_name, last_name, email, phone, department, title, status)
VALUES
    ('T2001', 'David', 'Miller', 'david.miller@example.com', '555-1111', 'Computer Science', 'Professor', 'ACTIVE'),
    ('T2002', 'Eva', 'Wilson', 'eva.wilson@example.com', '555-2222', 'Mathematics', 'Associate Professor', 'ACTIVE'),
    ('T2003', 'Frank', 'Davis', 'frank.davis@example.com', '555-3333', 'Physics', 'Lecturer', 'ACTIVE'),
    ('T2004', 'Grace', 'Thomas', 'grace.thomas@example.com', '555-4445', 'Computer Science', 'Assistant Professor', 'ACTIVE'),
    ('T2005', 'Henry', 'White', 'henry.white@example.com', '555-5556', 'Mathematics', 'Professor', 'ON_LEAVE');

-- ==== COURSES ====
-- (Yeni şemaya göre start_date ve end_date zorunlu)
INSERT INTO courses (course_code, name, credit, department, start_date, end_date)
VALUES
    ('CS101', 'Introduction to Programming', 4, 'Computer Science', '2025-02-01', '2025-06-15'),
    ('CS201', 'Data Structures', 5, 'Computer Science', '2025-02-01', '2025-06-15'),
    ('MATH201', 'Linear Algebra', 3, 'Mathematics', '2025-02-01', '2025-06-15'),
    ('MATH301', 'Probability Theory', 4, 'Mathematics', '2025-02-01', '2025-06-15'),
    ('PHYS301', 'Quantum Mechanics', 5, 'Physics', '2025-02-01', '2025-06-15'),
    ('PHYS401', 'Thermodynamics', 4, 'Physics', '2025-02-01', '2025-06-15');

-- ==== ENROLLMENTS ====
INSERT INTO enrollments (student_id, course_id, grade, status)
VALUES
    (1, 1, 4.0, 'COMPLETED'),
    (1, 2, 3.5, 'COMPLETED'),
    (1, 3, NULL, 'ENROLLED'),
    (2, 3, 3.0, 'COMPLETED'),
    (2, 4, 2.5, 'COMPLETED'),
    (3, 5, NULL, 'ENROLLED'),
    (3, 6, 1.5, 'FAILED'),
    (4, 1, 3.0, 'COMPLETED'),
    (4, 2, 2.0, 'COMPLETED'),
    (5, 3, 1.0, 'FAILED');

-- ==== TEACHER ASSIGNMENTS ====
INSERT INTO teacher_assignments (teacher_id, course_id, role)
VALUES
    (1, 1, 'INSTRUCTOR'),
    (4, 2, 'INSTRUCTOR'),
    (2, 3, 'INSTRUCTOR'),
    (5, 4, 'INSTRUCTOR'),
    (3, 5, 'INSTRUCTOR'),
    (3, 6, 'INSTRUCTOR');

-- ==== SYSTEM LOGS ====
INSERT INTO system_logs (log_level, message, logger, thread)
VALUES
    ('INFO', 'System initialized successfully', 'SystemInit', 'main'),
    ('WARN', 'Student S1002 has incomplete profile', 'StudentService', 'worker-1'),
    ('ERROR', 'Failed to assign teacher to course', 'TeacherAssignmentService', 'worker-2'),
    ('INFO', 'New course CS201 created successfully', 'CourseService', 'main'),
    ('INFO', 'Enrollment completed for student S1004 in CS101', 'EnrollmentService', 'worker-3');
