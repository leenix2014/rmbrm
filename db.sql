GRANT ALL PRIVILEGES ON rmbrm.* TO 'rmbrm'@'localhost'IDENTIFIED BY 'Hongxinrmbrm';
create database rmbrm;

DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
ID INT NOT NULL AUTO_INCREMENT COMMENT '用户ID,User ID',
PHONE VARCHAR(255) NOT NULL COMMENT '用户电话，Phone',
PWD  VARCHAR(255) NOT NULL COMMENT '用户密码，User Password',
NAME VARCHAR(255) COMMENT '用户名,User Name',
WE_CHAT VARCHAR(255) COMMENT '用户微信',
CREATE_TIME DATETIME NOT NULL COMMENT '创建时间',
PRIMARY KEY(ID),
UNIQUE KEY `UNI_PHONE`(PHONE)
)COMMENT'用户表';

DROP TABLE IF EXISTS EXCHANGE;
CREATE TABLE EXCHANGE
(
ID INT NOT NULL AUTO_INCREMENT COMMENT '用户ID,User ID',
PHONE VARCHAR(255) NOT NULL COMMENT '用户电话，Phone',
EX_RATE DECIMAL(5,4) NOT NULL DEFAULT 1.55 COMMENT '马币兑人民币汇率',
BUY BOOLEAN NOT NULL DEFAULT true COMMENT 'true=买入马币，false=卖出马币',
AMOUNT INT NOT NULL DEFAULT 0 COMMENT '马币交易金额',
CREATE_TIME DATETIME NOT NULL COMMENT '创建时间',
END_TIME DATETIME COMMENT '结束时间',
PRIMARY KEY(ID)
)COMMENT'人民币马币交换记录表';