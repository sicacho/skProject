package skAndroid.dao.impl;

import org.springframework.stereotype.Repository;
import skAndroid.dao.BaseDao;
import skAndroid.dao.CourseDao;
import skAndroid.domain.Tblcourse;
import skAndroid.util.Constant;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Repository
public class CourseDaoImpl extends BaseDao<Tblcourse, Integer> implements CourseDao {

    @Override
    public List<Tblcourse> getUserCourses(int userId,int page) throws NoResultException {
        Query query = null;
        query = entityManager.createQuery("select t from Tblcourse t where userId.id = :userId order by id", Tblcourse.class);
        query.setParameter("userId", userId);
        if (page < 1) {
            page = 1;
        }
        query.setFirstResult((page-1)* Constant.numberPage);
        query.setMaxResults(Constant.numberPage+1);
        return query.getResultList();
    }

    @Override
    public List<Tblcourse> getCourseSearch(String keyword,int page) throws NoResultException {
        Query query = null;
        query = entityManager.createQuery("select t from Tblcourse t where t.alias like :keyword", Tblcourse.class);
        query.setParameter("keyword", "%"+keyword+"%");
        if (page < 1) {
            page = 1;
        }
        query.setFirstResult((page-1)* Constant.numberPage);
        query.setMaxResults(Constant.numberPage+1);
        return query.getResultList();
    }

    @Override
    public int getNumberCourseSearch(String keyword) throws NoResultException {
        Query query = null;
        query = entityManager.createQuery("select count(t) from Tblcourse t where t.alias like :keyword");
        query.setParameter("keyword", "%"+keyword+"%");
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public List<Tblcourse> getAllCourses(int page) throws NoResultException {
        Query query = null;
        query = entityManager.createQuery("select t from Tblcourse t order by id asc", Tblcourse.class);
        if (page < 1) {
            page = 1;
        }
        query.setFirstResult((page-1)* Constant.numberPage);
        query.setMaxResults(Constant.numberPage+1);
        return query.getResultList();
    }

    @Override
    public Integer getNumberAllCourses() throws NoResultException {
        Query query = null;
        query = entityManager.createQuery("select count(*) from Tblcourse ");
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public Integer getNumberUserCourse(int userId) {
        Query query = null;
        query = entityManager.createQuery("select count(t) from Tblcourse t where userId.id = :userId");
        query.setParameter("userId", userId);
        return ((Long)query.getSingleResult()).intValue();
    }
    public Tblcourse checkCourseOwner(int userId,int courseId) {
        Query query = null;
        query = entityManager.createQuery("select c from  Tblcourse c where c.userId.id = :userId and c.id = :courseId", Tblcourse.class);
        query.setParameter("userId",userId);
        query.setParameter("courseId",courseId);
        Tblcourse tblcourse = null;
        try {
            tblcourse = (Tblcourse) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("User null");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tblcourse;
    }

}
