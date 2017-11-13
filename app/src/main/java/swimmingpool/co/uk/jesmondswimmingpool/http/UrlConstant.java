package swimmingpool.co.uk.jesmondswimmingpool.http;

/**
 * Created by cody on 2017/11/13.
 */

public interface UrlConstant {
    String BASE_URL="http://10.0.2.2:8080/swimmingpool/api/";

    String LOGIN=BASE_URL+"tutor/login/";

    String ADD_TUTOR=BASE_URL+"tutor";
    String UPDATE_TUTOR=BASE_URL+"tutor";
    String GET_TUTOR=BASE_URL+"tutor";
    String GET_ALL_TUTOR=BASE_URL+"tutor/tutors";
    String REMOVE_TUTOR=BASE_URL+"tutor/login/";



    String ADD_STUDENT=BASE_URL+"student";
    String REMOVE_STUDENT=BASE_URL+"student";
    String UPDATE_STUDENT=BASE_URL+"student";
    String GET_STUDENT=BASE_URL+"student";
    String GET_ALL_STUDENT=BASE_URL+"student/students";
    String GET_ALL_STUDENT_BY_TUTOR=BASE_URL+"student/tutor/students";
    String GET_ACHIEVEMENT=BASE_URL+"student/achievement";
    String GET_SIGN_IN=BASE_URL+"student/signin/";

    String ADD_COURSE=BASE_URL+"course";
    String REMOVE_COURSE=BASE_URL+"course";
    String UPDATE_COURSE=BASE_URL+"course";
    String GET_COURSE=BASE_URL+"course";
    String GET_ALL_COURSE=BASE_URL+"course/courses";
    String GET_ALL_COURSE_BY_TUTOR=BASE_URL+"course/tutor";

    String CHOOSE=BASE_URL+"course/choose";
    String CHANGE=BASE_URL+"course/change";










}
