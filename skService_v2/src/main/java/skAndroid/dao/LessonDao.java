package skAndroid.dao;

import skAndroid.domain.Tbllesson;

import java.util.List;

/**
 * Created by khangtnse60992 on 10/17/2014.
 */
public interface LessonDao {
    public List<Tbllesson> getListSessonByCourseId(int id, int page);
    public Tbllesson getLessonById(Integer idCourse, Integer lessonNumber);
    public List<Integer> getNumberLesson(int idCourse);
}
