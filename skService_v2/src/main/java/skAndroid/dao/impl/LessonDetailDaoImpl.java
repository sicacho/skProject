package skAndroid.dao.impl;

import org.springframework.stereotype.Repository;
import skAndroid.dao.BaseDao;
import skAndroid.dao.LessonDetailDao;
import skAndroid.domain.Tbllessondetail;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */

@Repository
public class LessonDetailDaoImpl extends BaseDao<Tbllessondetail,Integer> implements LessonDetailDao {

    @Override
    public List<Tbllessondetail> getLessonDetailByLessonId(int lessonId) throws NoResultException {
        Query query = null;
        query = entityManager.createQuery("select t from Tbllessondetail t where lessonId.id = :lessonId order by id",Tbllessondetail.class);
        query.setParameter("lessonId",lessonId);
        return query.getResultList();
    }
}
