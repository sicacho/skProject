package skAndroid.dao;

import skAndroid.domain.Tbluser;

/**
 * Created by khangtnse60992 on 1/20/2015.
 */
public interface UserDao  {
    public Tbluser checkLogin(String username, String password);
}
