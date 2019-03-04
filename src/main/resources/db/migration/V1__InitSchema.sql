----ENTITIES

-- CREATE SCHEMA IF NOT EXISTS PUBLIC;

create table PUBLIC.account
(
  account_id int primary key,
  email varchar(255),
  password varchar(255)
);
--
create table PUBLIC.chat
(
  chat_id        int primary key,
  chat_name varchar(255)
);
--
create table PUBLIC.message
(
  message_id   int primary key,
  text varchar(255)
);

create table PUBLIC.attribute
(
  attribute_id   int primary key,
  attribute_name varchar(255)
);

----RELATIONS
create table PUBLIC.account_chat
(
  account_id int NOT NULL,
  chat_id    int NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account (account_id),
  FOREIGN KEY (chat_id) REFERENCES chat (chat_id)

);

create table PUBLIC.chat_message
(
  message_id int NOT NULL,
  chat_id    int NOT NULL,
  FOREIGN KEY (message_id) REFERENCES message (message_id),
  FOREIGN KEY (chat_id) REFERENCES chat (chat_id)

);

create table PUBLIC.account_message
(
  message_id int NOT NULL,
  account_id    int NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account (account_id),
  FOREIGN KEY (message_id) REFERENCES message (message_id)

);

create table PUBLIC.chat_attribute
(
  attribute_id int NOT NULL,
  chat_id    int NOT NULL,
  FOREIGN KEY (attribute_id) REFERENCES attribute (attribute_id),
  FOREIGN KEY (chat_id) REFERENCES chat (chat_id)

);


