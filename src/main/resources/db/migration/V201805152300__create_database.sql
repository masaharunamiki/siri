
CREATE TABLE `word` (
   `word_id` int(11) NOT NULL AUTO_INCREMENT,
   `prefix` char(2) NOT NULL,
   `word` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`word_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 CREATE TABLE `session` (
   `session_id` varchar(255) NOT NULL,
   `insert_time` datetime DEFAULT NULL,
   `update_time` datetime DEFAULT NULL,
   PRIMARY KEY (`session_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 CREATE TABLE `session_and_word` (
   `session_id` varchar(255) NOT NULL,
   `own_word_json` mediumtext,
   `opponent_word_json` mediumtext,
   PRIMARY KEY (`session_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 