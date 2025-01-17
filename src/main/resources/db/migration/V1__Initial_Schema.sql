CREATE TABLE video
(
    id              SERIAL PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    director        VARCHAR(255),
    genre           VARCHAR(100),
    main_actor      VARCHAR(255),
    cast_member     VARCHAR(255),
    synopsis        VARCHAR(1024),
    year_of_release INT,
    running_time    INT,
    soft_deleted    BOOLEAN DEFAULT FALSE
);

CREATE TABLE engagement
(
    id          SERIAL PRIMARY KEY,
    video_id    INT NOT NULL,
    impressions INT DEFAULT 0,
    views       INT DEFAULT 0,
    FOREIGN KEY (video_id) REFERENCES video (id)
);
