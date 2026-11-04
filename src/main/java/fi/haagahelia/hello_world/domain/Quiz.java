package fi.haagahelia.hello_world.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String courseCode;

    private boolean published = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Later, when the Question entity exists, add:
    // @JsonIgnore
    // @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // private List<Question> questions = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(String name, String description, String courseCode, boolean published) {
        this.name = name;
        this.description = description;
        this.courseCode = courseCode;
        this.published = published;
    }

    // Sets the date automatically when the quiz is saved for the first time
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Quiz [id=" + id + ", name=" + name + ", courseCode=" + courseCode
                + ", published=" + published + ", createdAt=" + createdAt + "]";
    }
}