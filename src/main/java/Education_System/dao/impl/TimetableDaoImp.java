package Education_System.dao.impl;

import Education_System.dao.TimetableDao;
import Education_System.pojo.timetable;

import java.util.List;

public class TimetableDaoImp extends BaseDao implements TimetableDao {

    @Override
    public List<timetable> queryTimetableBycourseIdandclazzId(String course_id, String clazz_id) {
        String sql = "select `index`,`course_id`,`clazz_id`,`classroom`,`start_week`,`end_week`,`week_day`,`section` from timetable_t where course_id = ? and  clazz_id=?";
        return queryForList(timetable.class,sql,course_id,clazz_id);
    }

    @Override
    public List<timetable> queryTimetableByclazzIdandweekdayandsection(String clazz_id,String week_day, String section) {
        String sql = "select `index`,`course_id`,`clazz_id`,`classroom`,`start_week`,`end_week`,`week_day`,`section` from timetable_t where clazz_id=? and week_day = ? and section=?";
        return queryForList(timetable.class,sql,clazz_id,week_day,section);
    }

    @Override
    public List<timetable> queryTimetableByclassroomandweekdayandsection(String classroom, String week_day, String section) {
        String sql = "select `index`,`course_id`,`clazz_id`,`classroom`,`start_week`,`end_week`,`week_day`,`section` from timetable_t where classroom=? and week_day = ? and section=?";
        return queryForList(timetable.class,sql,classroom,week_day,section);
    }

    @Override
    public timetable queryTimetableBycourseIdandclazzIdandstartweekandendweekandweekdayandsection(String course_id, String clazz_id, Integer start_week, Integer end_week, String week_day, String section) {
        String sql = "select `index`,`course_id`,`clazz_id`,`classroom`,`start_week`,`end_week`,`week_day`,`section` from timetable_t where course_id=? and clazz_id=? and start_week=? and end_week=? and week_day = ? and section=?";
        return queryForOne(timetable.class,sql,course_id,clazz_id,start_week,end_week,week_day,section);
    }


    @Override
    public int saveTimetable(timetable ti) {
        String sql = "insert into timetable_t(`course_id`,`clazz_id`,`classroom`,`start_week`,`end_week`,`week_day`,`section`) values(?,?,?,?,?,?,?)";
        return update(sql, ti.getCourse_id(),ti.getClazz_id(),ti.getClassroom(),ti.getStart_week(),ti.getEnd_week(),ti.getWeek_day(),ti.getSection());
    }

    @Override
    public int removeTimetable(timetable ti) {
        String sql = "delete from timetable_t where course_id=? and clazz_id=? and start_week = ? and end_week = ? and week_day=? and section=?";
        return update(sql, ti.getCourse_id(),ti.getClazz_id(),ti.getStart_week(),ti.getEnd_week(),ti.getWeek_day(),ti.getSection());
    }

    @Override
    public int removeTimetableBycourseIdandclazzId(String course_id, String clazz_id) {
        String sql = "delete from timetable_t where course_id=? and clazz_id=?";
        return update(sql,course_id,clazz_id);
    }
}
