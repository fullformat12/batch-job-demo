CREATE TABLE USERS (
                       id BIGINT PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255)
);
INSERT INTO USERS (id, name, email)
SELECT * FROM CSVREAD('classpath:users.csv');
