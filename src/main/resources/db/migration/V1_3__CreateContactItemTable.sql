CREATE TABLE contact_items(
    id INT not null AUTO_INCREMENT,
    contact_id varchar(255) not null ,
    user_id varchar(255) not null,
    PRIMARY KEY(id)
)