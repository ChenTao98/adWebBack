# 微信小程序教师端

陈涛 16302010026 复旦大学16级软件工程 

---

### 教师端使用说明:[教师端使用说明](https://github.com/ChenTao98/adWebBack/blob/master/%E6%95%99%E5%B8%88%E7%AB%AF%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md)

---

### 1. 项目组织及文件说明

```
│          
├─db
│     adweb_project.sql                                  数据库建表sql语句
│      
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com.adweb.adweb
│  │  │          │  AdWebApiApplication.java            入口类
│  │  │          │  
│  │  │          │-config                               SpringBoot 配置类的包
│  │  │          │      AuthConfig                      教师授权认证的配置类
│  │  │          │      
│  │  │          │      
│  │  │          ├─controllor                           控制类的包
│  │  │          │      CourseController.java           添加或修改课程、章节、小节、选择题的控制类
│  │  │          │      LoginRgisterController.java     教师登录注册的控制类
│  │  │          │      MyInfoController.java           教师个人信息展示、修改的控制类
│  │  │          │      StudentController.java          查看选课学生、学生作业的控制类
│  │  │          │      TestCon.java                    学习使用图灵机器人的问答接口，已经丢弃
│  │  │          │      
│  │  │          │      
│  │  │          ├─dao                                  数据库操作接口类的包
│  │  │          │      ChapterDao.java                 章节 数据库表的访问操作接口
│  │  │          │      ChoiceQuestionDao.java          选择题题目 数据库表的访问操作接口
│  │  │          │      CollectionDao.java              收藏 数据库表的访问操作接口
│  │  │          │      CourseDao.java                  课程 数据库表的访问操作接口
│  │  │          │      CourseSelectionDao.java         学生选课 数据库表的访问操作接口
│  │  │          │      CourseStudentDao.java           学生与课程 的数据库表访问操作接口
│  │  │          │      KnowledgeDao.java               对话 数据库表的访问操作接口
│  │  │          │      NoteDao.java                    笔记 数据库表的访问操作接口
│  │  │          │      OptionDao.java                  选择题选项 数据库表的访问操作接口
│  │  │          │      QuestionAnswerDao.java          学生答题答案 数据库表的访问操作接口
│  │  │          │      SectionDao.java                 小节 数据库表的访问操作接口
│  │  │          │      StudentDao.java                 学生 数据库表的访问操作接口
│  │  │          │      TeacherAvatarDao.java           教师头像 数据库表的访问操作接口
│  │  │          │      TeacherDao.java                 教师 数据库表的访问操作接口
│  │  │          │      Theme.java                      课程主题 数据库表的访问操作接口
│  │  │          │      
│  │  │          │      
│  │  │          ├─entity                               实体类的包，包含数据库实体类以及操作工具Example类
│  │  │          │  |
│  │  │          │  └─courseStudent                     学生与课程相关的实体类包
│  │  │          │          CourseStudentSelect.java    选某门课的课程与学生信息
│  │  │          │          StudentChoice.java          学生的某个小节的作业信息
│  │  │          │      
│  │  │          │      Chapter.java                    章节实体类
│  │  │          │      ChapterExample.java
│  │  │          │      ChoiceQuestion.java             选择题 表的实体类
│  │  │          │      ChoiceQuestionExample.java
│  │  │          │      CollectionKey.java              收藏 表的实体类
│  │  │          │      CollectionExample.java
│  │  │          │      Course.java                     课程 表的实体类
│  │  │          │      CourseExample.java
│  │  │          │      CourseSelection.java            学生选课 表的实体类
│  │  │          │      CourseSelectionExample.java
│  │  │          │      CourseSelectionKey.java
│  │  │          │      Knowledge.java                  知识 表的实体类
│  │  │          │      KnowledgeExample.java
│  │  │          │      Note.java                       笔记 表的实体类
│  │  │          │      NoteExample.java
│  │  │          │      NoteKey.java
│  │  │          │      Option.java                     选择题选项 表的实体类
│  │  │          │      OptionExample.java
│  │  │          │      QuestionAnswer.java             学生答题答案 表的实体类
│  │  │          │      QuestionAnswerExample.java
│  │  │          │      Section.java                    小节 表的实体类
│  │  │          │      SectionExample.java
│  │  │          │      Student.java                    学生 表的实体类
│  │  │          │      StudentExample.java      
│  │  │          │      Teacher.java                    老师 表的实体类
│  │  │          │      TeacherExample.java
│  │  │          │      TeacherAvatar.java              老师头像 表的实体类
│  │  │          │      TeacherAvatarExample.java
│  │  │          │      Theme.java                      课程主题 表的实体类
│  │  │          │      ThemeExample.java
│  │  │          │      
│  │  │          │      
│  │  │          ├─service                              servie层的接口以及实现类，用于连接控制层与dao层
│  │  │          │  |
│  │  │          │  └─impl                              接口类的实现包，不展开了
│  │  │          │  |
│  │  │          │  └─Tl                                学习图灵机器人问答，已丢弃
│  │  │          │      
│  │  │          │      ChapterService.java             章节 的Service
│  │  │          │      ChoiceQuestionService           选择题  的Service
│  │  │          │      CourseService                   课程 的Service
│  │  │          │      CourseStudentService            学生选课  的Service
│  │  │          │      KnowledgeService                对话知识点 的Service
│  │  │          │      SectionService                  小节 的Service
│  │  │          │      TeacherAvatarService            教师头像  的Service
│  │  │          │      TeacherService                  教师  的Service
│  │  │          │      ThemeService                    主题  的Service
│  │  │          │      
│  │  │          │      
│  │  │          └─util
│  │  │                  Message.java                   封装错误信息的枚举类
│  │  │                  PathUtil.java                  静态路径的工具类，方便修改路径与部署
│  │  │                  SessionUtil.java               从Session获取教师id的工具类
│  │  │                  StringUtil                     字符串操作工具类
│  │  │                  
│  │  └─resources
│  │      │      application.yml                            应用的配置文件
│  │      │  
│  │      ├─mapper                                      对应dao包接口的实现文件
│  |      │      ChapterDao.xml                         章节 数据库表的操作接口实现文件
│  |      │      ChoiceQuestionDao.xml                  选择题题目 数据库表的操作接口实现文件
│  |      │      CollectionDao.xml                      收藏 数据库表的操作接口实现文件
│  |      │      CourseDao.xml                          课程 数据库表的操作接口实现文件
│  |      │      CourseSelectionDao.xml                 学生选课 数据库表的操作接口实现文件
│  |      │      CourseStudentDao.xml                   学生与课程 的数据库表访问操作接口
│  |      │      KnowledgeDao.xml                       对话 数据库表的操作接口实现文件
│  |      │      NoteDao.xml                            笔记 数据库表的操作接口实现文件
│  |      │      OptionDao.xml                          选择题选项 数据库表的操作接口实现文件
│  |      │      QuestionAnswerDao.xml                  学生答题答案 数据库表的操作接口实现文件
│  |      │      SectionDao.xml                         小节 数据库表的操作接口实现文件
│  |      │      StudentDao.xml                         学生 数据库表的操作接口实现文件
│  |      │      TeacherAvatarDao.xml                   教师头像 数据库表的操作接口实现文件
│  |      │      TeacherDao.xml                         教师 数据库表的操作接口实现文件
│  |      │      Theme.xml                              课程主题 数据库表的操作接口实现文件
│  │      │  
│  │      ├─static                                      存放静态文件的文件夹
│  │      |     ├─css                                   存放前端显示的css文件
│  │      |     ├─image                                 存放静态图片
│  │      |     ├─js                                    存放js文件
│  │      │  
│  │      ├─templates                                   存放前端显示的html文件
│  │      |     ├─course                                课程相关的html文件
│  │      |     |   chapterDetail.html                  显示章节详细内容
│  │      |     |   choiceQuestionDetail.html           显示选择题详细内容
│  │      |     |   courseDetail.html                   显示课程详细内容
│  │      |     |   indexDetail.html                    显示教师开的所有课程
│  │      |     |   sectionDetail.html                  显示小节详细内容
│  │      |     |   
│  │      |     ├─header                                头部与尾部的html文件
│  │      |     |   header.html                         头部html
│  │      |     |   footer.html                         尾部html
│  │      |     |   
│  │      |     ├─login                                 登录界面的html
│  │      |     |   index.html                          登录界面的html
│  │      |     |   
│  │      |     ├─myInfo                                个人信息界面的html
│  │      |     |   index.html                          个人信息界面的html
│  │      |     |   
│  │      |     ├─student                               选课学生界面的html
│  │      |     |   choiceOneStudent.html               显示学生做的某一小节的选择题
│  │      |     |   courseOneStudent.html               显示一个学生关于该课程的所有作业
│  │      |     |   couresSelect.html                   显示选择某一门课程的所有学生
│  │      |     |   index.html                          显示教师的所有课程
│  │      |     |   

```

