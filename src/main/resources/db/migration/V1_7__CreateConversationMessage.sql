CREATE TABLE conversation_messages(
    conversation_id varchar(255) not null ,
    message_id varchar(255) not null ,
    PRIMARY KEY(conversation_id,message_id)
)