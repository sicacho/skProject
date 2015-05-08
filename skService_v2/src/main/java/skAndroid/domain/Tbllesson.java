package skAndroid.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Entity
public class Tbllesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "courseID" ,referencedColumnName = "id")
    @ManyToOne
    @JsonBackReference
    private Tblcourse courseId;
    @Basic
    @Column(name = "lessonName")
    private String lessonName;
    @Basic
    @Column(name = "lessonNumber")
    private int lessonNumber;
    @OneToMany(mappedBy = "lessonId",fetch = FetchType.EAGER,orphanRemoval=true)
    @Cascade(CascadeType.ALL)
    private List<Tbllessondetail> listLessonDetail = new ArrayList<Tbllessondetail>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Tblcourse getCourseId() {
        return courseId;
    }

    public void setCourseId(Tblcourse courseId) {
        this.courseId = courseId;
    }


    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }


    public List<Tbllessondetail> getListLessonDetail() {
        return listLessonDetail;
    }

    public void setListLessonDetail(List<Tbllessondetail> listLessonDetail) {
        this.listLessonDetail = listLessonDetail;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tbllesson tbllesson = (Tbllesson) o;

        if (id != tbllesson.id) return false;
        if (courseId != null ? !courseId.equals(tbllesson.courseId) : tbllesson.courseId != null) return false;
        if (lessonName != null ? !lessonName.equals(tbllesson.lessonName) : tbllesson.lessonName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (lessonName != null ? lessonName.hashCode() : 0);
        return result;
    }
}
