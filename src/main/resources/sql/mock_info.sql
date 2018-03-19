USE mock_server;
CREATE TABLE `mock_info` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '接口名称',
  `proto` varchar(10) DEFAULT NULL COMMENT '协议',
  `domain` varchar(50) DEFAULT NULL COMMENT '域名',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `expectation` longtext COMMENT '期望值',
  `complete_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;