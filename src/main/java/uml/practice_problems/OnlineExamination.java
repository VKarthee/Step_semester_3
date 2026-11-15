
import java.util.*;

abstract class Question {

    protected String questionText;
    protected String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public abstract boolean evaluate(String answer);
}

class MCQQuestion extends Question {

    public MCQQuestion(String text, String answer) {
        super(text, answer);
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String text, String answer) {
        super(text, answer);
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Examination {

    private String examName;
    private List<Question> questions = new ArrayList<>();

    public Examination(String examName) {
        this.examName = examName;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public String getExamName() {
        return examName;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Student {

    private String name;
    private Map<String, Attempt> attempts = new HashMap<>();

    public Student(String name) {
        this.name = name;
    }

    public Attempt startExam(Examination exam) {
        if (attempts.containsKey(exam.getExamName())) {
            System.out.println("Attempt already exists!");
            return null;
        }

        Attempt attempt = new Attempt(this, exam);
        attempts.put(exam.getExamName(), attempt);

        System.out.println("Examination '"
                + exam.getExamName()
                + "' started by " + name + ".");

        return attempt;
    }
}

class Attempt {

    private Student student;
    private Examination exam;
    private Map<Integer, String> answers = new HashMap<>();
    private boolean submitted = false;

    public Attempt(Student student, Examination exam) {
        this.student = student;
        this.exam = exam;
    }

    public void answerQuestion(int number, String answer) {
        if (submitted) {
            System.out.println("Cannot modify submitted attempt.");
            return;
        }

        if (number < 1 || number > exam.getQuestions().size()) {
            System.out.println("Invalid question number.");
            return;
        }

        answers.put(number - 1, answer);

        System.out.println("Question " + number
                + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) {
            System.out.println("Attempt already submitted.");
            return;
        }

        submitted = true;

        System.out.println("Examination '"
                + exam.getExamName()
                + "' submitted successfully.");

        evaluate();
    }

    private void evaluate() {
        int score = 0;

        for (int i = 0; i < exam.getQuestions().size(); i++) {
            String answer = answers.get(i);

            if (answer != null
                    && exam.getQuestions().get(i).evaluate(answer)) {
                score++;
            }
        }

        System.out.println("Result for '"
                + exam.getExamName()
                + "' attempt: " + score + "/"
                + exam.getQuestions().size() + " correct.");
    }
}

public class OnlineExamination {

    public static void main(String[] args) {

        Student student = new Student("John");

        Examination exam = new Examination("Math Quiz");

        exam.addQuestion(new MCQQuestion(
                "What is 2 + 2?", "A"));

        exam.addQuestion(new MCQQuestion(
                "What is 5 + 5?", "B"));

        Attempt attempt = student.startExam(exam);

        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        attempt.submit();

        attempt.answerQuestion(1, "B");
    }
}
