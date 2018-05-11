USE mock_server;

CREATE TABLE `mock_info` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) DEFAULT NULL COMMENT '接口名称',
  `proto` varchar(10) DEFAULT NULL COMMENT '协议',
  `domain` varchar(50) DEFAULT NULL COMMENT '域名',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `json` longtext COMMENT '期望值',
  `idenkey` varchar(20) DEFAULT NULL COMMENT '标识名',
  `idenval` varchar(20) DEFAULT NULL COMMENT '标识值',
  `method` varchar(20) DEFAULT NULL COMMENT 'dubbo接口方法',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8