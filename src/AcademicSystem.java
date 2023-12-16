import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Student {
    String name;
    String studentId;

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }
}

class Course {
    String courseId;
    String courseName;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }
}

class AcademicRecord {
    Student student;
    Course course;
    int grade;

    public AcademicRecord(Student student, Course course, int grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }
}

public class AcademicSystem {
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<AcademicRecord> academicRecords = new ArrayList<>();

    private static final String STUDENTS_FILE = "students.csv";
    private static final String COURSES_FILE = "courses.csv";
    private static final String RECORDS_FILE = "academic_records.csv";

    public static void main(String[] args) {
        loadStudents();
        loadCourses();
        loadAcademicRecords();

        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Студенти:");
        showStudents();
        System.out.println("----------------------------");
        System.out.println("Курси:");
        showCourses();
        while (true) {

            System.out.println("-------- Зробіть вибір ------");
            System.out.println("1. Додати студента");
            System.out.println("2. Додати курс");
            System.out.println("3. Додати академічний запис");
            System.out.println("4. Показати всі записи");
            System.out.println("5. Видалити студента");
            System.out.println("6. Видалити курс");
            System.out.println("7. Видалити академічний запис");
            System.out.println("0. Вийти");
            System.out.println("----------------------------");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addCourse();
                    break;
                case 3:
                    addAcademicRecord();
                    break;
                case 4:
                    showAllRecords();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    deleteCourse();
                    break;
                case 7:
                    deleteAcademicRecord();
                    break;
                case 0:
                    saveStudents();
                    saveCourses();
                    saveAcademicRecords();
                    System.out.println("Дякую за використання програми. До побачення!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Введіть ім'я студента:");
        String name = scanner.nextLine();
        System.out.println("Введіть номер студентського квитка:");
        String studentId = scanner.nextLine();
        Student student = new Student(name, studentId);
        students.add(student);
        System.out.println("Студент доданий успішно!");
        saveStudents();
    }

    private static void addCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Введіть назву курсу:");
        String courseName = scanner.nextLine();
        System.out.println("Введіть ідентифікатор курсу:");
        String courseId = scanner.nextLine();
        Course course = new Course(courseId, courseName);
        courses.add(course);
        System.out.println("Курс доданий успішно!");
        saveCourses();
    }

    private static void addAcademicRecord() {
        if (students.isEmpty() || courses.isEmpty()) {
            System.out.println("Додайте хоча б одного студента та один курс перед додаванням академічного запису.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Оберіть студента:");
        showStudents();
        int studentIndex = scanner.nextInt() - 1;
        if (studentIndex < 0 || studentIndex >= students.size()) {
            System.out.println("Невірний індекс студента.");
            return;
        }
        Student selectedStudent = students.get(studentIndex);

        System.out.println("Оберіть курс:");
        showCourses();
        int courseIndex = scanner.nextInt() - 1;
        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("Невірний індекс курсу.");
            return;
        }
        Course selectedCourse = courses.get(courseIndex);

        System.out.println("Введіть оцінку:");
        int grade = scanner.nextInt();

        AcademicRecord academicRecord = new AcademicRecord(selectedStudent, selectedCourse, grade);
        academicRecords.add(academicRecord);
        System.out.println("Академічний запис доданий успішно!");
        saveAcademicRecords();
    }

    private static void deleteStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Введіть номер студентського квитка для видалення:");
        String studentId = scanner.nextLine();

        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.studentId.equals(studentId)) {
                iterator.remove();
                System.out.println("Студент видалений успішно!");
                saveStudents();
                return;
            }
        }
        System.out.println("Студент з номером " + studentId + " не знайдений.");
    }

    private static void deleteCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Введіть ідентифікатор курсу для видалення:");
        String courseId = scanner.nextLine();

        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.courseId.equals(courseId)) {
                iterator.remove();
                System.out.println("Курс видалений успішно!");
                saveCourses();
                return;
            }
        }
        System.out.println("Курс з ідентифікатором " + courseId + " не знайдений.");
    }

    private static void deleteAcademicRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Введіть індекс академічного запису для видалення:");
        showAllRecords();
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < academicRecords.size()) {
            academicRecords.remove(index);
            System.out.println("Академічний запис видалений успішно!");
            saveAcademicRecords();
        } else {
            System.out.println("Невірний індекс академічного запису.");
        }
    }

    private static void showStudents() {
        System.out.println("----------------------------");
        System.out.println("Список студентів:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).name);
        }
    }

    private static void showCourses() {
        System.out.println("----------------------------");
        System.out.println("Список курсів:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).courseName);
        }
    }

    private static void showAllRecords() {
        if (academicRecords.isEmpty()) {
            System.out.println("----------------------------");
            System.out.println("Немає академічних записів.");
            return;
        }

        System.out.println("----------------------------");
        System.out.println("Всі академічні записи:");
        for (int i = 0; i < academicRecords.size(); i++) {
            AcademicRecord record = academicRecords.get(i);
            String studentName = (record.student != null) ? record.student.name : "Unknown";
            String courseName = (record.course != null) ? record.course.courseName : "Unknown";
            System.out.println((i + 1) + ". Студент: " + studentName +
                    ", Курс: " + courseName +
                    ", Оцінка: " + record.grade);
        }
    }

    private static void loadStudents() {
        loadFromFile(STUDENTS_FILE, students, Student.class);
    }

    private static void loadCourses() {
        loadFromFile(COURSES_FILE, courses, Course.class);
    }

    private static void loadAcademicRecords() {
        loadFromFile(RECORDS_FILE, academicRecords, AcademicRecord.class);
    }

    private static <T> void loadFromFile(String fileName, List<T> data, Class<T> type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    if (type.equals(Student.class)) {
                        Student student = new Student(parts[0], parts[1]);
                        data.add(type.cast(student));
                    } else if (type.equals(Course.class)) {
                        Course course = new Course(parts[0], parts[1]);
                        data.add(type.cast(course));
                    } else if (type.equals(AcademicRecord.class)) {
                        Student student = findStudentById(parts[0]);
                        Course course = findCourseById(parts[1]);
                        int grade = Integer.parseInt(parts[2]);
                        AcademicRecord academicRecord = new AcademicRecord(student, course, grade);
                        data.add(type.cast(academicRecord));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveStudents() {
        saveToFile(STUDENTS_FILE, students);
    }

    private static void saveCourses() {
        saveToFile(COURSES_FILE, courses);
    }

    private static void saveAcademicRecords() {
        saveToFile(RECORDS_FILE, academicRecords);
    }

    private static <T> void saveToFile(String fileName, List<T> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (T item : data) {
                if (item instanceof Student) {
                    Student student = (Student) item;
                    writer.println(student.name + "," + student.studentId);
                } else if (item instanceof Course) {
                    Course course = (Course) item;
                    writer.println(course.courseId + "," + course.courseName);
                } else if (item instanceof AcademicRecord) {
                    AcademicRecord record = (AcademicRecord) item;
                    writer.println(record.student.studentId + "," +
                            record.course.courseId + "," +
                            record.grade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.studentId.equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.courseId.equals(courseId)) {
                return course;
            }
        }
        return null;
    }
}
