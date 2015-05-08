package skAndroid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skAndroid.dao.impl.CourseDaoImpl;
import skAndroid.dao.impl.LessonDaoImpl;
import skAndroid.dao.impl.LessonDetailDaoImpl;
import skAndroid.domain.Tblcourse;
import skAndroid.domain.Tbllesson;
import skAndroid.domain.Tbllessondetail;
import skAndroid.domain.Tbluser;
import skAndroid.dto.LessonDTO;
import skAndroid.util.ConvertEntity;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/25/2014.
 */
@Controller
@RequestMapping(value = "/lesson")
public class LessonService {

    @Autowired
    LessonDaoImpl lessonDaoImpl;
    @Autowired
    HttpSession sesson;
    @Autowired
    CourseDaoImpl courseDaoImpl;
    @Autowired
    LessonDetailDaoImpl lessonDetailDaoImpl;
    @Autowired
    SecurityService securityService;

    @RequestMapping(value = "/{id}/{page}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<LessonDTO> getListLessonByCourse(@PathVariable Integer id, @PathVariable Integer page) {
        List<Tbllesson> tbllessons = lessonDaoImpl.getListSessonByCourseId(id, page);
        List<LessonDTO> lessonDTOs = ConvertEntity.convertListLessonEntityToDTO(tbllessons);
        return lessonDTOs;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public String getListLessonDefault(@PathVariable int courseId, ModelMap model) {
        Tbllesson tbllesson = null;
        try {
            tbllesson = lessonDaoImpl.getLessonById(courseId, null);
        } catch (Exception e) {
            System.out.println("Log :" + e.getMessage());
        }
        List<Integer> mapLesson = lessonDaoImpl.getNumberLesson(courseId);
        model.addAttribute("lesson", tbllesson);
        model.addAttribute("listNumber", mapLesson);
        if (tbllesson != null) {
            model.addAttribute("thisPage", tbllesson.getLessonNumber());
        } else {
            Tblcourse tblcourse = courseDaoImpl.find(courseId);
            model.addAttribute("thisPage",null);
            model.addAttribute("course",tblcourse);
        }
        return "lesson";
    }

    @RequestMapping(value = "{courseId}/{lessonNumber}", method = RequestMethod.GET)
    public String getLessonAndDetail(@PathVariable int courseId, @PathVariable Integer lessonNumber, ModelMap model) {
        Tbllesson tbllesson = null;
        try {
            tbllesson = lessonDaoImpl.getLessonById(courseId, lessonNumber);
        } catch (Exception e) {
            System.out.println("Log :" + e.getMessage());
        }
        List<Integer> mapLesson = lessonDaoImpl.getNumberLesson(courseId);
        model.addAttribute("lesson", tbllesson);
        model.addAttribute("listNumber", mapLesson);
        model.addAttribute("thisPage", lessonNumber);
        return "lesson";
    }

    @RequestMapping(value = "create/{courseId}", method = RequestMethod.GET)
    public String createRedirect(@PathVariable int courseId, ModelMap model) {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(courseId,tbluser.getId())==false) {
            model.addAttribute("error","You dont have permission");
            return "error";
        }
        model.addAttribute("courseId", courseId);
        return "lessonAction";
    }

