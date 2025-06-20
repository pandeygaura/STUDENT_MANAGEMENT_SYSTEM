import java.util.Scanner;

class Student {
    private String name;
    private int rollNumber;
    private String dob;
    private String email;
    private String studentId;
    private String course;

    public Student(String name, int rollNumber, String dob, String email, String studentId, String course) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.dob = dob;
        this.email = email;
        this.studentId = studentId;
        this.course = course;
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getDob() { return dob; }
    public String getEmail() { return email; }
    public String getStudentId() { return studentId; }
    public String getCourse() { return course; }

    public void displayDetails() {
        System.out.println("\nStudent Details:");
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Email: " + email);
        System.out.println("Student ID: " + studentId);
        System.out.println("Course: " + course);
    }
}

class Subject {
    private String name;
    private int marks;
    private String grade;
    private double percentage;

    public Subject(String name, int marks) {
        this.name = name;
        this.marks = marks;
        calculateGradeAndPercentage();
    }

    private void calculateGradeAndPercentage() {
        this.percentage = (marks / 100.0) * 100;

        if (marks >= 90) grade = "A+";
        else if (marks >= 80) grade = "A";
        else if (marks >= 70) grade = "B+";
        else if (marks >= 60) grade = "B";
        else if (marks >= 50) grade = "C";
        else if (marks >= 40) grade = "D";
        else grade = "F";
    }

    public String getName() { return name; }
    public int getMarks() { return marks; }
    public String getGrade() { return grade; }
    public double getPercentage() { return percentage; }

    public void displaySubjectDetails() {
        System.out.println("\nSubject: " + name);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
        System.out.println("Percentage: " + percentage + "%");
    }
}

class Examination {
    private Subject[] subjects;
    private int subjectCount;
    private double overallPercentage;
    private String overallGrade;

    public Examination() {
        subjects = new Subject[5]; // Fixed size for 5 subjects
        subjectCount = 0;
    }

    public void addSubject(Subject subject) {
        if (subjectCount < 5) {
            subjects[subjectCount] = subject;
            subjectCount++;
            calculateOverallPerformance();
        }
    }

    private void calculateOverallPerformance() {
        double totalMarks = 0;
        for (int i = 0; i < subjectCount; i++) {
            totalMarks += subjects[i].getMarks();
        }
        overallPercentage = (totalMarks / (subjectCount * 100)) * 100;

        if (overallPercentage >= 90) overallGrade = "A+";
        else if (overallPercentage >= 80) overallGrade = "A";
        else if (overallPercentage >= 70) overallGrade = "B+";
        else if (overallPercentage >= 60) overallGrade = "B";
        else if (overallPercentage >= 50) overallGrade = "C";
        else if (overallPercentage >= 40) overallGrade = "D";
        else overallGrade = "F";
    }

    public void displayExamResults() {
        System.out.println("\n--- Examination Results ---");
        for (int i = 0; i < subjectCount; i++) {
            subjects[i].displaySubjectDetails();
        }
        System.out.println("\nOverall Performance:");
        System.out.println("Percentage: " + overallPercentage + "%");
        System.out.println("Grade: " + overallGrade);
    }
}

public class StudentManagementGUI {
    private static Student[] students = new Student[100]; // Max 100 students
    private static Examination[] examinations = new Examination[100]; // Parallel array to students
    private static int studentCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add New Student");
            System.out.println("2. Add Examination Details");
            System.out.println("3. View Student Details");
            System.out.println("4. View Examination Results");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addExaminationDetails();
                    break;
                case 3:
                    viewStudentDetails();
                    break;
                case 4:
                    viewExaminationResults();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent() {
        if (studentCount >= students.length) {
            System.out.println("Maximum student capacity reached!");
            return;
        }

        System.out.println("\nEnter Student Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Roll Number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Date of Birth (DD/MM/YYYY): ");
        String dob = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Course: ");
        String course = scanner.nextLine();

        students[studentCount] = new Student(name, rollNumber, dob, email, studentId, course);
        studentCount++;
        System.out.println("Student added successfully!");
    }

    private static void addExaminationDetails() {
        if (studentCount == 0) {
            System.out.println("No students available. Please add a student first.");
            return;
        }

        System.out.print("Enter student roll number to add examination details: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int studentIndex = findStudentIndexByRollNumber(rollNumber);
        if (studentIndex == -1) {
            System.out.println("Student not found with roll number: " + rollNumber);
            return;
        }

        Examination examination = new Examination();

        System.out.println("\nEnter marks for 5 subjects (out of 100):");
        for (int i = 1; i <= 5; i++) {
            System.out.print("Subject " + i + " name: ");
            String subjectName = scanner.nextLine();

            System.out.print("Marks for " + subjectName + ": ");
            int marks = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (marks < 0 || marks > 100) {
                System.out.println("Invalid marks. Please enter between 0-100.");
                i--; // Retry this subject
                continue;
            }

            examination.addSubject(new Subject(subjectName, marks));
        }

        examinations[studentIndex] = examination;
        System.out.println("\nExamination details added successfully for " + students[studentIndex].getName());
        examination.displayExamResults();
    }

    private static void viewStudentDetails() {
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.print("Enter student roll number to view details: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int studentIndex = findStudentIndexByRollNumber(rollNumber);
        if (studentIndex == -1) {
            System.out.println("Student not found with roll number: " + rollNumber);
            return;
        }

        students[studentIndex].displayDetails();
    }

    private static void viewExaminationResults() {
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.print("Enter student roll number to view examination results: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int studentIndex = findStudentIndexByRollNumber(rollNumber);
        if (studentIndex == -1) {
            System.out.println("Student not found with roll number: " + rollNumber);
            return;
        }

        if (examinations[studentIndex] == null) {
            System.out.println("No examination results found for " + students[studentIndex].getName());
            return;
        }

        System.out.println("\nExamination results for " + students[studentIndex].getName() + ":");
        examinations[studentIndex].displayExamResults();
    }

    private static int findStudentIndexByRollNumber(int rollNumber) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getRollNumber() == rollNumber) {
                return i;
            }
        }
        return -1;
    }
}