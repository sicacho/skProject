package skAndroid.dao;

import skAndroid.domain.Tbllessondetail;

import java.util.List;

/**
 * Created by khangtnse60992 on 10/17/2014.
 */
public interface LessonDetailDao {
    public List<Tbllessondetail> getLessonDetailByLessonId(int lessonId);
}
