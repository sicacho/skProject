package skAndroid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import skAndroid.dao.impl.UserDaoImpl;
import skAndroid.domain.Tbluser;
import skAndroid.util.Validation;

import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 * Created by khangtnse60992 on 1/19/2015.
 */
@Controller
@RequestMapping(value = "/")
public class UserService {

    @Autowired
    HttpSession sesson;
    @Autowired
    UserDaoImpl userDao;
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String redirectLogin() {
        return "login";
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logout() {
        sesson.invalidate();
        return "redirect:/course/1";
    }

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String redirectRegister() {
        return "register";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String login(@RequestParam(value = "username",required = true) String username,
                                  @RequestParam(value = "password",required = true) String password,ModelMap model) {
        Tbluser tbluser = userDao.checkLogin(username, password);
        if(null==tbluser){
            model.addAttribute("error","Wrong username or password !!!");
            return "login";
        } else {
            sesson.setAttribute("user",tbluser);
        }
        return "redirect:course/1";
    }
    @RequestMapping(value="/register", method= RequestMethod.POST)
    public String register(@RequestParam(value = "username",required = true) String username,
                        @RequestParam(value = "password",required = true) String password,
                        @RequestParam(value = "email",required = true) String email,ModelMap model) {
        if(username.matches("[a-zA-Z0-9]+") && (username.length() <= 16) && (username.length() >= 4)){
            if((password.length() <= 32) && (password.length() >= 4)) {
                try {
                    if(Validation.doLookup(email.substring(email.lastIndexOf("@")+1))>=0) {
                        Tbluser tbluser = new Tbluser(username,password,email);
                        userDao.persist(tbluser);
                        sesson.setAttribute("user",tbluser);
                    }
                } catch (NamingException e) {
                    System.out.println("Log : "+e.getMessage());
                    model.addAttribute("error","Email is not exist");
                    return "register";
                }
            } else {
                model.addAttribute("error","username is not validation");
                return "register";
            }
        } else {
            model.addAttribute("error","username is not validation");
            return "register";
        }
        return "redirect:course/1";
    }

}
