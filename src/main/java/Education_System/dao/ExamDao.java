package Education_System.dao;

import Education_System.pojo.Exam;

import java.sql.Timestamp;
import java.util.List;


public interface ExamDao {
    public Exam queryByExamId(String id);

    public Exam queryExamBycourseidandclazzid(String course_id, String clazz_id);


    public List<Timestamp>  queryAllStarttimeByclazzid(String clazz_id);

    public List<Timestamp>  queryAllEndtimeByclazzid(String clazz_id);

    public List<Timestamp>  queryAllStarttimeByclassroom(String classroom);

    public List<Timestamp>  queryAllEndtimeByclassroom(String classroom);

    public List<Exam>  queryExamBycourseid(String course_id);

    public List<Exam>  queryExamByclazzid(String clazz_id);

    public List<Exam> queryAllExam();

    public int add_Exam(Exam exam);

    public int delete_Exam(Exam exam);

    public int delete_ExamBycourseidandclazzid(String course_id, String clazz_id);

    public int delete_All_Exam();

}
