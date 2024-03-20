CREATE TABLE roles
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE tokens
(
    id        BIGINT       NOT NULL,
    value     VARCHAR(255) NULL,
    user_id   BIGINT       NULL,
    expiry_at datetime     NULL,
    deleted   BIT(1)       NOT NULL,
    CONSTRAINT pk_tokens PRIMARY KEY (id)
);

CREATE TABLE users
(
    id             BIGINT       NOT NULL,
    name           VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    hash_password  VARCHAR(255) NULL,
    email_verified BIT(1)       NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    roles_id BIGINT NOT NULL,
    users_id BIGINT NOT NULL
);

ALTER TABLE tokens
    ADD CONSTRAINT FK_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (users_id) REFERENCES users (id);