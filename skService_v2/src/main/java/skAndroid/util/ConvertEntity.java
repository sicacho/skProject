package skAndroid.util;

import skAndroid.domain.Tblcourse;
import skAndroid.domain.Tbllesson;
import skAndroid.domain.Tbllessondetail;
import skAndroid.dto.CourseDTO;
import skAndroid.dto.LessonDTO;
import skAndroid.dto.LessonDetailDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khangtnse60992 on 10/17/2014.
 */
public class ConvertEntity {
    public static CourseDTO convertEntityToDTO(Tblcourse tblcourse) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(tblcourse.getId());
        courseDTO.setName(tblcourse.getCourseName());
        courseDTO.setDetail(tblcourse.getDetail());
        courseDTO.setUsername(tblcourse.getUserId().getUsername());
        return courseDTO;
    }

    public static LessonDTO convertEntityToDTO(Tbllesson tbllesson) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(tbllesson.getId());
        lessonDTO.setLessonName(tbllesson.getLessonName());
        lessonDTO.setLessonNumber(tbllesson.getLessonNumber());
//        lessonDTO.setLessonDetails(convertListLessonDetailEntityToDTO(tbllesson.getListLessonDetail()));
        return lessonDTO;
    }
    public static LessonDetailDTO convertEntityToDTO(Tbllessondetail tbllessondetail){
        LessonDetailDTO lessonDetailDTO = new LessonDetailDTO();
        lessonDetailDTO.setId(tbllessondetail.getId());
        lessonDetailDTO.setSentence(tbllessondetail.getSentence());
        lessonDetailDTO.setSentenceTrans(tbllessondetail.getSentenceTrans());
        lessonDetailDTO.setVoiceUrl(tbllessondetail.getVoiceUrl());
        lessonDetailDTO.setType(tbllessondetail.getType());
        return lessonDetailDTO;
    }

    public static List<CourseDTO> convertListCourseEntityToDTO(List<Tblcourse> tblcourses) {
        List<CourseDTO> courseDTOs = new ArrayList<CourseDTO>();
        for(int i = 0 ; i < tblcourses.size() ; i ++) {
            courseDTOs.add(convertEntityToDTO(tblcourses.get(i)));
        }
        return courseDTOs;
    }

    public static List<LessonDetailDTO> convertListLessonDetailEntityToDTO(List<Tbllessondetail> tbllessondetails) {
        List<LessonDetailDTO> lessonDetailDTOs = new ArrayList<LessonDetailDTO>();
        for(int i = 0 ; i < tbllessondetails.size() ; i ++) {
            lessonDetailDTOs.add(convertEntityToDTO(tbllessondetails.get(i)));
        }
        return lessonDetailDTOs;
    }
    public static List<LessonDTO> convertListLessonEntityToDTO(List<Tbllesson> tbllessons) {
        List<LessonDTO> lessonDTOs = new ArrayList<LessonDTO>();
        for(int i = 0 ; i < tbllessons.size() ; i ++) {
            lessonDTOs.add(convertEntityToDTO(tbllessons.get(i)));
        }
        return lessonDTOs;
    }
}
