package Home9core;
import java.util.Arrays;
import java.util.List;
import static Home9core.Student.*;

public class Study {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Ваня",
                        Arrays.asList(
                                new Course("Английский"),
                                new Course("Математика"),
                                new Course("Философия"),
                                new Course("Геометрия"),
                                new Course("Экономика")
                        )),
                new Student("Антон",
                        Arrays.asList(
                                new Course("Физика"),
                                new Course("Английский"),
                                new Course("Геометрия"),
                                new Course("Математическая логика")
                        )),
                new Student("Саша",
                        Arrays.asList(
                                new Course("Физика"),
                                new Course("Геометрия"),
                                new Course("Математическая логика")
                        )),
                new Student("Анна",
                        Arrays.asList(
                                new Course("Геометрия"),
                                new Course("Матиматическая логика")
                        ))
        );
        Course mathLogic = new Course("Матиматическая логика");

        System.out.println("Список уникальных курсов: " + getUniqueCourses(students));
        System.out.println("Список трёх самых любознательных студентов: "
                + getListOfThirstyForKnowledgeStudent(students, 3));
        System.out.println("Список студентов, посещающих курс \"Матиматическая логика\": " + getListOfStudentVisitingCourse(students, mathLogic));
    }
}
