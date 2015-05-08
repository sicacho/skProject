package skAndroid.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Entity
public class Tblcourse {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;
    @Column(name = "detail")
    @Lob
    private String detail;
    @JoinColumn(name = "userID",referencedColumnName = "id")
//    @JoinColumn(name = "userID")
    @ManyToOne
    @JsonBackReference
    private Tbluser userId;
    @Basic
    @Column(name = "courseName")
    private String courseName;
    @Basic
    @Column(name = "alias")
    private String alias;
    @OneToMany(mappedBy = "courseId",fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @JsonManagedReference
    private List<Tbllesson> lesson = new ArrayList<Tbllesson>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Tbluser getUserId() {
        return userId;
    }

    public void setUserId(Tbluser userId) {
        this.userId = userId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<Tbllesson> getLesson() {
        return lesson;
    }

    public void setLesson(List<Tbllesson> lesson) {
        this.lesson = lesson;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tblcourse tblcourse = (Tblcourse) o;

        if (id != tblcourse.id) return false;
        if (userId != tblcourse.userId) return false;
        if (courseName != null ? !courseName.equals(tblcourse.courseName) : tblcourse.courseName != null) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + userId;
//        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
//        return result;
//    }
}
