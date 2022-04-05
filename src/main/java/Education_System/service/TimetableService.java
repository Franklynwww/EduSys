package Education_System.service;

import Education_System.dao.Teacher_Course_Dao;
import Education_System.dao.TimetableDao;
import Education_System.dao.UserDao;
import Education_System.dao.impl.StudentUserDaoImp;
import Education_System.dao.impl.Teacher_Course_Dao_Imp;
import Education_System.dao.impl.TimetableDaoImp;
import Education_System.pojo.Course;
import Education_System.pojo.Student_Course;
import Education_System.pojo.Teacher_Course;
import Education_System.pojo.timetable;
import Education_System.utils.Term;

import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    private TimetableDao timetableDao = new TimetableDaoImp();
    private Student_Course_Service student_course_service = new Student_Course_Service();
    private Teacher_Course_Service teacher_course_service = new Teacher_Course_Service();
    private Teacher_Course_Dao teacher_course_dao = new Teacher_Course_Dao_Imp();
    private UserDao userDao = new StudentUserDaoImp();

    public List<timetable> queryTimetableByteacher(String teacher_identity) {
        List<timetable> l = new ArrayList<timetable>();
        List<Teacher_Course> l1 = teacher_course_dao.queryTCByTeacher(teacher_identity);
        for (Teacher_Course teacher_course : l1) {
            l.addAll(timetableDao.queryTimetableBycourseIdandclazzId(teacher_course.getCourse_id(), teacher_course.getClazz_id()));
        }
        return l;
    }

    public void add_timetable(timetable ti) {
        timetableDao.saveTimetable(ti);
    }

    public void delete_timetable(timetable ti) {
        timetableDao.removeTimetable(ti);
    }

    public boolean conflict(timetable ti_candidate) {
        List<timetable> tis = timetableDao.queryTimetableByclazzIdandweekdayandsection(ti_candidate.getClazz_id(), ti_candidate.getWeek_day(), ti_candidate.getSection());
        for (timetable ti : tis) {
            if (queryterm(ti).equals(queryterm(ti_candidate)) && !(ti_candidate.getEnd_week() < ti.getStart_week() || ti_candidate.getStart_week() > ti.getEnd_week())) {
                return true;
            }
        }
        tis = timetableDao.queryTimetableByclassroomandweekdayandsection(ti_candidate.getClassroom(), ti_candidate.getWeek_day(), ti_candidate.getSection());
        for (timetable ti : tis) {
            if (queryterm(ti).equals(queryterm(ti_candidate)) && !(ti_candidate.getEnd_week() < ti.getStart_week() || ti_candidate.getStart_week() > ti.getEnd_week())) {
                return true;
            }
        }
        return false;
    }

    public List<timetable> querycurrentTimetableBystudentidentity(String student_identity) {
        List<timetable> l = new ArrayList<timetable>();
        List<Course> courses = student_course_service.querycourse(student_identity);
        for (Course course : courses) {
            Student_Course sc = student_course_service.querybyStudentandCourse(student_identity, course.getId());
                l.addAll(timetableDao.queryTimetableBycourseIdandclazzId(course.getId(), userDao.queryUserByIdentity(student_identity).getClazz_id()));
            }
        return l;
    }

    public List<String> querycurrentcourseIdnotinTimetableBystudentidentity(String student_identity) {
        List<String> l = new ArrayList<String>();
        List<Course> courses = student_course_service.querycourse(student_identity);
        for (Course course : courses) {
            Student_Course sc = student_course_service.querybyStudentandCourse(student_identity, course.getId());
            if (timetableDao.queryTimetableBycourseIdandclazzId(course.getId(),userDao.queryUserByIdentity(student_identity).getClazz_id()).size()==0) {
                l.add(course.getId());
            }
        }
        return l;
    }

    public List<timetable> querycurrentTimetableByteacheridentity(String teacher_identity) {
        List<timetable> l = new ArrayList<timetable>();
        List<Teacher_Course> tcs = teacher_course_dao.queryTCByTeacher(teacher_identity);
        for (Teacher_Course teacher_course : tcs) {
            l.addAll(timetableDao.queryTimetableBycourseIdandclazzId(teacher_course.getCourse_id(), teacher_course.getClazz_id()));
        }
        return l;
    }

    public List<List<String>> querycurrentcourseIdandclazzIdnotinTimetableByteacheridentity(String teacher_identity) {
        List<List<String>> l = new ArrayList<>();
        List<Teacher_Course> tcs = teacher_course_dao.queryTCByTeacher(teacher_identity);
        for (Teacher_Course teacher_course : tcs) {
            List<String> l_temp = new ArrayList<>();
            if (timetableDao.queryTimetableBycourseIdandclazzId(teacher_course.getCourse_id(),teacher_course.getClazz_id()).size()==0) {
                l_temp.add(teacher_course.getCourse_id());
                l_temp.add(teacher_course.getClazz_id());
                l.add(l_temp);
            }
        }
        return l;
    }

    public String queryterm(timetable ti){
        return teacher_course_dao.queryTCByCourseandClazz(ti.getCourse_id(),ti.getClazz_id()).getTerm();
    }

    public List<timetable> querycurrentTimetableBystudentidentityincurrentterm(String student_identity){
        List<Course>courses = student_course_service.querycourseinoneterm(student_identity, Term.get_current_term());
        List<String> course_ids = new ArrayList<>();
        for(Course course:courses){
            course_ids.add(course.getId());
        };
        List<timetable> tis = new ArrayList<>();
        List<timetable> tis1 =  querycurrentTimetableBystudentidentity(student_identity);
        for(timetable ti:tis1){
            if(course_ids.indexOf(ti.getCourse_id())!=-1){
                tis.add(ti);
            }
        }
        return tis;
    }


    public List<Teacher_Course> querycurrentTCnotinTimetableBystudentidentityincurrentterm(String student_identity){
        List<Teacher_Course> l=new ArrayList<>();
        List<String> list_not_in_timetable_course_ids = querycurrentcourseIdnotinTimetableBystudentidentity(student_identity);
        for(String cid:list_not_in_timetable_course_ids){
            Teacher_Course tc = teacher_course_dao.queryTCByCourseandClazz(cid,userDao.queryUserByIdentity(student_identity).getClazz_id());
            if(tc.getTerm().equals(Term.get_current_term())){
                l.add(tc);
            }
        }
        return l;

    }


    public boolean in_list(List<List<String>> l,String s1,String s2){
        int len = l.size();
        for (List<String> strings : l) {
            if (s1.equals(strings.get(0)) && s2.equals(strings.get(1))) {
                return true;
            }
        }
        return false;
    }

    public List<timetable> querycurrentTimetableByteacheridentityincurrentterm(String teacher_identity){
        List<List<String>>courses_clazzs = teacher_course_service.querycourseIdandclazzIdinoneterm(teacher_identity,Term.get_current_term());
        List<timetable> tis = new ArrayList<>();
        List<timetable> tis1 = querycurrentTimetableByteacheridentity(teacher_identity);
        for(timetable ti:tis1){
            if(in_list(courses_clazzs,ti.getCourse_id(),ti.getClazz_id())){
                tis.add(ti);
            }
        }
        return tis;
    }



}
