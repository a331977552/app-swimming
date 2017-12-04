package swimmingpool.co.uk.jesmondswimmingpool.http;

/**
 * Created by cody on 2017/11/13.
 */

public interface UrlConstant {
//    String BASE_URL="http://10.0.2.2:8080/swimmingpool/api/";
//    String BASE_URL="http://192.168.0.24:8080/swimmingpool/api/";
    String BASE_URL=" http://52.88.68.208:8080/swimmingpool/api/";

    String LOGIN=BASE_URL+"login";

    String ADD_TUTOR=BASE_URL+"tutor";

    String ADD_STUDENT=BASE_URL+"student";
    String REMOVE_STUDENT=BASE_URL+"student/";
    String UPDATE_STUDENT=BASE_URL+"student";
    String GET_ALL_STUDENT_BYCOURSE = BASE_URL+"student/students/";
    String GET_ALL_STUDENT=BASE_URL+"student/students";
    String GET_ACHIEVEMENT=BASE_URL+"student/achievement";

    String ADD_COURSE=BASE_URL+"course";
    String REMOVE_COURSE=BASE_URL+"course/";
    String UPDATE_COURSE=BASE_URL+"course";
    String GET_ALL_COURSE=BASE_URL+"course/courses";

    String CHOOSE=BASE_URL+"course/choose";
    String CHANGE=BASE_URL+"course/change";




    String ATTENDANCE=BASE_URL+"student/attendance";
    String SIGN_IN = BASE_URL+"student/signin/";
    String GET_COURSE_BY_STUDENT =BASE_URL+ "course/student/";
    String FINISH_COURSE =BASE_URL+"student/finishcourse";
    String GET_ALL_ITEM_BY_COURSE =BASE_URL+"course/items/";
    String ACHIEVEMENT = BASE_URL + "student/achievemnt";
    String ADD_ITEMS = BASE_URL + "course/items";
    String REMOVE_ITEM = BASE_URL + "course/item/";
    String POST_ERROR = BASE_URL + "error";
    String REMOVE_FINISHSTATUS = BASE_URL + "student/finishcourse";
}
