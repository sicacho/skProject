package skAndroid.dto;

/**
 * Created by khangtnse60992 on 10/17/2014.
 */
public class LessonDTO {
    int id;
    String lessonName;
    int lessonNumber;
//    List<LessonDetailDTO> lessonDetails;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

//    public List<LessonDetailDTO> getLessonDetails() {
//        return lessonDetails;
//    }
//
//    public void setLessonDetails(List<LessonDetailDTO> lessonDetails) {
//        this.lessonDetails = lessonDetails;
//    }
}
