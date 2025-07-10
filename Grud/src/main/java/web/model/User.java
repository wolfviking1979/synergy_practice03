package web.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class User {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 to 30")
    private String name;
    @Column(name = "sur_name")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 to 30")
    private String surName;
    @Column(name = "age")
    private int age;
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
    @Column(name = "num_order")
    @NotEmpty(message = "Order should not be empty")
    private String order;
    public User() {
    }

    public User(String name, String surName, int age, String email, String order) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.email = email;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 30,
            message = "Name should be between 2 to 30") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name should not be empty") @Size(min = 2, max = 30,
            message = "Name should be between 2 to 30") String name) {
        this.name = name;
    }

    public @Size(min = 2, max = 30, message = "Name should be between 2 to 30") String getSurName() {
        return surName;
    }

    public void setSurName(@Size(min = 2, max = 30, message = "Name should be between 2 to 30") String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return String.format("User [id = %d; name = %s; surname = %s; age = %s; email = %s; order = %s]",
                id, name, surName, age, email, order);
    }
}