---

### 2. 关键功能实现

+ 登录、注册
    + 登录访问后台时，通过SpringBoot的Security拦截请求，判断教师是否登录，没有的话会强制登录
    + 登录之后会授予用户页面访问权限，并添加Session，用于存储当前登录用户的信息，登录期间每次操作都会获取Session的信息用于操作
    + 教师如果没有账户，可以注册账户，注册账户上会判断邮箱是否存在
+ 个人信息
    + 教师可以查看个人信息
    + 教师可以修改个人信息、头像
+ 创建/修改课程、章节、小节、对话、选择题
    + 教师可以在我的课程界面输入必要的信息创建课程，并在课程里面创建对应的章节、小节、对话、选择题
    + 教师可以修改对应的课程、章节、小节、对话信息，并可以修改章节、小节、对话顺序，以调整课程的信息
    + 修改顺序时，会先修改其他信息的顺序号，对其进行加一或者减一，最后再修改本条消息的顺序号
    + 教师只可以查看、修改属于自己的课程信息，每次查看、修改、删除操作会获取Session的教师信息，进行比对，如果不匹配，则返回当前教师的课程界面
    + 教师可以删除对应的课程、章节、小节、对话、选择题，删除后，会删除对应的下级信息
    + 如果教师课程已经开课，则不允许教师进行删除、修改操作
+ 查看学生选课、作业情况
    + 教师可以查看某门课程的选课情况，可以看到所以选择该课程的学生
    + 教师可以查看某个学生的对某门课程的所有作业完成汇总
    + 教师可以查看某个学生对某个小节的作业的详细情况

---

### 3. 关键技术

+ 使用SpringBoot实现Restful服务，前端发送请求，后端实现响应
+ 使用Mybatis进行数据库操作
+ 使用Security进行权限认证
+ 前端使用Thymeleaf模板进行页面数据填充
+ 前端使用BootStrap框架样式