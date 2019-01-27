/*
Navicat MySQL Data Transfer

Source Server         : DBconn
Source Server Version : 50537
Source Host           : localhost:3306
Source Database       : mdhotel

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2017-06-06 12:33:31
*/
use mdhotel;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accom
-- ----------------------------
DROP TABLE IF EXISTS `accom`;
CREATE TABLE `accom` (
  `accom_code` varchar(255) NOT NULL,
  `accom_room` varchar(255) NOT NULL,
  `accom_pass` varchar(255) NOT NULL,
  `accom_intime` varchar(255) NOT NULL,
  `accom_outime` varchar(255) NOT NULL,
  `accom_order` varchar(255) DEFAULT NULL,
  `accom_price` int(5) NOT NULL,
  `accom_staff` varchar(255) NOT NULL,
  `accom_remark` varchar(255) DEFAULT NULL,
  `accom_state` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`accom_code`),
  KEY `accom_room` (`accom_room`),
  KEY `accom_order` (`accom_order`),
  KEY `accom_ibfk_4` (`accom_staff`),
  KEY `accom_ibfk_2` (`accom_pass`),
  CONSTRAINT `accom_ibfk_1` FOREIGN KEY (`accom_room`) REFERENCES `room` (`room_code`),
  CONSTRAINT `accom_ibfk_2` FOREIGN KEY (`accom_pass`) REFERENCES `passenger` (`pass_code`),
  CONSTRAINT `accom_ibfk_4` FOREIGN KEY (`accom_staff`) REFERENCES `staff` (`staff_login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accom
-- ----------------------------
INSERT INTO `accom` VALUES ('A20170528143308', 'r009', 'p20170504160516', '2017-05-28', '2017-05-29', null, '200', 's003', '', '1');
INSERT INTO `accom` VALUES ('A20170528143354', 'r006', 'p20170513153949', '2017-05-30', '2017-05-31', null, '300', 's003', '', '-1');
INSERT INTO `accom` VALUES ('A20170528143536', 'r011', 'p20170504160615', '2017-05-28', '2017-05-31', null, '400', 's001', '', '1');
INSERT INTO `accom` VALUES ('A20170528143551', 'r003', 'p20170504160006', '2017-05-28', '2017-05-30', null, '500', 's001', '', '0');
INSERT INTO `accom` VALUES ('A20170528143603', 'r002', 'p20170504155807', '2017-05-30', '2017-06-02', null, '1100', 's001', '', '0');
INSERT INTO `accom` VALUES ('A20170528143632', 'r004', 'p20170504160303', '2017-05-28', '2017-05-29', null, '200', 's001', '', '0');
INSERT INTO `accom` VALUES ('A20170528143858', 'r001', 'p20170504160303', '2017-05-28', '2017-05-29', 'O20170528143911', '216', 's001', '', '1');
INSERT INTO `accom` VALUES ('A20170528143919', 'r008', 'p20170504163947', '2017-05-28', '2017-05-31', 'O20170531092419,O20170602084128', '1808', 's001', '', '0');
INSERT INTO `accom` VALUES ('A20170528144908', 'r005', 'p20170504160411', '2017-05-28', '2017-05-30', null, '800', 'zhangyuan', '', '0');
INSERT INTO `accom` VALUES ('A20170528145057', 'r007', 'p20170504160303', '2017-05-28', '2017-05-30', 'O20170528145108,O20170528145111', '1320', 's003', '', '1');
INSERT INTO `accom` VALUES ('A20170528145159', 'r010', 'p20170504155807', '2017-05-28', '2017-05-30', 'O20170528145211', '305', 's003', '', '0');
INSERT INTO `accom` VALUES ('A20170528145918', 'r0012', 'p20170504163947', '2017-05-28', '2017-05-30', 'O20170528145928', '512', 's003', '', '0');
INSERT INTO `accom` VALUES ('A2017053192629', 'r001', 'p20170504160303', '2017-05-31', '2017-06-01', null, '200', 's001', '', '1');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_code` varchar(255) NOT NULL,
  `goods_name` varchar(255) NOT NULL,
  `goods_count` int(5) NOT NULL,
  `goods_price` int(5) NOT NULL,
  PRIMARY KEY (`goods_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('g001', '牙刷', '16', '8');
INSERT INTO `goods` VALUES ('g002', '纸巾', '9', '3');
INSERT INTO `goods` VALUES ('g003', '牙膏', '5', '10');
INSERT INTO `goods` VALUES ('g004', '拖鞋', '0', '19');
INSERT INTO `goods` VALUES ('g005', '梳子', '6', '8');
INSERT INTO `goods` VALUES ('g006', '毛巾', '0', '13');
INSERT INTO `goods` VALUES ('g007', '指甲刀套装', '4', '12');
INSERT INTO `goods` VALUES ('g008', '杯子', '8', '5');

-- ----------------------------
-- Table structure for gorder
-- ----------------------------
DROP TABLE IF EXISTS `gorder`;
CREATE TABLE `gorder` (
  `order_code` varchar(255) NOT NULL,
  `order_goods` varchar(255) NOT NULL,
  `order_count` int(5) NOT NULL,
  `order_price` int(5) NOT NULL,
  `order_time` varchar(255) NOT NULL,
  `order_type` int(2) NOT NULL,
  `order_shop` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_code`),
  KEY `order_goods` (`order_goods`) USING BTREE,
  KEY `gorder_shop` (`order_shop`),
  CONSTRAINT `gorder_ibfk_1` FOREIGN KEY (`order_goods`) REFERENCES `goods` (`goods_code`),
  CONSTRAINT `gorder_shop` FOREIGN KEY (`order_shop`) REFERENCES `shop` (`shop_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gorder
-- ----------------------------
INSERT INTO `gorder` VALUES ('O20170528143732', 'g002', '10', '30', '2017-05-28 14:37:32', '1', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528143737', 'g008', '5', '25', '2017-05-28 14:37:37', '1', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528143743', 'g001', '12', '96', '2017-05-28 14:37:43', '1', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528143750', 'g003', '5', '50', '2017-05-28 14:37:50', '1', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528143757', 'g007', '3', '36', '2017-05-28 14:37:57', '1', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528143801', 'g005', '1', '8', '2017-05-28 14:38:01', '1', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528143911', 'g001', '2', '16', '2017-05-28 14:39:11', '0', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170528144226', 'g008', '4', '20', '2017-05-28 14:42:26', '1', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145005', 'g007', '3', '36', '2017-05-28 14:50:05', '1', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145009', 'g005', '5', '40', '2017-05-28 14:50:09', '1', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145014', 'g001', '7', '56', '2017-05-28 14:50:14', '1', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145108', 'g001', '1', '8', '2017-05-28 14:51:08', '0', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145111', 'g007', '1', '12', '2017-05-28 14:51:11', '0', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145211', 'g008', '1', '5', '2017-05-28 14:52:11', '0', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170528145928', 'g007', '1', '12', '2017-05-28 14:59:28', '0', 'S20170523132717');
INSERT INTO `gorder` VALUES ('O20170531092419', 'g008', '1', '5', '2017-05-31 09:24:19', '0', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170602084128', 'g002', '1', '3', '2017-06-02 08:41:28', '0', 'S20170523125608');
INSERT INTO `gorder` VALUES ('O20170602084258', 'g008', '1', '5', '2017-06-02 08:42:58', '1', 'S20170523125608');

-- ----------------------------
-- Table structure for passenger
-- ----------------------------
DROP TABLE IF EXISTS `passenger`;
CREATE TABLE `passenger` (
  `pass_code` varchar(255) NOT NULL,
  `pass_name` varchar(255) NOT NULL,
  `pass_age` int(255) DEFAULT NULL,
  `pass_sex` varchar(2) DEFAULT NULL,
  `pass_idcard` varchar(255) NOT NULL,
  `pass_tel` varchar(255) NOT NULL,
  `pass_state` int(2) NOT NULL DEFAULT '0',
  `pass_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pass_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of passenger
-- ----------------------------
INSERT INTO `passenger` VALUES ('p20170504155807', '晴明', '21', '男', '141101199706181234', '13305056709', '1', '');
INSERT INTO `passenger` VALUES ('p20170504160006', '茨木童子', '27', '男', '210101199111137101', '18245048947', '1', '');
INSERT INTO `passenger` VALUES ('p20170504160303', '刘凡', '30', '男', '41101198809192345', '15700302340', '1', '');
INSERT INTO `passenger` VALUES ('p20170504160411', '张文', '29', '女', '140101198904174789', '18840506792', '1', '');
INSERT INTO `passenger` VALUES ('p20170504160516', '左右', '25', '女', '13111199307102391', '13068949673', '1', '');
INSERT INTO `passenger` VALUES ('p20170504160615', '沈玉', '26', '男', '21201199210102111', '15634890153', '1', '111');
INSERT INTO `passenger` VALUES ('p20170504163947', '张嘉', '31', '男', '121101198709103795', '18834803863', '1', '');
INSERT INTO `passenger` VALUES ('p20170513153949', '虾条', '27', '男', '122101199112278951', '13087493759', '1', '');
INSERT INTO `passenger` VALUES ('p20170520123025', '李二', '26', '男', '500101199203017689', '18834568768', '0', '');
INSERT INTO `passenger` VALUES ('p20170520123117', '贾斯', '23', '女', '220101199509123456', '17628657321', '0', '');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `room_code` varchar(255) NOT NULL,
  `room_name` varchar(255) NOT NULL,
  `room_cash` int(3) NOT NULL,
  `room_price` int(5) NOT NULL,
  `room_state` int(2) NOT NULL DEFAULT '0',
  `room_remark` varchar(255) DEFAULT NULL,
  `room_shop` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`room_code`),
  KEY `rshop` (`room_shop`),
  CONSTRAINT `rshop` FOREIGN KEY (`room_shop`) REFERENCES `shop` (`shop_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('r001', '普通房', '100', '100', '0', null, 'S20170523125608');
INSERT INTO `room` VALUES ('r0012', '标准房', '100', '200', '1', null, 'S20170523132717');
INSERT INTO `room` VALUES ('r002', '高级房', '200', '300', '1', null, 'S20170523125608');
INSERT INTO `room` VALUES ('r003', '标准房', '100', '200', '1', null, 'S20170523125608');
INSERT INTO `room` VALUES ('r004', '普通房', '100', '100', '1', null, 'S20170523125608');
INSERT INTO `room` VALUES ('r005', '高级房', '200', '300', '1', null, 'S20170523132717');
INSERT INTO `room` VALUES ('r006', '标准房', '100', '200', '1', null, 'S20170523132717');
INSERT INTO `room` VALUES ('r007', '总统房', '300', '500', '0', null, 'S20170523132717');
INSERT INTO `room` VALUES ('r008', '总统房', '300', '500', '1', null, 'S20170523125608');
INSERT INTO `room` VALUES ('r009', '普通房', '100', '100', '0', null, 'S20170523132717');
INSERT INTO `room` VALUES ('r010', '普通房', '100', '100', '1', null, 'S20170523132717');
INSERT INTO `room` VALUES ('r011', '普通房', '100', '100', '0', null, 'S20170523125608');

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shop_code` varchar(255) NOT NULL,
  `shop_name` varchar(255) NOT NULL,
  `shop_address` varchar(255) NOT NULL,
  `shop_time` varchar(255) NOT NULL,
  `shop_state` int(2) NOT NULL DEFAULT '0',
  `shop_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('S20170523125608', '牡丹之星连锁旅馆-洛阳分店', '洛阳市洛龙区', '2017/05/23 12:56:08', '1', '');
INSERT INTO `shop` VALUES ('S20170523132717', '牡丹之星连锁旅馆-郑州分店', '郑州市金水区', '2017/05/23 13:27:17', '1', '');
INSERT INTO `shop` VALUES ('S20170523135059', '1', '1', '2017/05/23 13:50:59', '0', '');
INSERT INTO `shop` VALUES ('S20170523135104', '2', '2', '2017/05/23 13:51:04', '0', '');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staff_login` varchar(255) NOT NULL,
  `staff_name` varchar(255) NOT NULL,
  `staff_pwd` varchar(255) NOT NULL,
  `staff_type` int(255) NOT NULL,
  `staff_age` int(255) DEFAULT NULL,
  `staff_sex` char(2) DEFAULT NULL,
  `staff_time` varchar(255) DEFAULT NULL,
  `staff_tel` varchar(255) DEFAULT NULL,
  `staff_address` varchar(255) DEFAULT NULL,
  `staff_state` int(2) NOT NULL DEFAULT '0',
  `staff_remark` varchar(255) DEFAULT NULL,
  `staff_shop` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`staff_login`),
  KEY `shop` (`staff_shop`),
  CONSTRAINT `shop` FOREIGN KEY (`staff_shop`) REFERENCES `shop` (`shop_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('hhm', '张飞飞', '000', '0', '23', '女', '2017-05-23', '15677331199', '', '0', '', null);
INSERT INTO `staff` VALUES ('hxm', '刘玉', '000', '0', '22', '女', '2017-05-23', '134', '', '0', '', null);
INSERT INTO `staff` VALUES ('s001', '张三三', '111', '0', '25', '男', '2017-04-12', '13011117777', '河南省洛阳市', '1', '111', 'S20170523125608');
INSERT INTO `staff` VALUES ('s003', '王五', '111', '0', '4', '男', '2017-04-15', '18278786655', '北京市海淀区', '1', '员工', 'S20170523132717');
INSERT INTO `staff` VALUES ('s004', '赵六六', '111', '1', '32', '男', '2017-03-25', '', '', '0', '', 'S20170523125608');
INSERT INTO `staff` VALUES ('s005', '宋风', '555', '1', '20', '男', '2017-04-07', '133', '', '0', '', 'S20170523132717');
INSERT INTO `staff` VALUES ('s007', '李四', '111', '2', '28', '男', '2017-04-24', '13345066603', '', '0', '', null);
INSERT INTO `staff` VALUES ('zhangyuan', '张元', '000', '0', '27', '男', '2017-05-28', '13398096754', '', '1', '', 'S20170523132717');
DROP TRIGGER IF EXISTS `roompassstaff_update`;
DELIMITER ;;
CREATE TRIGGER `roompassstaff_update` AFTER INSERT ON `accom` FOR EACH ROW begin
update room set room_state=1 where room_code=new.accom_room;
update passenger set pass_state=1 where pass_code=new.accom_pass;
update staff set staff_state=1 where staff_login=new.accom_staff;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `roomState_update`;
DELIMITER ;;
CREATE TRIGGER `roomState_update` AFTER UPDATE ON `accom` FOR EACH ROW begin
if(new.accom_state=1) then
update room set room_state=0 where room_code=new.accom_room;
end if;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `goods_order`;
DELIMITER ;;
CREATE TRIGGER `goods_order` AFTER INSERT ON `gorder` FOR EACH ROW begin
if new.order_type=1 then
update goods set goods_count=goods_count+new.order_count where goods_code=new.order_goods;
end if;
if new.order_type=0 then
update goods set goods_count=goods_count-new.order_count where goods_code=new.order_goods;
end if;
end
;;
DELIMITER ;
GRANT ALL PRIVILEGES ON mdhotel.* TO 'root'@'127.0.0.1' IDENTIFIED BY '123456';
FLUSH PRIVILEGES;