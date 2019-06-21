package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.*;
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
import java.util.ArrayList;
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
    @Autowired
    private ChoiceQuestionService choiceQuestionService;

    private static final String courseImageUp = PathUtil.COURSE_IMAGE_UP;
    private static final String courseImageHtml = PathUtil.COURSE_IMAGE_HTML;
    private static final int[] arrayInt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final String[] typeArray = {"web开发", "人工智能", "移动开发", "游戏开发", "运维测试", "互联网", "大数据与云计算", "计算机系统"};

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

    @PostMapping("modify/{courseId}/course")
    public String modifyCourse(@RequestParam() MultipartFile image, @RequestParam() String name,
                               @RequestParam() String summary, @RequestParam() String startTime,
                               @RequestParam() String endTime, @RequestParam() Integer credit,
                               @RequestParam() String type, @RequestParam() Integer theme, @PathVariable() Integer courseId,
                               Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Course course = courseService.getCourseById(courseId, teacherId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(summary) || StringUtil.isEmpty(startTime)
                || StringUtil.isEmpty(endTime) || credit == null || StringUtil.isEmpty(type) || theme == null) {
            Message.writeMessage(model, Message.DATE_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        Date startTimeDate;
        Date endTimeDate;
        try {
            startTimeDate = StringUtil.simpleDateFormat.parse(startTime);
            endTimeDate = StringUtil.simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            Message.writeMessage(model, Message.DATE_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        if (startTimeDate.after(endTimeDate) || credit <= 0) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        Course courseModify = new Course(name, summary, null, startTimeDate, endTimeDate, null, credit, type, theme, 0);
        courseModify.setId(courseId);
        File file = null;
        if (!image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            String imageSrc = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
            file = new File(courseImageUp + imageSrc);
            try {
                image.transferTo(file);
                courseModify.setImageSrc(imageSrc);
            } catch (IOException e) {
                if (file.exists()) {
                    file.delete();
                }
                Message.writeMessage(model, Message.SYSTEM_ERROR);
                setTeacherCourseModel(model, teacherId);
                return "course/index";
            }
        }
        if (courseService.modifyCourse(courseModify) != 1) {
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
            }
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setCourseDetailModel(model, course);
            return "course/courseDetail";
        }
        if (file != null) {
            file = new File(courseImageUp + course.getImageSrc());
            if (file.exists()) {
                file.delete();
            }
        }
        Message.writeMessage(model, Message.SUCCESS);
        setCourseDetailModel(model, courseService.getCourseById(courseId, teacherId));
        return "course/courseDetail";
    }

    @GetMapping("delete/{courseId}/course")
    public String deleteCourse(@PathVariable() Integer courseId, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Course course = courseService.getCourseById(courseId, teacherId);
        if (course == null) {
            Message.writeMessage(model, Message.COURSE_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        courseService.deleteCourse(course);
        File file = new File(courseImageUp + course.getImageSrc());
        if (file.exists()) {
            file.delete();
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

    @PostMapping("modify/{chapterId}/chapter")
    public String modifyChapter(@PathVariable() Integer chapterId, @RequestParam() String name, @RequestParam() Integer courseId,
                                @RequestParam() String summary, @RequestParam() Integer orderNumber,
                                @RequestParam() Integer orderNumberOld, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Chapter chapter = chapterService.isChapterBelongToTeacher(chapterId, teacherId);
        if (chapter == null) {
            Message.writeMessage(model, Message.CHAPTER_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(summary) || orderNumber <= 0) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        Course course = courseService.getCourseByChapter(chapterId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        Chapter chapterModify = new Chapter(name, summary, null, null);
        chapterModify.setId(chapterId);
        if (!orderNumber.equals(orderNumberOld)) {
            chapterModify.setOrderNumber(orderNumber);
        }
        if (chapterService.modifyChapter(chapterModify, orderNumberOld, courseId) == 0) {
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setChapterDetail(model, chapterService.getChapterByChapterId(chapterId));
        return "course/chapterDetail";
    }

    @GetMapping("delete/{chapterId}/chapter")
    public String deleteChapter(@PathVariable() Integer chapterId,
                                Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Chapter chapter = chapterService.isChapterBelongToTeacher(chapterId, teacherId);
        if (chapter == null) {
            Message.writeMessage(model, Message.CHAPTER_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseByChapter(chapterId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setChapterDetail(model, chapter);
            return "course/chapterDetail";
        }
        chapterService.deleteChapter(chapter);
        Message.writeMessage(model, Message.SUCCESS);
        setCourseDetailModel(model, course);
        return "course/courseDetail";
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
    public String viewSection(@PathVariable() Integer sectionId, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        setSectionDetail(model, section);
        return "course/sectionDetail";
    }

    @PostMapping("modify/{sectionId}/section")
    public String modifySection(@PathVariable() Integer sectionId, @RequestParam() String name, @RequestParam() Integer chapterId,
                                @RequestParam() String summary, @RequestParam() Integer orderNumber,
                                @RequestParam() Integer orderNumberOld, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(summary) || orderNumber <= 0) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }
        Course course = courseService.getCourseBySection(sectionId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }
        Section sectionModify = new Section(name, summary, null, null);
        sectionModify.setId(sectionId);
        if (!orderNumber.equals(orderNumberOld)) {
            sectionModify.setOrderNumber(orderNumber);
        }
        if (sectionService.modifySection(sectionModify, orderNumberOld, chapterId) == 0) {
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setSectionDetail(model, sectionService.getSectionBySectionId(sectionId));
        return "course/sectionDetail";
    }

    @GetMapping("delete/{sectionId}/section")
    public String deleteSection(@PathVariable() Integer sectionId,
                                Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseBySection(sectionId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }
        sectionService.deleteSection(section);
        Message.writeMessage(model, Message.SUCCESS);
        setChapterDetail(model, chapterService.getChapterByChapterId(section.getChapterId()));
        return "course/chapterDetail";
    }

    @PostMapping("add/{sectionId}/knowledge")
    public String addKnowledge(@PathVariable() Integer sectionId, @RequestParam("content") String[] content,
                               @RequestParam(value = "teacher", required = false) Integer[] teacher, @RequestParam("important") Integer[] important,
                               Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseBySection(sectionId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }

        int teacherLength = teacher == null ? 0 : teacher.length;
        int orderNumber = knowledgeService.getLargestKnowledgeOrderNumber(sectionId);
        List<Knowledge> list = new ArrayList<>();
        for (int i = 0, j = 0; i < content.length; i++) {
            if (StringUtil.isEmpty(content[i])) {
                break;
            }
            if (j < teacherLength && teacher[j] == i) {
                j++;
                list.add(new Knowledge(0, content[i], ++orderNumber, sectionId, important[i], 0));
            } else {
                list.add(new Knowledge(0, content[i], ++orderNumber, sectionId, important[i], 1));
            }
        }
        if (list.size() == 0) {
            Message.writeMessage(model, Message.PLEASE_INPUT_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }
        if (knowledgeService.insertKnowledge(list) != list.size()) {
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setSectionDetail(model, section);
            return "course/sectionDetail";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setSectionDetail(model, section);
        return "course/sectionDetail";
    }

    @GetMapping("choice/{sectionId}")
    public String viewChoice(@PathVariable() Integer sectionId, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        setChoiceQuestionDetail(model, section);
        return "course/choiceQuestionDetail";
    }

    @PostMapping("add/{sectionId}/choice")
    public String addChoice(@PathVariable() Integer sectionId, @RequestParam() String question,
                            @RequestParam() Integer isCorrect, @RequestParam() String[] option,
                            @RequestParam() String answerKey, Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseBySection(sectionId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setChoiceQuestionDetail(model, section);
            return "course/choiceQuestionDetail";
        }
        List<Option> list = new ArrayList<>();
        for (int i = 0; i < option.length; i++) {
            if (StringUtil.isEmpty(option[i])) {
                Message.writeMessage(model, Message.DATA_ERROR);
                setChoiceQuestionDetail(model, section);
                return "course/choiceQuestionDetail";
            }
            if (isCorrect == i) {
                list.add(new Option(option[i], 1));
            } else {
                list.add(new Option(option[i], 0));
            }
        }
        ChoiceQuestion choiceQuestion = new ChoiceQuestion(sectionId, question, answerKey, list);
        if (choiceQuestionService.insertQuestion(choiceQuestion) != 1) {
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setChoiceQuestionDetail(model, section);
            return "course/choiceQuestionDetail";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setChoiceQuestionDetail(model, section);
        return "course/choiceQuestionDetail";
    }
    @GetMapping("delete/{sectionId}/{questionId}/choice")
    public String deleteChoice(@PathVariable()Integer sectionId,@PathVariable()Integer questionId,
                               Model model,HttpServletRequest httpServletRequest){
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        if (section == null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseBySection(sectionId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setChoiceQuestionDetail(model, section);
            return "course/choiceQuestionDetail";
        }
        choiceQuestionService.deleteChoice(questionId);
        Message.writeMessage(model,Message.SUCCESS);
        setChoiceQuestionDetail(model,section);
        return  "course/choiceQuestionDetail";
    }
    @GetMapping("delete/{sectionId}/{knowledgeId}/knowledge")
    public String deleteKnowledge(@PathVariable()Integer sectionId,@PathVariable()Integer knowledgeId,
                                  Model model,HttpServletRequest httpServletRequest){
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Section section = sectionService.isSectionBelongToTeacher(sectionId, teacherId);
        Knowledge knowledge=knowledgeService.getKnowledgeById(knowledgeId);
        if (section == null || knowledge==null) {
            Message.writeMessage(model, Message.SECTION_NOT_YOURS_ERROR);
            setTeacherCourseModel(model, teacherId);
            return "course/index";
        }
        Course course = courseService.getCourseBySection(sectionId);
        if (course.getStartTime().before(new Date())) {
            Message.writeMessage(model, Message.COURSE_HAVE_START_ERROR);
            setSectionDetail(model,section);
            return "course/sectionDetail";
        }
        if(knowledgeService.deleteKnowledge(sectionId,knowledge)!=1){
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setSectionDetail(model,section);
            return "course/sectionDetail";
        }
        Message.writeMessage(model,Message.SUCCESS);
        setSectionDetail(model,section);
        return "course/sectionDetail";
    }
    private void setTeacherCourseModel(Model model, String teacherId) {
        model.addAttribute("list", courseService.getCourseByTeacher(teacherId));
        model.addAttribute("themeList", themeService.getAllTheme());
        model.addAttribute("typeList", typeArray);
    }

    private void setCourseDetailModel(Model model, Course course) {
        course.setImageSrc(courseImageHtml + course.getImageSrc());
        model.addAttribute("course", course);
        List<Chapter> list = chapterService.getChapterByCourse(course.getId());
        model.addAttribute("list", list);
        model.addAttribute("themeList", themeService.getAllTheme());
        model.addAttribute("typeList", typeArray);
    }

    private void setChapterDetail(Model model, Chapter chapter) {
        model.addAttribute("chapter", chapter);
        List<Section> list = sectionService.getSectionByChapter(chapter.getId());
        model.addAttribute("list", list);
    }

    private void setSectionDetail(Model model, Section section) {
        model.addAttribute("section", section);
        List<Knowledge> list = knowledgeService.getKnowledgeBySection(section.getId());
        model.addAttribute("list", list);
        model.addAttribute("arrayInt", arrayInt);
    }

    private void setChoiceQuestionDetail(Model model, Section section) {
        model.addAttribute("section", section);
        List<ChoiceQuestion> list = choiceQuestionService.getQuestionWithOptionBySection(section.getId());
        model.addAttribute("list", list);
    }
}
