package ru.innopolis.university.stc27.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usrs", uniqueConstraints =
@UniqueConstraint(columnNames = "login", name = "unique_login"))
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50)
    private String name;

    @Column(name = "login", nullable = false, length = 20)
    private String login;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "email", nullable = false, length = 50)
    private String mail;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Column(name="active")
    private Boolean active;

    @Column(name="code")
    private String code;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
    private Set<Income> incomes;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "user")
    private Set<Expense> expenses;

    public User(String name, String login, String password, String mail) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.active = false;
    }

    public User() {}

    public boolean isUser(){
        return roles.contains(Role.USER);
    }

    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return name;
    }

    public void setUserName(String userName) {
        this.name = userName;
    }

    public String getUserLogin() {
        return login;
    }

    public void setUserLogin(String userLogin) {
        this.login = userLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
