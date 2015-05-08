package skAndroid.dao;

import org.springframework.stereotype.Repository;
import skAndroid.domain.Tblcourse;

import java.util.List;

/**
 * Created by khangtnse60992 on 10/17/2014.
 */
@Repository
public interface CourseDao {
    public List<Tblcourse> getUserCourses(int userId, int page);
    public List<Tblcourse> getAllCourses(int page);
    public Integer getNumberAllCourses();
    public Integer getNumberUserCourse(int userId);
    public List<Tblcourse> getCourseSearch(String keyword,int page);
    public int getNumberCourseSearch(String keyword);
}
