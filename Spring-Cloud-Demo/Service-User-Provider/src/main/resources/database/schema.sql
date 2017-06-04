drop table user if exists;
create table user (
  id INT(11) AUTO_INCREMENT PRIMARY KEY ,
  username VARCHAR(255),
  password VARCHAR(255)
  );