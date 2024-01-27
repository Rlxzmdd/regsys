use regsys_admin;

### 学院信息表
drop table if exists entity_college;
create table entity_college
(
    id          bigint unsigned auto_increment comment '记录ID'
        primary key,
    create_time datetime         default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime         default CURRENT_TIMESTAMP null comment '记录更新时间',
    coll_name   varchar(30)                                not null comment '学院全称',
    coll_abbr   varchar(30)                                not null comment '学院简称',
    create_user int unsigned                               null comment '创建记录用户',
    update_user int unsigned                               null comment '更新记录用户',
    mark        tinyint unsigned default '1'               null comment '记录有效性'
)
    comment '学院信息表';
create index idx_id
    on entity_college (id);
insert into entity_college (coll_name, coll_abbr, create_user, update_user)
values
    ('计算机科学与技术学院', '计科院', 1, 1),
    ('文学与艺术学院', '文艺院', 2, 2),
    ('商学院', '商院', 3, 3),
    ('外语学院', '外语院', 4, 4),
    ('理学院', '理院', 5, 5);

### 部门信息表
drop table if exists entity_department;
create table entity_department
(
    id          bigint unsigned auto_increment comment '记录ID'
        primary key,
    create_time datetime         default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime         default CURRENT_TIMESTAMP null comment '记录更新时间',
    college_id  bigint unsigned  default '0'               null comment '部门所属学院->entity_college',
    dep_name    varchar(30)                                not null comment '部门全称',
    dep_abbr    varchar(30)                                not null comment '部门简称',
    create_user int unsigned                               null comment '创建记录用户',
    update_user int unsigned                               null comment '更新记录用户',
    mark        tinyint unsigned default '1'               null comment '记录有效性'
)
    comment '部门信息表';
create index idx_id
    on entity_department (id);
insert into entity_department (college_id, dep_name, dep_abbr, create_user, update_user)
values
    (1, '计算机科学与技术系', '计科系', 1, 1),
    (2, '文学系', '文学系', 2, 2),
    (3, '市场营销部', '市场部', 3, 3),
    (4, '英语教研室', '英语室', 4, 4),
    (5, '数学系', '数学系', 5, 5);

### 专业信息表
drop table if exists entity_major;
create table entity_major
(
    id          bigint unsigned auto_increment comment '记录ID'
        primary key,
    maj_name    varchar(30)                                not null comment '专业全称',
    maj_abbr    varchar(30)                                not null comment '专业简称',
    college_id  bigint unsigned  default '0'               null comment '专业所属学院->entity_college',
    create_time datetime         default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime         default CURRENT_TIMESTAMP null comment '更新时间',
    create_user int unsigned                               null comment '创建记录用户',
    update_user int unsigned                               null comment '更新记录用户',
    mark        tinyint unsigned default '1'               null comment '记录有效性'
)
    comment '专业信息表';
create index idx_id
    on entity_major (id);
insert into entity_major (maj_name, maj_abbr, college_id, create_user, update_user)
values
    ('计算机科学与技术', '计科', 1, 1, 1),
    ('汉语言文学', '汉语', 2, 2, 2),
    ('市场营销', '市场', 3, 3, 3),
    ('英语', '英语', 4, 4, 4),
    ('数学与应用数学', '数学', 5, 5, 5);

### 班级信息表
drop table if exists entity_classes;
create table entity_classes
(
    id               bigint unsigned auto_increment comment '记录ID'
        primary key,
    class_name       varchar(30)                                not null comment '班级名称',
    teacher_number   varchar(15)                                null comment '班主任工号',
    counselor_number varchar(15)                                null comment '辅导员工号',
    assistant_number varchar(15)                                null comment '助辅学号',
    college_id       bigint unsigned  default '0'               null comment '所属学院索引->entity_college',
    major_id         bigint unsigned  default '0'               null comment '所属专业索引->entity_major',
    create_time      datetime         default CURRENT_TIMESTAMP null comment '创建时间',
    update_time      datetime         default CURRENT_TIMESTAMP null comment '更新时间',
    create_user      int unsigned                               null comment '创建记录用户',
    update_user      int unsigned                               null comment '更新记录用户',
    mark             tinyint unsigned default '1'               null comment '记录有效性'
)
    comment '班级信息表';
create index idx_id
    on entity_classes (id);
