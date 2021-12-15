DROP TABLE profile;
DROP TABLE alarm_clock;
DROP TABLE conditioner;
DROP TABLE light_bulb;
DROP TABLE speaker;
DROP TABLE television;
DROP TABLE condition;
DROP TABLE scene;
DROP TABLE device;
DROP TABLE room;

CREATE TABLE profile
(
    id       VARCHAR(255) NOT NULL,
    name     VARCHAR(255) not NULL,
    surname  VARCHAR(255) not NULL,
    email    VARCHAR(255) not NULL,
    password VARCHAR(255) not NULL,
    CONSTRAINT pk_profile PRIMARY KEY (id)
);

CREATE TABLE room
(
    id   VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE device
(
    id      VARCHAR(255)        NOT NULL,
    name    VARCHAR(255) UNIQUE NOT NULL,
    status  BOOLEAN DEFAULT FALSE,
    type    VARCHAR(255)        NOT NULL,
    room_id VARCHAR(255),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id)
);

CREATE TABLE scene
(
    id     VARCHAR(255),
    name   VARCHAR(255) UNIQUE NOT NULL,
    status BOOLEAN DEFAULT FALSE,
    period          VARCHAR(255),
    CONSTRAINT pk_scene PRIMARY KEY (id)
);

CREATE TABLE condition
(
    id              VARCHAR(255)        NOT NULL,
    device_id       VARCHAR(255)        NOT NULL,
    scene_id        VARCHAR(255)        NOT NULL,
    activation_date TIMESTAMP(0),
    threshold       FLOAT,
    name            VARCHAR(255) UNIQUE NOT NULL,
    CONSTRAINT pk_condition PRIMARY KEY (id),
    FOREIGN KEY (device_id) REFERENCES device (id),
    FOREIGN KEY (scene_id) REFERENCES scene (id)
);

CREATE TABLE alarm_clock
(
    id      VARCHAR(255)        NOT NULL,
    time    VARCHAR(255),
    frequency    VARCHAR(255),
    song    VARCHAR(255),
    room_id VARCHAR(255),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (id) REFERENCES device(id)
);

CREATE TABLE conditioner
(
    id          VARCHAR(255)        NOT NULL,
    temperature INTEGER,
    settings    VARCHAR(255),
    room_id     VARCHAR(255),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (id) REFERENCES device(id)
);

CREATE TABLE light_bulb
(
    id         VARCHAR(255)        NOT NULL,
    brightness INTEGER,
    colortemp  INTEGER,
    room_id    VARCHAR(255),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (id) REFERENCES device(id)
);

CREATE TABLE speaker
(
    id      VARCHAR(255)        NOT NULL,
    volume  INTEGER,
    device_fk VARCHAR(255),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (device_fk) REFERENCES device(id)
);

CREATE TABLE television
(
    id      VARCHAR(255)        NOT NULL,
    volume  INTEGER,
    channel INTEGER,
    room_id VARCHAR(255),
    CONSTRAINT pk_device PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (id) REFERENCES device(id)
);

