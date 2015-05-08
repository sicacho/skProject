package skAndroid.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Entity
public class Tbluser {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "userId")
    @JsonManagedReference
    private List<Tblcourse> listCourse = new ArrayList<Tblcourse>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Tblcourse> getListCourse() {
        return listCourse;
    }

    public void setListCourse(List<Tblcourse> listCourse) {
        this.listCourse = listCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tbluser tbluser = (Tbluser) o;

        if (id != tbluser.id) return false;
        if (email != null ? !email.equals(tbluser.email) : tbluser.email != null) return false;
        if (password != null ? !password.equals(tbluser.password) : tbluser.password != null) return false;
        if (username != null ? !username.equals(tbluser.username) : tbluser.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public Tbluser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Tbluser() {
    }
}
