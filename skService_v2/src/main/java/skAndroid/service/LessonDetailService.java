package skAndroid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import skAndroid.dao.impl.LessonDaoImpl;
import skAndroid.dao.impl.LessonDetailDaoImpl;
import skAndroid.domain.Tbllessondetail;
import skAndroid.dto.LessonDetailDTO;
import skAndroid.util.ConvertEntity;

import java.util.List;

/**
 * Created by khangtnse60992 on 10/3/2014.
 */
@Controller
@RequestMapping(value = "/lessonDetail")
public class LessonDetailService {

    @Autowired
    LessonDaoImpl lessonDaoImpl;
    @Autowired
    LessonDetailDaoImpl lessonDetailDaoImpl;

    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET,produces="application/json")
    public @ResponseBody
    List<LessonDetailDTO> getTblLessonDetail(@PathVariable String id) {
           List<Tbllessondetail> tbllessondetails = lessonDetailDaoImpl.getLessonDetailByLessonId(Integer.parseInt(id));
           List<LessonDetailDTO> lessonDetailDTOs = ConvertEntity.convertListLessonDetailEntityToDTO(tbllessondetails);
           return lessonDetailDTOs;
    }


}
