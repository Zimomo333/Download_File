-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `CDK`;
CREATE TABLE `CDK`  (
  `Number` varchar (70) NOT NULL ,
  `Times` int (7) NOT NULL,
  `Used` int (3) NOT NULL,
  PRIMARY KEY (`Number`)
) ;

INSERT INTO `CDK` VALUES ('ABC123', 5, 0);
INSERT INTO `CDK` VALUES ('DEF456', 0, 1);