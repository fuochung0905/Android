package model;

public class Student {
    private int Id;
    private String studentName;
    private String className;
    private double totalScore;
    private byte[] image;

    public Student() {
    }

    public Student(int id, String studentName, String className, double totalScore, byte[] image) {
        Id = id;
        this.studentName = studentName;
        this.className = className;
        this.totalScore = totalScore;
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
