-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2019-06-20 12:08:35
-- 服务器版本： 10.1.40-MariaDB
-- PHP 版本： 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+08:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `adweb_project`
--

-- --------------------------------------------------------

--
-- 表的结构 `chapter`
--

CREATE TABLE `chapter` (
  `id` int(11) NOT NULL COMMENT '章的id',
  `name` varchar(11) NOT NULL COMMENT '章名',
  `summary` varchar(100) NOT NULL COMMENT '章的简介',
  `order_number` int(11) NOT NULL COMMENT '章的顺序号',
  `course_id` int(11) NOT NULL COMMENT '章对应课程的id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='表示课程的章';

-- --------------------------------------------------------

--
-- 表的结构 `choice_question`
--

CREATE TABLE `choice_question` (
  `id` int(11) NOT NULL COMMENT '选择题id',
  `section_id` int(11) NOT NULL COMMENT '选择题对应的小节id',
  `content` varchar(100) NOT NULL COMMENT '选择题题目',
  `answer_key` varchar(100) NOT NULL COMMENT '答案解析'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='选择题表';

-- --------------------------------------------------------

--
-- 表的结构 `collection`
--

CREATE TABLE `collection` (
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `knowledge_id` int(31) NOT NULL COMMENT '知识点id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='表示用户收藏知识点的表';

-- --------------------------------------------------------

--
-- 表的结构 `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL COMMENT '课程的id',
  `name` varchar(31) NOT NULL COMMENT '课程名字',
  `summary` varchar(100) NOT NULL COMMENT '课程简介',
  `teacher_id` varchar(255) NOT NULL COMMENT '老师的id',
  `start_time` datetime NOT NULL COMMENT '课程开始时间',
  `end_time` datetime NOT NULL COMMENT '课程结束时间',
  `image_src` varchar(100) NOT NULL COMMENT '图片路径',
  `credit` int(11) NOT NULL COMMENT '学分数',
  `type` varchar(11) NOT NULL COMMENT '课程种类（如数学，物理）',
  `theme_id` int(31) NOT NULL COMMENT '主题id，表示属于哪个系列',
  `order_number` int(11) NOT NULL COMMENT '顺序号'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='存储课程的表';

-- --------------------------------------------------------

--
-- 表的结构 `course_selection`
--

CREATE TABLE `course_selection` (
  `student_id` varchar(255) NOT NULL COMMENT '学生id',
  `course_id` int(31) NOT NULL COMMENT '课程id',
  `status` int(1) NOT NULL COMMENT '课程完成状态 1表示已完成 0表示未完成',
  `chapter` int(11) DEFAULT NULL COMMENT '当前学到哪个章节的id',
  `section_id` int(11) DEFAULT NULL COMMENT '当前学到哪个小节'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='选课表';

-- --------------------------------------------------------

--
-- 表的结构 `knowledge`
--

CREATE TABLE `knowledge` (
  `id` int(11) NOT NULL COMMENT '知识点id',
  `type` int(11) NOT NULL COMMENT '知识点种类 0代表图片 1代表文本',
  `content` varchar(100) NOT NULL COMMENT '图片的话是url 文本的话就是对应内容',
  `order_number` int(11) NOT NULL COMMENT '知识点的顺序号',
  `section_id` int(11) NOT NULL COMMENT '知识点对应的小节id',
  `importance_degree` int(11) NOT NULL COMMENT '表示知识点重要程度 0 1 2 3 0最重要',
  `who_say` int(11) NOT NULL COMMENT '谁说的，0为老师，1为学生'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='知识点表';

-- --------------------------------------------------------

--
-- 表的结构 `note`
--

CREATE TABLE `note` (
  `user_id` varchar(170) NOT NULL COMMENT '用户id',
  `url` varchar(100) NOT NULL COMMENT '笔记对应的url',
  `content` blob NOT NULL COMMENT '笔记内容'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='存储笔记的表';

-- --------------------------------------------------------

--
-- 表的结构 `option`
--

CREATE TABLE `option` (
  `id` int(11) NOT NULL COMMENT '选项id',
  `question_id` int(11) NOT NULL COMMENT '选项对应问题的id',
  `content` varchar(100) NOT NULL COMMENT '选项内容',
  `is_correct` int(11) NOT NULL COMMENT '是否为正确选项 0表示不正确 1表示是正确选项'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='选择题题目选项的表';

-- --------------------------------------------------------

--
-- 表的结构 `question_answer`
--

CREATE TABLE `question_answer` (
  `student_id` varchar(255) NOT NULL COMMENT '学生的id',
  `question_id` int(11) NOT NULL COMMENT '题目的id',
  `option_id` int(11) NOT NULL COMMENT '学生选择选项的id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='记录学生题目答案的表';

-- --------------------------------------------------------

--
-- 表的结构 `section`
--

CREATE TABLE `section` (
  `id` int(11) NOT NULL COMMENT '小节的id',
  `name` varchar(31) NOT NULL COMMENT '小节的名字',
  `summary` varchar(100) NOT NULL COMMENT '小节的摘要',
  `order_number` int(11) NOT NULL COMMENT '小节的顺序号',
  `chapter_id` int(11) NOT NULL COMMENT '小节对应的章节id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='表示课程的小节';

-- --------------------------------------------------------

--
-- 表的结构 `student`
--

CREATE TABLE `student` (
  `open_id` varchar(255) NOT NULL COMMENT '用户id 即微信openid',
  `name` varchar(31) NOT NULL COMMENT '学生姓名',
  `email` varchar(31) NOT NULL COMMENT '登录邮箱',
  `sex` varchar(31) NOT NULL COMMENT '性别 male或female',
  `revised_credits` int(31) NOT NULL COMMENT '已修学分'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='学生表';

-- --------------------------------------------------------

--
-- 表的结构 `teacher`
--

CREATE TABLE `teacher` (
  `open_id` varchar(255) NOT NULL COMMENT '老师的id',
  `name` varchar(31) NOT NULL COMMENT '老师的名字',
  `password` varchar(255) NOT NULL COMMENT '登录密码',
  `email` varchar(31) NOT NULL COMMENT '登录邮箱'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 表的结构 `teacher_avatar`
--

CREATE TABLE `teacher_avatar` (
  `teacher_id` varchar(255) NOT NULL,
  `image_src` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `theme`
--

CREATE TABLE `theme` (
  `id` int(11) NOT NULL COMMENT '主题id',
  `name` varchar(11) NOT NULL COMMENT '主题名字',
  `summary` varchar(100) NOT NULL COMMENT '主题简介',
  `image_url` varchar(100) NOT NULL COMMENT '主题的图片路径'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='主题表';

--
-- 转储表的索引
--

--
-- 表的索引 `chapter`
--
ALTER TABLE `chapter`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `choice_question`
--
ALTER TABLE `choice_question`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `collection`
--
ALTER TABLE `collection`
  ADD PRIMARY KEY (`user_id`,`knowledge_id`);

--
-- 表的索引 `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `course_selection`
--
ALTER TABLE `course_selection`
  ADD PRIMARY KEY (`student_id`,`course_id`);

--
-- 表的索引 `knowledge`
--
ALTER TABLE `knowledge`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`user_id`,`url`);

--
-- 表的索引 `option`
--
ALTER TABLE `option`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `question_answer`
--
ALTER TABLE `question_answer`
  ADD PRIMARY KEY (`student_id`,`question_id`,`option_id`);

--
-- 表的索引 `section`
--
ALTER TABLE `section`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`open_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- 表的索引 `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`open_id`);

--
-- 表的索引 `theme`
--
ALTER TABLE `theme`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `chapter`
--
ALTER TABLE `chapter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '章的id';

--
-- 使用表AUTO_INCREMENT `choice_question`
--
ALTER TABLE `choice_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '选择题id';

--
-- 使用表AUTO_INCREMENT `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程的id';

--
-- 使用表AUTO_INCREMENT `knowledge`
--
ALTER TABLE `knowledge`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '知识点id';

--
-- 使用表AUTO_INCREMENT `option`
--
ALTER TABLE `option`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '选项id';

--
-- 使用表AUTO_INCREMENT `section`
--
ALTER TABLE `section`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小节的id';

--
-- 使用表AUTO_INCREMENT `theme`
--
ALTER TABLE `theme`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主题id';
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
