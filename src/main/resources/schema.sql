DROP TABLE IF EXISTS Teacher;
CREATE TABLE Teacher
(
    id      VARCHAR(50) PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,

    address VARCHAR(500) NOT NULL

);
INSERT INTO  Teacher VALUES
                            ('T001','Sampath','GALLE'),
                            ('T002','Kasun','GALLE');
