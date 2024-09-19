## [Главная](./README.md)
### Создание структуры БД
```
CREATE TABLE "film" (
    "id" integer PRIMARY KEY,
    "name" varchar,
    "description" varchar,
    "releaseDate" date,
    "duration" integer,
    "mpa_rating" varchar CHECK ("mpa_rating" IN ('G', 'PG', 'PG-13', 'R', 'NC-17'))
);

CREATE TABLE "genre" (
    "id" integer PRIMARY KEY,
    "name" varchar
);

CREATE TABLE "user" (
    "id" integer PRIMARY KEY,
    "email" varchar,
    "login" varchar,
    "name" varchar,
    "birth_day" date
);

CREATE TABLE "film_like" (
    "film_id" integer,
    "user_id" integer
);

CREATE TABLE "film_general" (
    "film_id" integer,
    "genre_id" integer
);

CREATE TABLE "user_friend" (
    "user_id" integer,
    "friend_id" integer,
    "status" varchar CHECK ("status" IN ('UNCONFIRMED', 'CONFIRMED'))
);

COMMENT ON COLUMN "user_friend"."user_id" IS 'Ссылка на User';
COMMENT ON COLUMN "user_friend"."friend_id" IS 'Ссылка на User';

ALTER TABLE "film_like" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("id");
ALTER TABLE "film_like" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "film_general" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");
ALTER TABLE "film_general" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("id");

ALTER TABLE "user_friend" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");
ALTER TABLE "user_friend" ADD FOREIGN KEY ("friend_id") REFERENCES "user" ("id");

```