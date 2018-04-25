USE mock_server;
CREATE TABLE `mock_info` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) DEFAULT NULL COMMENT '接口名称',
  `proto` varchar(10) DEFAULT NULL COMMENT '协议',
  `domain` varchar(50) DEFAULT NULL COMMENT '域名',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `json` longtext COMMENT '期望值',
  `iden` varchar(50) DEFAULT NULL COMMENT '标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;