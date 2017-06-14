DROP TABLE user if exist;
create table user (
  id INT(11) AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR(255),
  password VARCHAR(255),
  age INT(3),
  nickname VARCHAR(255),
  mail VARCHAR(255),
  memo VARCHAR(255),
  );