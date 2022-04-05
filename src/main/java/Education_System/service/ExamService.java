package Education_System.service;

import Education_System.dao.ExamDao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.ExamDaoImp;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.global.exam_state;
import Education_System.pojo.Course;
import Education_System.pojo.Exam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamService {
    private Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    private Student_Course_Service student_course_service = new Student_Course_Service();
    private ExamDao examDao = new ExamDaoImp();
    private UserDao userDao = new StudentUserDaoImp();
    public int queryexamstate(Exam exam){
        Date date = new Date();
        Timestamp nowtime = new Timestamp(date.getTime());
        if(exam==null) return exam_state.NOT_ARRANGED;
        else {
            if(nowtime.before(exam.getStart_time()))return exam_state.ARRANGED_NOTSTARTED;
            else{
                if(nowtime.after(exam.getStart_time()) && nowtime.before(exam.getEnd_time()) )return  exam_state.STARTED_NOTFINISHED;
                else {
                    return exam_state.FINISHED;
                }
            }
        }
    }
    public List<Exam> query_All_Arranged_Exam_By_TeacherIdentity(String teacher_identity){
        List<Exam> exams = new ArrayList<Exam>();
        List<List<String>> l = teacher_course_service.querycourseIdandclazzId(teacher_identity);
        for(List<String> l1 : l){
            Exam exam_temp = examDao.queryExamBycourseidandclazzid(l1.get(0),l1.get(1));
            if(queryexamstate(exam_temp)!=exam_state.NOT_ARRANGED){
                exams.add(exam_temp);
            }
        }
        return exams;
    }

    public List<Exam> query_All_Arranged_Exam_By_StudentIdentity(String student_identity){
        List<Exam> exams = new ArrayList<Exam>();
        List<Course> courses = student_course_service.querycourse(student_identity);
        for(Course course:courses){
            String clazz =userDao.queryUserByIdentity(student_identity).getClazz_id();
            Exam exam_temp = examDao.queryExamBycourseidandclazzid(course.getId(), userDao.queryUserByIdentity(student_identity).getClazz_id());
            if(queryexamstate(exam_temp)!=exam_state.NOT_ARRANGED) {
                exams.add(exam_temp);
            }
        }
        return exams;
    }

    public List<Exam> query_Arranged_Not_Started_Exam_By_TeacherIdentity(String teacher_identity){
        List<Exam> exams = new ArrayList<Exam>();
        List<List<String>> l = teacher_course_service.querycourseIdandclazzId(teacher_identity);
        for(List<String> l1 : l){
            Exam exam_temp = examDao.queryExamBycourseidandclazzid(l1.get(0),l1.get(1));
            if(queryexamstate(exam_temp)==exam_state.ARRANGED_NOTSTARTED){
                   exams.add(exam_temp);
            }
        }
        return exams;
    }

    public List<Exam> query_Arranged_Not_Started_Exam_By_StudentIdentity(String student_identity){
        List<Exam> exams = new ArrayList<Exam>();
        List<Course> courses = student_course_service.querycourse(student_identity);
        for(Course course:courses){
            String clazz =userDao.queryUserByIdentity(student_identity).getClazz_id();
            Exam exam_temp = examDao.queryExamBycourseidandclazzid(course.getId(), userDao.queryUserByIdentity(student_identity).getClazz_id());
            if(queryexamstate(exam_temp)==exam_state.ARRANGED_NOTSTARTED) {
                exams.add(exam_temp);
            }
        }
        return exams;
    }

    public boolean if_time_conf_in_clazz(String start_time,String end_time,String clazz_id){
        Timestamp req_start_time = Timestamp.valueOf(start_time);
        Timestamp req_end_time = Timestamp.valueOf(end_time);
        List<Timestamp> all_start_time_same_clazzid = examDao.queryAllStarttimeByclazzid(clazz_id);
        List<Timestamp> all_end_time_same_clazzid = examDao.queryAllEndtimeByclazzid(clazz_id);
        int len = all_end_time_same_clazzid.size();
        for (int i = 0;i<len;i++){
            if(req_start_time.before(all_end_time_same_clazzid.get(i)) && req_end_time.after(all_start_time_same_clazzid.get(i))){
                return true;
            }
        }
        return false;
    }


    public boolean if_time_conf_in_classroom(String start_time,String end_time,String classroom){
        Timestamp req_start_time = Timestamp.valueOf(start_time);
        Timestamp req_end_time = Timestamp.valueOf(end_time);
        List<Timestamp> all_start_time_same_classroom = examDao.queryAllStarttimeByclassroom(classroom);
        List<Timestamp> all_end_time_same_classroom = examDao.queryAllEndtimeByclassroom(classroom);
        int len = all_end_time_same_classroom.size();
        for (int i = 0;i<len;i++){
            if(req_start_time.before(all_end_time_same_classroom.get(i)) && req_end_time.after(all_start_time_same_classroom.get(i))){
                return true;
            }
        }
        return false;
    }

    public boolean is_legal_start_end(String start_time,String end_time){
        Timestamp req_start_time = Timestamp.valueOf(start_time);
        Timestamp req_end_time = Timestamp.valueOf(end_time);
        return req_end_time.after(req_start_time);
    }



}