    @RequestMapping(value = "update/{lessonId}", method = RequestMethod.GET)
    public String updateRedirect(@PathVariable int lessonId, ModelMap model) {
        Tbllesson tbllesson = lessonDaoImpl.find(lessonId);
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(tbllesson.getCourseId().getId(),tbluser.getId())==false) {
            model.addAttribute("error","You dont have permission");
            return "error";
        }
        model.addAttribute("tbllesson", tbllesson);
        return "lessonAction";
    }
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam(required = true) int lessonId,@RequestParam(required = true) int courseId,ModelMap model) {
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(courseId,tbluser.getId())==false) {
            model.addAttribute("error","You dont have permission");
            return "error";
        }
        Tbllesson tbllesson = lessonDaoImpl.find(lessonId);
        lessonDaoImpl.remove(tbllesson);
        return "redirect:/course/update/" + courseId;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@RequestParam(required = true) int courseId,
                         @RequestParam(required = true) String name,
                         @RequestParam(required = true) int number,
                         @RequestParam(required = true) List<String> voca,

                         @RequestParam(required = true) List<String> vocatrans,
                         @RequestParam(required = true) List<Integer> type, ModelMap model,RedirectAttributes redirectAttrs) {
        Tbllesson tbllesson = null;
        List<Tbllessondetail> tbllessondetails = new ArrayList<Tbllessondetail>();
        Tbllessondetail tbllessondetail = null;
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(courseId,tbluser.getId())==false) {
            model.addAttribute("error","You dont have permission");
            return "error";
        }
        boolean checkNum;
        checkNum = lessonDaoImpl.checkLessonNumber(courseId, number);
        if (checkNum == false) {
//            model.addAttribute("courseId", courseId);
            redirectAttrs.addFlashAttribute("error", "Lesson's number is exist");
            return "redirect:create/" + courseId;
        }
        tbllesson = new Tbllesson();
        try {

            tbllesson.setCourseId(courseDaoImpl.find(courseId));
        } catch (Exception e) {
            System.out.println("Log :" + e.getMessage());
            return "redirect:create/" + courseId;
        }
        tbllesson.setLessonName(name);
        tbllesson.setLessonNumber(number);

        for (int i = 0; i < voca.size(); i++) {
            tbllessondetail = new Tbllessondetail();
            tbllessondetail.setLessonId(tbllesson);
            tbllessondetail.setSentence(voca.get(i));
            tbllessondetail.setSentenceTrans(vocatrans.get(i));
            tbllessondetail.setType(type.get(i));
            tbllessondetails.add(tbllessondetail);
        }
        tbllesson.getListLessonDetail().addAll(tbllessondetails);
        try {
            lessonDaoImpl.persist(tbllesson);

        } catch (Exception e) {
            System.out.println("Log :" + e.getMessage());
            return "redirect:create/" + courseId;
        }
        return "redirect:" + courseId + "/" + tbllesson.getLessonNumber();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@RequestParam(required = true) int lessonId,
                         @RequestParam(required = true) int courseId,
                         @RequestParam(required = true) String name,
                         @RequestParam(required = true) int number,
                         @RequestParam(required = true) List<String> voca,
                         @RequestParam(required = true) List<Integer> lessonDetailId,
                         @RequestParam(required = true) List<String> vocatrans,
                         @RequestParam(required = true) List<Integer> type, ModelMap model,RedirectAttributes redirectAttrs) {
        Tbllesson tbllesson = null;
        List<Tbllessondetail> tbllessondetails = new ArrayList<Tbllessondetail>();
        Tbllessondetail tbllessondetail = null;
        boolean checkNum;
        Tbluser tbluser = (Tbluser) sesson.getAttribute("user");
        if (tbluser == null) {
            return "login";
        } else if (securityService.authorize(courseId,tbluser.getId())==false) {
            model.addAttribute("error","You dont have permission");
            return "error";
        }
        tbllesson = lessonDaoImpl.find(lessonId);
        if (number != tbllesson.getLessonNumber()) {
            checkNum = lessonDaoImpl.checkLessonNumberUpdate(courseId,lessonId, number);
            if (checkNum == false) {
                redirectAttrs.addFlashAttribute("error", "Lesson's number is exist");
                return "redirect:update/" + lessonId;
            }
        }
        tbllesson.setLessonName(name);
        tbllesson.setLessonNumber(number);

        for (int i = 0; i < voca.size(); i++) {
            tbllessondetail = new Tbllessondetail();
            if (lessonDetailId.get(i) != null) {
                tbllessondetail.setId(lessonDetailId.get(i));
            }
            tbllessondetail.setLessonId(tbllesson);
            tbllessondetail.setSentence(voca.get(i));
            tbllessondetail.setSentenceTrans(vocatrans.get(i));
            tbllessondetail.setType(type.get(i));
            tbllessondetails.add(tbllessondetail);
        }
        tbllesson.getListLessonDetail().clear();
        tbllesson.getListLessonDetail().addAll(tbllessondetails);
        try {
            lessonDaoImpl.merge(tbllesson);

        } catch (Exception e) {
            System.out.println("Log :" + e.getMessage());
            return "redirect:update/" + lessonId;
        }
        return "redirect:update/" + lessonId;
    }
}
