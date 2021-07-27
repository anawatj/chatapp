CREATE TABLE users
(
    user_id varchar(255) not null ,
    username varchar(255) not null ,
    password varchar(8000) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    PRIMARY KEY(user_id)

)