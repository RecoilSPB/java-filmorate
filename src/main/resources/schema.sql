CREATE TABLE IF NOT EXISTS film (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255),
    description varchar(255),
    release_date date,
    duration int,
    mpa_rating_id BIGINT
);

CREATE TABLE IF NOT EXISTS genre (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS usr (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email varchar(255),
    login varchar(255),
    name varchar(255),
    birth_day date
);

CREATE TABLE IF NOT EXISTS film_like (
    film_id BIGINT,
    user_id BIGINT
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id BIGINT,
    genre_id BIGINT
);

CREATE TABLE IF NOT EXISTS user_friend (
    user_id BIGINT,
    friend_id BIGINT,
    status varchar(255) CHECK (status IN ('UNCONFIRMED', 'CONFIRMED'))
);

CREATE TABLE IF NOT EXISTS mpa_rating (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(255) NOT NULL,
	CONSTRAINT RATING_PK PRIMARY KEY (ID)
);

COMMENT ON COLUMN user_friend.user_id IS 'Ссылка на User';
COMMENT ON COLUMN user_friend.friend_id IS 'Ссылка на User';

ALTER TABLE film_like ADD FOREIGN KEY (film_id) REFERENCES film (id);
ALTER TABLE film_like ADD FOREIGN KEY (user_id) REFERENCES usr (id);

ALTER TABLE film_genre ADD FOREIGN KEY (genre_id) REFERENCES genre (id);
ALTER TABLE film_genre ADD FOREIGN KEY (film_id) REFERENCES film (id);

ALTER TABLE user_friend ADD FOREIGN KEY (user_id) REFERENCES usr (id);
ALTER TABLE user_friend ADD FOREIGN KEY (friend_id) REFERENCES usr (id);

ALTER TABLE film ADD FOREIGN KEY (mpa_rating_id) REFERENCES mpa_rating (id);
