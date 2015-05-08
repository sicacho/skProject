package skAndroid.dao.impl;

import org.springframework.stereotype.Repository;
import skAndroid.dao.BaseDao;
import skAndroid.dao.UserDao;
import skAndroid.domain.Tblcourse;
import skAndroid.domain.Tbluser;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created by khangtnse60992 on 1/20/2015.
 */
@Repository
public class UserDaoImpl extends BaseDao<Tbluser, Integer> implements UserDao {

    @Override
    public Tbluser checkLogin(String username, String password) {
        Query query = null;
        query = entityManager.createQuery("select u from Tbluser u where u.username=:username and u.password=:password", Tbluser.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Tbluser tbluser = null;
        try {
            tbluser = (Tbluser) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("User null");
        }
        return tbluser;
    }


}
