package spittr.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ChenZhePC on 2017/1/16.
 */

public class Spitter {
    private Long id;

    @NotNull
    @Size(min = 5, max = 16, message = "{spitter.username.size}")
    private String username;

    @NotNull
    @Size(min = 5, max = 25, message = "{spitter.password.size}")
    private String password;

    @NotNull
    @Size(min = 2, max = 30, message = "{spitter.firstName.size}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{spitter.lastName.size}")
    private String lastName;

    @NotNull
    @Email
    private String email;

    public Spitter() {}

    public Spitter(String username, String password, String firstName, String lastName, String email) {
        this(null, username, password, firstName, lastName, email);
    }

    public Spitter(Long id, String username, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj,
                "firstName", "lastName", "username", "password", "email");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,
                "firstName", "lastName", "username", "password", "email");
    }
}
