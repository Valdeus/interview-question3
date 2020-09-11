DROP TABLE IF EXISTS TBL_MESSAGES;

CREATE TABLE TBL_MESSAGES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  question_id INT DEFAULT NULL,
  author VARCHAR(250) NOT NULL,
  message VARCHAR(250) NOT NULL
);

INSERT INTO TBL_MESSAGES (id, question_id, author, message)
    VALUES(1, NULL, 'John Rambo', 'Question 1?');
INSERT INTO TBL_MESSAGES (id, question_id, author, message)
    VALUES(2, 1, 'John Snow', 'Answer to Question 1');
INSERT INTO TBL_MESSAGES (id, question_id, author, message)
    VALUES(3, 1, 'John Travolta', 'Answer to Question 1');
INSERT INTO TBL_MESSAGES (id, question_id, author, message)
    VALUES(4, NULL, 'John Snow', 'Question 2?');