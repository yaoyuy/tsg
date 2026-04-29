CREATE DATABASE IF NOT EXISTS library_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE library_management;

-- 1. 管理员表
CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. 读者表
CREATE TABLE reader (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(100) DEFAULT '123456',
    gender VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(100),
    department VARCHAR(100),
    max_borrow_count INT DEFAULT 5,
    status INT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3. 图书分类表
CREATE TABLE book_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL,
    parent_id BIGINT DEFAULT 0,
    sort_order INT DEFAULT 0,
    description TEXT
);

-- 4. 图书信息表
CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100),
    publisher VARCHAR(100),
    publish_date DATE,
    price DECIMAL(10, 2),
    category_id BIGINT,
    total_count INT DEFAULT 1,
    available_count INT DEFAULT 1,
    location VARCHAR(100),
    description TEXT,
    keywords VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES book_category(id)
);

-- 5. 图书详情表（一对一关系）
CREATE TABLE book_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT NOT NULL UNIQUE,
    page_count INT,
    language VARCHAR(50),
    edition VARCHAR(50),
    format VARCHAR(50),
    weight DECIMAL(8, 2),
    dimensions VARCHAR(100),
    summary TEXT,
    FOREIGN KEY (book_id) REFERENCES book(id)
);

-- 6. 借阅记录表
CREATE TABLE borrow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    fine DECIMAL(10, 2) DEFAULT 0,
    status INT DEFAULT 0,
    remark TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reader_id) REFERENCES reader(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

-- 7. 预约记录表
CREATE TABLE reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    reserve_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    expire_date DATETIME,
    status INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reader_id) REFERENCES reader(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

-- 8. 标签表
CREATE TABLE tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- 9. 图书标签关联表（多对多关系）
CREATE TABLE book_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id),
    UNIQUE KEY uk_book_tag (book_id, tag_id)
);

-- 10. 公告表
CREATE TABLE announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    publisher_id BIGINT,
    publish_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status INT DEFAULT 1,
    FOREIGN KEY (publisher_id) REFERENCES admin(id)
);

-- 11. 收藏表（用户收藏图书）
CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reader_id) REFERENCES reader(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    UNIQUE KEY uk_reader_book (reader_id, book_id)
);

-- 12. 评价表（用户对图书评价）
CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    rating INT NOT NULL,
    content TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status INT DEFAULT 1,
    FOREIGN KEY (reader_id) REFERENCES reader(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

-- 13. 消息通知表
CREATE TABLE message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    is_read INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reader_id) REFERENCES reader(id)
);

-- 14. 罚款记录表
CREATE TABLE fine_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    borrow_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    is_paid INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reader_id) REFERENCES reader(id),
    FOREIGN KEY (borrow_id) REFERENCES borrow_record(id)
);

-- 插入测试数据
INSERT INTO admin (username, password, real_name, phone, email) VALUES
('admin', 'admin123', '系统管理员', '13800138000', 'admin@library.com');

INSERT INTO book_category (category_name, parent_id, sort_order, description) VALUES
('计算机', 0, 1, '计算机相关图书'),
('文学', 0, 2, '文学作品'),
('历史', 0, 3, '历史类图书'),
('科学', 0, 4, '自然科学'),
('编程语言', 1, 1, '编程语言相关'),
('数据库', 1, 2, '数据库相关'),
('小说', 2, 1, '小说类'),
('散文', 2, 2, '散文类');

INSERT INTO book (isbn, title, author, publisher, publish_date, price, category_id, total_count, available_count, location, description, keywords) VALUES
('9787111544937', 'Java核心技术', 'Cay S. Horstmann', '机械工业出版社', '2016-09-01', 119.00, 5, 3, 3, 'A区101', 'Java编程经典教材', 'Java,编程,核心技术'),
('9787115428660', 'Python编程从入门到实践', 'Eric Matthes', '人民邮电出版社', '2016-07-01', 89.00, 5, 5, 5, 'A区102', 'Python入门经典', 'Python,编程,入门'),
('9787111280798', 'MySQL必知必会', 'Ben Forta', '机械工业出版社', '2009-01-01', 45.00, 6, 4, 4, 'A区103', 'MySQL入门经典', 'MySQL,数据库,SQL'),
('9787506365734', '活着', '余华', '作家出版社', '2012-08-01', 20.00, 7, 6, 6, 'B区201', '余华代表作', '小说,文学,人生'),
('9787108009821', '百年孤独', '加西亚·马尔克斯', '南海出版公司', '2011-06-01', 39.50, 7, 4, 4, 'B区202', '魔幻现实主义代表作', '小说,文学,魔幻'),
('9787101009828', '史记', '司马迁', '中华书局', '1959-09-01', 280.00, 3, 2, 2, 'C区301', '中国第一部纪传体通史', '历史,史记,古代'),
('9787030309860', '时间简史', '史蒂芬·霍金', '科学出版社', '2010-04-01', 45.00, 4, 3, 3, 'D区401', '宇宙学经典科普', '科学,宇宙,物理'),
('9787115504531', '深入理解Java虚拟机', '周志明', '机械工业出版社', '2019-12-01', 129.00, 5, 2, 2, 'A区104', 'JVM深入解析', 'Java,JVM,虚拟机');

INSERT INTO book_detail (book_id, page_count, language, edition, format, weight, dimensions, summary) VALUES
(1, 780, '中文', '第10版', '平装', 1.2, '210x148mm', '全面介绍Java核心特性'),
(2, 456, '中文', '第1版', '平装', 0.8, '210x148mm', 'Python编程入门指南'),
(3, 210, '中文', '第1版', '平装', 0.4, '210x148mm', 'MySQL基础教程'),
(4, 191, '中文', '第1版', '平装', 0.3, '210x148mm', '讲述福贵的一生'),
(5, 360, '中文', '第1版', '精装', 0.5, '210x148mm', '布恩迪亚家族七代人的传奇'),
(6, 1200, '中文', '第1版', '精装', 2.0, '260x185mm', '中国历史经典著作'),
(7, 250, '中文', '第1版', '平装', 0.4, '210x148mm', '探索宇宙奥秘'),
(8, 500, '中文', '第3版', '平装', 0.9, '210x148mm', '深入理解JVM原理');

INSERT INTO tag (tag_name, description) VALUES
('热门', '热门借阅图书'),
('推荐', '编辑推荐'),
('新书', '新入库图书'),
('经典', '经典著作'),
('教材', '教学用书');

INSERT INTO book_tag (book_id, tag_id) VALUES
(1, 4), (1, 5), (2, 1), (2, 3), (3, 4), (4, 4), (5, 4), (6, 4), (7, 2), (8, 1), (8, 5);

INSERT INTO reader (reader_no, name, password, gender, phone, email, department, max_borrow_count) VALUES
('R2023001', '张三', '123456', '男', '13800138001', 'zhangsan@edu.com', '计算机学院', 5),
('R2023002', '李四', '123456', '女', '13800138002', 'lisi@edu.com', '文学院', 5),
('R2023003', '王五', '123456', '男', '13800138003', 'wangwu@edu.com', '历史学院', 8);

INSERT INTO announcement (title, content, publisher_id, status) VALUES
('图书馆开放时间调整', '因节假日安排，图书馆开放时间调整为9:00-17:00', 1, 1),
('新书入库通知', '本月新增计算机类图书50册，欢迎借阅', 1, 1);
