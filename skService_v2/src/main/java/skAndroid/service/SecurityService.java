package skAndroid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skAndroid.dao.impl.CourseDaoImpl;
import skAndroid.dao.impl.UserDaoImpl;
import skAndroid.domain.Tblcourse;
import skAndroid.domain.Tbluser;

/**
 * Created by khangtnse60992 on 3/15/2015.
 */
@Service
public class SecurityService {
    @Autowired
    CourseDaoImpl courseDao;
    public boolean authorize(int courseId,int userId) {
        Tblcourse tblcourse = courseDao.checkCourseOwner(userId,courseId);
        if(tblcourse==null) {
            return false;
        }
        return true;
    }
}
