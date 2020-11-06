package ru.innopolis.university.stc27.domain;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "category_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 50)
    private String name;

    @Column(name = "category_type", nullable = false)
    private Boolean type;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Category() {
    }

    public Category(String name, Boolean type, Long user_id) {
        this.name = name;
        this.type = type;
        this.userId = user_id;
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
