package skAndroid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import skAndroid.dao.CourseDao;
import skAndroid.dao.impl.CourseDaoImpl;
import skAndroid.domain.Tblcourse;
import skAndroid.domain.Tbluser;
import skAndroid.dto.CourseDTO;
import skAndroid.util.Constant;
import skAndroid.util.ConvertEntity;
import skAndroid.util.RemoveUTF8;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/21/2014.
 */
@Controller
@RequestMapping(value = "/course")
public class CourseService {

    @Autowired
    CourseDaoImpl courseDaoImpl;
    @Autowired
    HttpSession sesson;
    @Autowired
    SecurityService securityService;

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public String getAllCourse(@PathVariable int page, ModelMap model) {
        List<Tblcourse> tblcourses = courseDaoImpl.getAllCourses(page);
        int numberCourse = courseDaoImpl.getNumberAllCourses();
        model.addAttribute("courses", tblcourses);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxpage", Math.ceil(numberCourse * 1.0 / Constant.numberPage));
//        System.out.println(tblcourses.get(0).getLesson().size());
        return "index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createRedirect() {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        }
        return "courseAction";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam String keyword, ModelMap model) {
        String keywordConvert = RemoveUTF8.removeAccent(keyword);
        List<Tblcourse> courses = courseDaoImpl.getCourseSearch(keywordConvert, 0);
        int numberCourse = courseDaoImpl.getNumberCourseSearch(keywordConvert);
        if (numberCourse != 0) {
            model.addAttribute("courses", courses);
        } else {
            model.addAttribute("error","Your search returned no matches.");
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", 1);
        model.addAttribute("maxpage", Math.ceil(numberCourse * 1.0 / Constant.numberPage));
        return "search";
    }

    @RequestMapping(value = "/search/{keyword}/{page}", method = RequestMethod.GET)
    public String search(@PathVariable String keyword, @PathVariable int page, ModelMap model) {
        String keywordConvert = RemoveUTF8.removeAccent(keyword);
        List<Tblcourse> courses = courseDaoImpl.getCourseSearch(keywordConvert, page);
        int numberCourse = courseDaoImpl.getNumberCourseSearch(keywordConvert);
        if (numberCourse != 0) {
            model.addAttribute("courses", courses);
        } else {
            model.addAttribute("error","Your search returned no matches.");
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("maxpage", Math.round(numberCourse * 1.0 / Constant.numberPage));
        return "search";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(
            @RequestParam(value = "courseId", required = true) int courseId,
            @RequestParam(value = "urlPath", required = true) String urlPath, ModelMap model) {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(courseId, tbluser.getId()) == false) {
            model.addAttribute("error", "You dont have permission");
            return "error";
        }
        courseDaoImpl.remove(courseDaoImpl.find(courseId));
        String url = "redirect:" + urlPath;
        return url;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateRedirect(@PathVariable int id, ModelMap model) {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(id, tbluser.getId()) == false) {
            model.addAttribute("error", "You dont have permission");
            return "error";
        }

        Tblcourse tblcourse = null;
        try {
            tblcourse = courseDaoImpl.find(id);
        } catch (Exception e) {
            System.out.println("Log : " + e.getStackTrace());
        }
        model.addAttribute("course", tblcourse);
        return "courseAction";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(value = "id", required = true) int id,
                         @RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "detail", required = true) String detail, ModelMap model) {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(id, tbluser.getId()) == false) {
            model.addAttribute("error", "You dont have permission");
            return "error";
        }

        Tblcourse tblcourse = null;
        try {
            tblcourse = courseDaoImpl.find(id);
            tblcourse.setCourseName(name);
            tblcourse.setDetail(detail);
            tblcourse.setAlias(RemoveUTF8.removeAccent(name));
            courseDaoImpl.merge(tblcourse);
        } catch (Exception e) {
            System.out.println("Log : " + e.getMessage());
        }
        model.addAttribute("course", tblcourse);
        return "courseAction";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "detail", required = true) String detail) {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        }
        Tblcourse tblcourse = new Tblcourse();
        tblcourse.setCourseName(name);
        tblcourse.setDetail(detail);
        tblcourse.setUserId(tbluser);
        tblcourse.setAlias(RemoveUTF8.removeAccent(name));
        courseDaoImpl.persist(tblcourse);
        return "redirect:/course/" + tbluser.getId() + "/1";
    }

    @RequestMapping(value = "/{id}/{page}", method = RequestMethod.GET)
    public String getListCourseByUserView(@PathVariable int id, @PathVariable int page, ModelMap model) {
        List<Tblcourse> courses = null;
        try {
            courses = courseDaoImpl.getUserCourses(id, page);
        } catch (Exception e) {
            System.out.println("Log : " + e.getMessage());
        }
        int numberCourse = courseDaoImpl.getNumberUserCourse(id);
        if (numberCourse != 0) {
            model.addAttribute("courses", courses);
        } else {
            model.addAttribute("error","You dont have any course .");
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("maxpage", Math.ceil(numberCourse * 1.0 / Constant.numberPage));
        return "index";
    }

    @RequestMapping(value = "/{id}/{page}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<CourseDTO> getListCourseByUser(@PathVariable int id, @PathVariable int page) {
        List<Tblcourse> tblcourses = courseDaoImpl.getUserCourses(id, page);
        List<CourseDTO> courseDTOs = ConvertEntity.convertListCourseEntityToDTO(tblcourses);
        return courseDTOs;
    }
    @RequestMapping(value = "/search/{keyword}/{page}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<CourseDTO> getListSearch(@PathVariable String keyword, @PathVariable int page, ModelMap model) {
        String keywordConvert = RemoveUTF8.removeAccent(keyword);
        List<Tblcourse> courses = courseDaoImpl.getCourseSearch(keywordConvert, page);
        List<CourseDTO> courseDTOs = ConvertEntity.convertListCourseEntityToDTO(courses);
        return courseDTOs;
    }
//    @RequestMapping(value = "/insert" ,method = RequestMethod.GET,produces="application/json")
//    public void insertCourse() {
//        Tblcourse tblcourse = new Tblcourse();
//        Tbluser tbluser = new Tbluser();
//        tbluser.setUsername("khang");
//        tbluser.setPassword("123456");
//        tbluser.setEmail("kha@gmail");
//        tblcourse.setCourseName("khang");
//        tblcourse.setDetail("abc");
//        tblcourse.setUserId(tbluser);
////        courseDaoImpl.getEntityManager().getTransaction().begin();
//        courseDaoImpl.persist(tblcourse);
////        courseDaoImpl.getEntityManager().getTransaction().commit();
//    }
}
