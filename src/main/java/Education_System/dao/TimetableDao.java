package Education_System.dao;

import Education_System.pojo.timetable;

import java.util.List;

public interface TimetableDao {

    public List<timetable> queryTimetableBycourseIdandclazzId(String course_id, String clazz_id);

    public List<timetable> queryTimetableByclazzIdandweekdayandsection(String clazz_id, String week_day, String section);

    public List<timetable> queryTimetableByclassroomandweekdayandsection(String classroom, String week_day, String section);

    public timetable queryTimetableBycourseIdandclazzIdandstartweekandendweekandweekdayandsection(String course_id, String clazz_id, Integer start_week, Integer end_week, String week_day, String section);

    public int saveTimetable(timetable ti);

    public int removeTimetable(timetable ti);

    public int removeTimetableBycourseIdandclazzId(String course_id, String clazz_id);
}
