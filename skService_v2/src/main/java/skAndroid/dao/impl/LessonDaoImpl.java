package skAndroid.dao.impl;

import org.springframework.stereotype.Repository;
import skAndroid.dao.BaseDao;
import skAndroid.dao.LessonDao;
import skAndroid.domain.Tbllesson;
import skAndroid.util.Constant;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Repository
public class LessonDaoImpl extends BaseDao<Tbllesson,Integer> implements LessonDao {

    public List<Tbllesson> getListSessonByCourseId(int id,int page) {
        Query query = null;
        query = entityManager.createQuery("select a from Tbllesson a where a.courseId.id = :id");
        query.setParameter("id",id);
        if (page < 1) {
            page = 1;
        }
        query.setFirstResult((page-1)* Constant.numberPage);
        query.setMaxResults(Constant.numberPage+1);
        return query.getResultList();
    }

    public Tbllesson getLessonById(Integer idCourse,Integer lessonNumber) {
        Query query = null;
        if(lessonNumber!=null) {
        query = entityManager.createQuery("select a from Tbllesson a where a.courseId.id = :idCourse and a.lessonNumber = :lessonNumber");
        query.setParameter("lessonNumber",lessonNumber) ;
        } else {
        query = entityManager.createQuery("select a from Tbllesson a where a.courseId.id = :idCourse ORDER BY lessonNumber ASC");
        query.setMaxResults(1);
        }
        query.setParameter("idCourse",idCourse);
        return (Tbllesson) query.getSingleResult();
    }

    public List<Integer> getNumberLesson(int idCourse) {
        Query query = null;
        query = entityManager.createQuery("select a.lessonNumber from Tbllesson a where a.courseId.id = :idCourse order by lessonNumber");
        query.setParameter("idCourse",idCourse);
        return  query.getResultList();
    }

    public boolean checkLessonNumber(int idCourse,int num) {
        Query query = null;
        query = entityManager.createQuery("select a.lessonNumber from Tbllesson a where a.courseId.id = :idCourse and a.lessonNumber = :num");
        query.setParameter("idCourse",idCourse);
        query.setParameter("num",num);
        List<Tbllesson> tbllessons = query.getResultList();
        try {
            if(0!=tbllessons.size()){
                return false;
            }
        } catch (Exception e){
            System.out.println("Log :" + e.getMessage());
        }
        return true;
    }
    public boolean checkLessonNumberUpdate(int courseId,int lessonId,int num) {
        Query query = null;
        query = entityManager.createQuery("select a.lessonNumber from Tbllesson a where a.courseId.id = :idCourse and a.lessonNumber = :num and a.id != :lessonId");
        query.setParameter("idCourse",courseId);
        query.setParameter("lessonId",lessonId);
        query.setParameter("num",num);
        List<Tbllesson> tbllessons = query.getResultList();
        try {
            if(0!=tbllessons.size()){
                return false;
            }
        } catch (Exception e){
            System.out.println("Log :" + e.getMessage());
        }
        return true;
    }
}
