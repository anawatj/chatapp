CREATE TABLE messages(
    id varchar(255) not null ,
    type varchar(100) not null ,
    data varchar(8000) not null ,
    user_id varchar(255) not null,
    PRIMARY KEY(id)
)