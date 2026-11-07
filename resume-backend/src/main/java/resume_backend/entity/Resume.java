
package resume_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String email;
    private String linkedin;
    private String github;

    private String class10;
    private String board10;
    private String year10;

    private String class12;
    private String board12;
    private String year12;

    private String degree;
    private String college;
    private String graduationYear;

    @Column(columnDefinition = "LONGTEXT")
    private String skills;

    @Column(columnDefinition = "LONGTEXT")
    private String experience;

    @Column(columnDefinition = "LONGTEXT")
    private String projects;

    @Column(columnDefinition = "LONGTEXT")
    private String achievements;

    public Resume() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }


    public String getClass10() {
        return class10;
    }

    public void setClass10(String class10) {
        this.class10 = class10;
    }

    public String getBoard10() {
        return board10;
    }

    public void setBoard10(String board10) {
        this.board10 = board10;
    }

    public String getYear10() {
        return year10;
    }

    public void setYear10(String year10) {
        this.year10 = year10;
    }

    public String getClass12() {
        return class12;
    }

    public void setClass12(String class12) {
        this.class12 = class12;
    }

    public String getBoard12() {
        return board12;
    }

    public void setBoard12(String board12) {
        this.board12 = board12;
    }

    public String getYear12() {
        return year12;
    }

    public void setYear12(String year12) {
        this.year12 = year12;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }
}
