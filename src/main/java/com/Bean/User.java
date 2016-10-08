package com.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1233175066212740985L;

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthday;
    @Lob
    private char[] password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<File> files = new HashSet<>();

    public User() {
    }

    public User(String name, String email, String birthday) {
        this(null, name, email, birthday);
    }

    public User(Long id, String name, String email, String birthday) {
        this(id, name, email, birthday, null);
    }

    public User(Long id, String name, String email, String birthday, char[] password) {
        this.id = id;
        this.name = name;
        this.email = email;
        if (birthday == null || birthday.isEmpty()) {
            this.birthday = null;
        } else {
            this.birthday = LocalDate.parse(birthday);
        }
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public void addFiles() {
        if (!files.isEmpty()) {
            for (File file : files) {
                if (!file.getFileContents().isEmpty()) {
                    for (FileContent content : file.getFileContents()) {
                        content.setFile(file);
                    }
                }
                file.setUser(this);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User rhs = (User) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.getEmail(), rhs.getEmail())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(getEmail())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("name", name)
                .append("email", email)
                .append("birthday", birthday)
                .toString();
    }
}
