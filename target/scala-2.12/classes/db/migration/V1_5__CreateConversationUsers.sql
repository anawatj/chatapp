CREATE TABLE conversation_users(
    conversation_id varchar(255) not null ,
    user_id varchar(255) not null ,
    PRIMARY KEY(conversation_id,user_id)
)