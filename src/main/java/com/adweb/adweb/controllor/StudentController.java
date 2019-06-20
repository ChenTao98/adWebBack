package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.Course;
import com.adweb.adweb.entity.Section;
import com.adweb.adweb.entity.Student;
import com.adweb.adweb.entity.courseStudent.StudentChoice;
import com.adweb.adweb.service.*;
import com.adweb.adweb.util.Message;
import com.adweb.adweb.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private ChoiceQuestionService choiceQuestionService;
    @Autowired
    private CourseStudentService courseStudentService;
    @Autowired
    private SectionService sectionService;

    @RequestMapping({"", "index", "/"})
    public String index(Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        setIndexModel(model, teacherId);
        return "student/index";
    }

    @GetMapping("{courseId}")
    public String viewCourseSelect(@PathVariable() Integer courseId, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Course course = courseService.getCourseById(courseId, teacherId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_NOT_YOURS_ERROR);
            setIndexModel(model, teacherId);
            return "student/index";
        }
        setCourseSelectModel(model, course);
        return "student/courseSelect";
    }

    @GetMapping("{courseId}/{studentId}")
    public String viewCourseOneStudent(@PathVariable() Integer courseId, @PathVariable() String studentId,
                                       Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Course course = courseService.getCourseById(courseId, teacherId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_NOT_YOURS_ERROR);
            setIndexModel(model, teacherId);
            return "student/index";
        }
        setCourseOneStudentModel(model, courseId, studentId);
        return "student/courseOneStudent";
    }

    @GetMapping("choice/{sectionId}/{studentId}")
    public String viewChoiceOneStudent(@PathVariable() Integer sectionId, @PathVariable() String studentId,
                                       Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setIndexModel(model, teacherId);
            return "course/index";
        }
        StudentChoice studentChoice=courseStudentService.studentChoice(sectionId,studentId);
        model.addAttribute("student",studentChoice);
        return "student/choiceOneStudent";
    }

    private void setIndexModel(Model model, String teacherId) {
        model.addAttribute("list", courseService.getCourseByTeacher(teacherId));
        model.addAttribute("themeList", themeService.getAllTheme());
    }

    private void setCourseSelectModel(Model model, Course course) {
        model.addAttribute("course", course);
        model.addAttribute("list", courseStudentService.getCourseSelectByCourseId(course.getId()));
    }

    private void setCourseOneStudentModel(Model model, Integer courseId, String studentId) {
        Course courseDetail = courseService.getCourseByStudent(courseId,studentId);
        model.addAttribute("courseName", courseDetail.getName());
        model.addAttribute("courseId", courseDetail.getId());
        model.addAttribute("list", courseDetail.getChapterList());
        Student student = courseStudentService.getStudentById(studentId);
        String name = student == null ? "" : student.getName();
        model.addAttribute("studentName", name);
        model.addAttribute("studentId", studentId);
    }
}
