package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.Chapter;
import com.adweb.adweb.entity.Course;
import com.adweb.adweb.entity.Knowledge;
import com.adweb.adweb.entity.Section;
import com.adweb.adweb.service.*;
import com.adweb.adweb.util.Message;
import com.adweb.adweb.util.PathUtil;
import com.adweb.adweb.util.SessionUtil;
import com.adweb.adweb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private KnowledgeService knowledgeService;
    private static final String courseImageUp = PathUtil.COURSE_IMAGE_UP;

    @RequestMapping({"", "index", "/"})
    public String index(Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        setTeacherCourseModel(model, teacherId);
        return "course/index";
    }

    @PostMapping("add")
    public String addCourse(@RequestParam() MultipartFile image, @RequestParam() String name,
                            @RequestParam() String summary, @RequestParam() String startTime,
                            @RequestParam() String endTime, @RequestParam() Integer credit,
                            @RequestParam() String type, @RequestParam() Integer theme,
                            Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        if (image.isEmpty() || StringUtil.isEmpty(name) || StringUtil.isEmpty(summary) || StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)
                || credit == null || StringUtil.isEmpty(type) || theme == null) {
            Message.writeMessage(model, Message.DATE_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Date startTimeDate;
        Date endTimeDate;
        try {
            startTimeDate = StringUtil.simpleDateFormat.parse(startTime);
            endTimeDate = StringUtil.simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            Message.writeMessage(model, Message.DATE_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }

        if (startTimeDate.after(endTimeDate) || credit <= 0) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        String fileName = image.getOriginalFilename();
        String imageSrc = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
        File file = new File(courseImageUp + imageSrc);
        try {
            image.transferTo(file);
        } catch (IOException e) {
            if (file.exists()) {
                file.delete();
            }
            Message.writeMessage(model, Message.SYSTEM_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = new Course(name, summary, teacherId, startTimeDate, endTimeDate, imageSrc, credit, type, theme, 0);
        if (courseService.insertCourse(course) != 1) {
            file.delete();
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setTeacherCourseModel(model, teacherId);
        return "course/index";
    }

    @GetMapping("{courseId}")
    public String viewCourse(@PathVariable() Integer courseId, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Course course = courseService.getCourseById(courseId, teacherId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        setCourseDetailModel(model, course);
        return "course/courseDetail";
    }

    @PostMapping("add/{courseId}/chapter")
    public String addChapter(@PathVariable() Integer courseId, @RequestParam() String name,
                             @RequestParam() String summary, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Course course = courseService.getCourseById(courseId, teacherId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(summary)) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        Chapter chapter = new Chapter(name, summary, chapterService.getLargestChapterOrderName(courseId) + 1, courseId);
        if (chapterService.insertChapter(chapter) != 1) {
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setCourseDetailModel(model, course);
        return "course/courseDetail";
    }

    @GetMapping("chapter/{chapterId}")
    public String viewChapter(@PathVariable() Integer chapterId, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Chapter chapter = chapterService.isChapterBelongToTeacher(chapterId, teacherId);
        if (chapter == null) {
            Message.writeMessage(model, Message.CHAPTER_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        setChapterDetail(model, chapter);
        return "course/chapterDetail";
    }

    @PostMapping("add/{chapterId}/section")
    public String addSection(@PathVariable() Integer chapterId, @RequestParam() String name,
                             @RequestParam() String summary, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Chapter chapter = chapterService.isChapterBelongToTeacher(chapterId, teacherId);
        if (chapter == null) {
            Message.writeMessage(model, Message.CHAPTER_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseByChapter(chapterId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_GET_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(summary)) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        Section section = new Section(name, summary, sectionService.getLargestSectionOrderNumber(chapterId) + 1, chapterId);
        if (sectionService.insertSection(section) != 1) {
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setChapterDetail(model, chapter);
        return "course/chapterDetail";
    }
    @GetMapping("section/{sectionId}")
    public String viewSection(@PathVariable()Integer sectionId,Model model,HttpServletRequest httpServletRequest){
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section=sectionService.isSectionBelongToTeacher(sectionId,teacherId);
        if(section==null){
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        setSectionDetail(model,section);
        return "course/sectionDetail";
    }

    private void setTeacherCourseModel(Model model, String teacherId) {
        model.addAttribute("list", courseService.getCourseByTeacher(teacherId));
        model.addAttribute("themeList", themeService.getAllTheme());
    }

    private void setCourseDetailModel(Model model, Course course) {
        model.addAttribute("course", course);
        List<Chapter> list = chapterService.getChapterByCourse(course.getId());
        model.addAttribute("list", list);
    }

    private void setChapterDetail(Model model, Chapter chapter) {
        model.addAttribute("chapter", chapter);
        List<Section> list = sectionService.getSectionByChapter(chapter.getId());
        model.addAttribute("list", list);
    }
    private void setSectionDetail(Model model,Section section){
        model.addAttribute("section",section);
        List<Knowledge> list=knowledgeService.getKnowledgeBySection(section.getId());
        model.addAttribute("list",list);
    }
}