-- 班级信息表 (entity_classes)
insert into entity_classes (class_name, teacher_number, counselor_number, assistant_number, college_id, major_id, create_user, update_user)
values
    ('计算机科学与技术2021班', 'TCH001', 'COUN001', 'ASSIST001', 1, 1, 1, 1),
    ('汉语言文学2022班', 'TCH002', 'COUN002', 'ASSIST002', 2, 2, 2, 2),
    ('市场营销2023班', 'TCH003', 'COUN003', 'ASSIST003', 3, 3, 3, 3),
    ('英语2022班', 'TCH004', 'COUN004', 'ASSIST004', 4, 4, 4, 4),
    ('数学与应用数学2021班', 'TCH005', 'COUN005', 'ASSIST005', 5, 5, 5, 5);


### 学生信息表
drop table if exists user_student;
# 为在查询和管理学生、班级、学院和专业的数据时具有良好的灵活性和效率，故添加学院、专业索引
create table user_student
(
    id            bigint unsigned auto_increment comment '记录ID'
        primary key,
    real_name     varchar(30)                                null comment '学生姓名',
    stu_number    varchar(15)                                not null comment '学生学号',
    phone         varchar(11)                                null comment '手机号',
    id_card       varchar(20)                                null comment '身份证号',
    gender        varchar(3)                                 null comment '性别',
    nation        varchar(10)                                null comment '民族',
    email         varchar(30)                                null comment '邮箱',
    qq            varchar(30)                                null comment 'QQ号',
    politic       varchar(8)                                 null comment '政治面貌',
    serial_number varchar(30)                                null comment '通知书编号',
    exam_number   varchar(30)                                null comment '考生号',
    stu_status    varchar(10)                                null comment '学生状态',
    college_id    bigint unsigned  default '0'               null comment '所属学院索引->entity_college',
    major_id      bigint unsigned  default '0'               null comment '所属专业索引->entity_major',
    classes_id    bigint unsigned                            null comment '所属班级索引->entity_classes',
    avatar        varchar(40)                                null comment '学生头像照片',
    create_time   datetime         default CURRENT_TIMESTAMP null comment '记录创建时间',
    update_time   datetime         default CURRENT_TIMESTAMP null comment '记录更新时间',
    create_user   int unsigned                               null comment '创建记录用户',
    update_user   int unsigned                               null comment '更新记录用户',
    mark          tinyint unsigned default '1'               null comment '记录有效性'
)
    comment '学生信息表';
create index idx_stu_number
    on user_student (stu_number);
-- 学生信息表 (user_student)
insert into user_student (real_name, stu_number, phone, id_card, gender, nation, email, qq, politic, serial_number, exam_number, stu_status, college_id, major_id, classes_id, avatar, create_user, update_user)
values
    ('张三', 'S2021001', '13812345678', '320123456789012345', '男', '汉族', 'zhangsan@example.com', '123456', '共青团员', '2021001', '2021001', '在校', 1, 1, 1, 'avatar1.jpg', 1, 1),
    ('李四', 'S2021002', '13912345678', '320123456789012346', '女', '汉族', 'lisi@example.com', '234567', '党员', '2021002', '2021002', '在校', 2, 2, 2, 'avatar2.jpg', 2, 2),
    ('王五', 'S2021003', '13612345678', '320123456789012347', '男', '汉族', 'wangwu@example.com', '345678', '团员', '2021003', '2021001', '在校', 1, 1, 1, 'avatar3.jpg', 1, 1),
    ('赵六', 'S2021004', '13512345678', '320123456789012348', '女', '汉族', 'zhaoliu@example.com', '456789', '群众', '2021004', '2021002', '在校', 2, 2, 2, 'avatar4.jpg', 2, 2),
    ('钱七', 'S2021005', '13712345678', '320123456789012349', '男', '汉族', 'qianqi@example.com', '567890', '党员', '2021005', '2021001', '在校', 1, 1, 1, 'avatar5.jpg', 1, 1),
    ('张八', 'S2021006', '13812345679', '320123456789012350', '男', '汉族', 'zhangba@example.com', '678901', '共青团员', '2021006', '2021001', '在校', 1, 1, 2, 'avatar6.jpg', 1, 1),
    ('李九', 'S2021007', '13912345679', '320123456789012351', '女', '汉族', 'lijiu@example.com', '789012', '党员', '2021007', '2021002', '在校', 2, 2, 2, 'avatar7.jpg', 2, 2),
    ('王十', 'S2021008', '13612345679', '320123456789012352', '男', '汉族', 'wangshi@example.com', '890123', '团员', '2021008', '2021001', '在校', 1, 1, 3, 'avatar8.jpg', 1, 1),
    ('赵十一', 'S2021009', '13512345679', '320123456789012353', '女', '汉族', 'zhaoyiyi@example.com', '901234', '群众', '2021009', '2021002', '在校', 2, 2, 3, 'avatar9.jpg', 2, 2),
    ('钱十二', 'S2021010', '13712345679', '320123456789012354', '男', '汉族', 'qianerbai@example.com', '012345', '党员', '2021010', '2021001', '在校', 1, 1, 4, 'avatar10.jpg', 1, 1),
    ('孙十三', 'S2021011', '13812345680', '320123456789012355', '女', '汉族', 'sunsansan@example.com', '123456', '共青团员', '2021011', '2021002', '在校', 2, 2, 4, 'avatar11.jpg', 2, 2),
    ('周十四', 'S2021012', '13912345680', '320123456789012356', '男', '汉族', 'zhou1414@example.com', '234567', '党员', '2021012', '2021001', '在校', 1, 1, 5, 'avatar12.jpg', 1, 1),
    ('吴十五', 'S2021013', '13612345680', '320123456789012357', '女', '汉族', 'wuwuwu@example.com', '345678', '团员', '2021013', '2021002', '在校', 2, 2, 5, 'avatar13.jpg', 2, 2),
    ('郑十六', 'S2021014', '13512345680', '320123456789012358', '男', '汉族', 'zhengzheng@example.com', '456789', '群众', '2021014', '2021001', '在校', 1, 1, 6, 'avatar14.jpg', 1, 1),
    ('陈十七', 'S2021015', '13712345680', '320123456789012359', '女', '汉族', 'chenshishi@example.com', '567890', '党员', '2021015', '2021002', '在校', 2, 2, 6, 'avatar15.jpg', 2, 2);


