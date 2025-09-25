CREATE OR REPLACE VIEW student_avg_grade AS
SELECT
    s.id AS student_id,
    s.first_name,
    s.last_name,
    ROUND(SUM(e.grade * c.credit) / NULLIF(SUM(c.credit), 0), 2) AS avg_grade
FROM students s
         LEFT JOIN enrollments e ON s.id = e.student_id
         LEFT JOIN courses c ON e.course_id = c.id
WHERE e.grade IS NOT NULL
GROUP BY s.id, s.first_name, s.last_name;

-- === Teacher-Course Average Grade View ===
CREATE OR REPLACE VIEW teacher_course_avg_grade AS
SELECT
    t.id AS teacher_id,
    t.first_name,
    t.last_name,
    c.id AS course_id,
    c.name AS course_name,
    ROUND(AVG(e.grade)::numeric, 2) AS avg_grade
FROM teachers t
         JOIN teacher_assignments ta ON ta.teacher_id = t.id
         JOIN courses c ON c.id = ta.course_id
         LEFT JOIN enrollments e ON e.course_id = c.id
GROUP BY t.id, t.first_name, t.last_name, c.id, c.name;


CREATE OR REPLACE VIEW course_avg_grade AS
SELECT
    c.id AS course_id,
    c.course_code,
    c.name AS course_name,
    c.credit,
    c.department,
    ROUND(AVG(e.grade)::numeric, 2) AS avg_grade,
    COUNT(*) FILTER (WHERE e.grade < 2.0) AS failure_count
FROM courses c
         LEFT JOIN enrollments e ON e.course_id = c.id
GROUP BY c.id, c.course_code, c.name, c.credit, c.department;
