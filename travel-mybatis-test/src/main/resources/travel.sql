CREATE DATABASE `travel` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `travel`;

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`name`,`create_time`) values (1,'小苹果','2018-10-30 20:54:49'),(2,'小番茄','2018-10-30 20:55:02'),(3,'小橘子','2018-10-30 20:55:13'),(4,'小西瓜','2018-10-30 20:55:26'),(5,'小桃子','2018-10-30 20:55:36'),(6,'小南瓜','2018-10-30 20:55:56');