### 教师信息表
drop table if exists user_teacher;
create table user_teacher
(
    id            bigint unsigned auto_increment comment '记录ID'
        primary key,
    real_name     varchar(30)                                null comment '教师姓名',
    tch_number    varchar(15)                                not null comment '教师工号',
    gender        varchar(3)                                 null comment '性别',
    phone         varchar(11)                                null comment '联系电话',
    email         varchar(30)                                null comment '邮箱',
    qq            varchar(30)                                null comment 'QQ号',
    office        varchar(100)                               null comment '办公室位置',
    password      varchar(50)                                null comment '登录密码',
    identity      varchar(10)      default '教师'            null comment '教师其他职责身份',
    college_id    bigint unsigned  default '0'               null comment '所属学院索引->entity_college',
    department_id bigint unsigned  default '0'               null comment '部门索引->entity_department',
    create_user   int unsigned                               null comment '创建记录用户',
    update_user   int unsigned                               null comment '更新记录用户',
    update_time   datetime         default CURRENT_TIMESTAMP null comment '更新时间',
    create_time   datetime         default CURRENT_TIMESTAMP null comment '创建时间',
    mark          tinyint unsigned default '1'               null comment '记录有效性'
)
    comment '教师信息表';
create index idx_tch_number
    on user_teacher (tch_number);
insert into user_teacher (real_name, tch_number, gender, phone, email, qq, office, password, identity, college_id, department_id, create_user, update_user)
values
    ('张老师', 'TCH001', '男', '13898765432', 'zhangteacher@example.com', '123456', '教师办公室A', 'password1', '普通教师', 1, 1, 1, 1),
    ('李老师', 'TCH002', '女', '13998765432', 'liteacher@example.com', '234567', '教师办公室B', 'password2', '普通教师', 2, 2, 2, 2),
    ('王老师', 'TCH003', '男', '13698765432', 'wangteacher@example.com', '345678', '教师办公室C', 'password3', '普通教师', 3, 3, 3, 3),
    ('赵老师', 'TCH004', '女', '13598765432', 'zhaoteacher@example.com', '456789', '教师办公室D', 'password4', '普通教师', 4, 4, 4, 4),
    ('钱老师', 'TCH005', '男', '13798765432', 'qianteacher@example.com', '567890', '教师办公室E', 'password5', '普通教师', 5, 5, 5, 5);



### 微信用户绑定表
drop table if exists user_wechat;
create table user_wechat
(
    id          bigint unsigned auto_increment comment '记录ID'
        primary key,
    open_id     varchar(200)                               not null comment '微信小程序用户ID',
    union_id    varchar(200)                               null comment '微信用户识别ID',
    session_key varchar(200)                               null comment '微信小程序用户Key',
    wechat_name varchar(50)                                null comment '微信帐号名称',
    user_id     varchar(200)                               null comment '绑定信息ID(学生、教师)->user_student/user_teacher',
    user_type   tinyint(1) default '0'                     null comment '类型：0学生 1教师',
    create_user int unsigned                               null comment '创建记录用户',
    update_user int unsigned                               null comment '更新记录用户',
    mark        tinyint unsigned default '1'               null comment '记录有效性',
    create_time datetime         default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime         default CURRENT_TIMESTAMP null comment '更新时间'
)
    comment '微信用户绑定表';

create index idx_open_id
    on user_wechat (open_id);

