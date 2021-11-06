DROP TABLE profile;
DROP TABLE conditions;
DROP TABLE device;
DROP TABLE room;
DROP TABLE scene;

CREATE TABLE profile
(
    id       VARCHAR(50)        NOT NULL,
    name     VARCHAR(20)        not NULL,
    surname  VARCHAR(20)        not NULL,
    email    VARCHAR(20)        not NULL,
    password VARCHAR(20)        not NULL,
    CONSTRAINT pk_profile PRIMARY KEY (id)
);

CREATE TABLE room
(
    id   VARCHAR(50) PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE device
(
    id      VARCHAR(50)        NOT NULL,
    name    VARCHAR(20) UNIQUE NOT NULL,
    status  BOOLEAN DEFAULT FALSE,
    room_id VARCHAR(50),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id)
);

CREATE TABLE scene
(
    id     VARCHAR(50),
    name   VARCHAR(20) UNIQUE NOT NULL,
    status BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_scene PRIMARY KEY (id)
);

CREATE TABLE conditions
(
    device_id       VARCHAR(50) NOT NULL,
    scene_id        VARCHAR(50) NOT NULL,
    activation_date DATE,
    period          VARCHAR(20),
    threshold       FLOAT,
    CONSTRAINT pk_condition PRIMARY KEY (device_id, scene_id),
    FOREIGN KEY (device_id) REFERENCES device (id),
    FOREIGN KEY (scene_id) REFERENCES scene (id)
);