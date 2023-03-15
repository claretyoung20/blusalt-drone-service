CREATE
SEQUENCE IF NOT EXISTS sequence_generator START
WITH 1 INCREMENT BY 50;

CREATE TABLE address
(
    id           BIGINT NOT NULL,
    house_number VARCHAR(255),
    street       VARCHAR(255),
    zip_code     VARCHAR(255),
    city         VARCHAR(255),
    country      VARCHAR(255),
    longitude    DOUBLE PRECISION,
    latitude     VARCHAR(255),
    date_created TIMESTAMP DEFAULT now() NOT NULL ,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE delivery
(
    id              BIGINT       NOT NULL,
    delivery_status VARCHAR(255) NOT NULL,
    start_time      TIMESTAMP WITHOUT TIME ZONE,
    end_time        TIMESTAMP WITHOUT TIME ZONE,
    date_created    TIMESTAMP DEFAULT now() NOT NULL ,
    date_updated    TIMESTAMP,
    duration        BIGINT,
    drone_id        BIGINT       NOT NULL,
    CONSTRAINT pk_delivery PRIMARY KEY (id)
);

CREATE TABLE drone
(
    id             BIGINT           NOT NULL,
    serial_number  VARCHAR(100)     NOT NULL,
    weight_limit   DOUBLE PRECISION NOT NULL,
    battery_level  INTEGER          NOT NULL,
    drone_status   VARCHAR(255)     NOT NULL,
    date_created   TIMESTAMP DEFAULT now() NOT NULL,
    date_updated   TIMESTAMP,
    drone_state_id BIGINT           NOT NULL,
    drone_model_id BIGINT           NOT NULL,
    CONSTRAINT pk_drone PRIMARY KEY (id)
);

CREATE TABLE drone_audit
(
    id             BIGINT  NOT NULL,
    battery_level  INTEGER NOT NULL,
    drone_activity VARCHAR(255),
    date_created   TIMESTAMP DEFAULT now() NOT NULL,
    date_updated   TIMESTAMP,
    drone_id       BIGINT  NOT NULL,
    drone_state_id BIGINT  NOT NULL,
    CONSTRAINT pk_drone_audit PRIMARY KEY (id)
);

CREATE TABLE drone_model
(
    id                 BIGINT       NOT NULL,
    model_name         VARCHAR(255) NOT NULL,
    drone_model_status VARCHAR(255) NOT NULL,
    date_created       TIMESTAMP DEFAULT now() NOT NULL,
    date_updated       TIMESTAMP,
    CONSTRAINT pk_drone_model PRIMARY KEY (id)
);

CREATE TABLE drone_state
(
    id                 BIGINT       NOT NULL,
    state              VARCHAR(255) NOT NULL,
    drone_state_status VARCHAR(255) NOT NULL,
    date_created       TIMESTAMP DEFAULT now() NOT NULL,
    date_updated       TIMESTAMP,
    CONSTRAINT pk_drone_state PRIMARY KEY (id)
);

CREATE TABLE package_info
(
    id             BIGINT           NOT NULL,
    name           VARCHAR(255)     NOT NULL,
    weight         DOUBLE PRECISION NOT NULL,
    code           VARCHAR(255)     NOT NULL,
    image_url      VARCHAR(255)     NOT NULL,
    package_type   VARCHAR(255)     NOT NULL,
    package_status VARCHAR(255)     NOT NULL,
    time_delivered TIMESTAMP WITHOUT TIME ZONE,
    date_created   TIMESTAMP DEFAULT now() NOT NULL,
    date_updated   TIMESTAMP,
    address_id     BIGINT,
    delivery_id    BIGINT           NOT NULL,
    CONSTRAINT pk_package_info PRIMARY KEY (id)
);

ALTER TABLE drone_model
    ADD CONSTRAINT uc_drone_model_model_name UNIQUE (model_name);

ALTER TABLE drone
    ADD CONSTRAINT uc_drone_serial_number UNIQUE (serial_number);

ALTER TABLE drone_state
    ADD CONSTRAINT uc_drone_state_state UNIQUE (state);

ALTER TABLE package_info
    ADD CONSTRAINT uc_package_info_code UNIQUE (code);

ALTER TABLE delivery
    ADD CONSTRAINT FK_DELIVERY_ON_DRONE FOREIGN KEY (drone_id) REFERENCES drone (id);

ALTER TABLE drone_audit
    ADD CONSTRAINT FK_DRONE_AUDIT_ON_DRONE FOREIGN KEY (drone_id) REFERENCES drone (id);

ALTER TABLE drone_audit
    ADD CONSTRAINT FK_DRONE_AUDIT_ON_DRONESTATE FOREIGN KEY (drone_state_id) REFERENCES drone_state (id);

ALTER TABLE drone
    ADD CONSTRAINT FK_DRONE_ON_DRONEMODEL FOREIGN KEY (drone_model_id) REFERENCES drone_model (id);

ALTER TABLE drone
    ADD CONSTRAINT FK_DRONE_ON_DRONESTATE FOREIGN KEY (drone_state_id) REFERENCES drone_state (id);

ALTER TABLE package_info
    ADD CONSTRAINT FK_PACKAGE_INFO_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE package_info
    ADD CONSTRAINT FK_PACKAGE_INFO_ON_DELIVERY FOREIGN KEY (delivery_id) REFERENCES delivery (id);


INSERT INTO drone_model (id, model_name, drone_model_status)
VALUES (1, 'Lightweight', 'ACTIVE');

INSERT INTO drone_model (id, model_name, drone_model_status)
VALUES (2, 'Middleweight', 'ACTIVE');

INSERT INTO drone_model ( id, model_name, drone_model_status)
VALUES (3, 'Cruiserweight', 'ACTIVE');

INSERT INTO drone_model ( id, model_name, drone_model_status)
VALUES (4, 'Heavyweight', 'ACTIVE');


INSERT INTO drone_state ( id, state, drone_state_status)
VALUES (1, 'IDLE', 'ACTIVE');

INSERT INTO drone_state ( id, state, drone_state_status)
VALUES (2, 'LOADING', 'ACTIVE');

INSERT INTO drone_state ( id, state, drone_state_status)
VALUES (3, 'LOADED', 'ACTIVE');

INSERT INTO drone_state ( id, state, drone_state_status)
VALUES (4, 'DELIVERING', 'ACTIVE');

INSERT INTO drone_state ( id, state, drone_state_status)
VALUES (5, 'DELIVERED', 'ACTIVE');

INSERT INTO drone_state ( id, state, drone_state_status)
VALUES (6, 'RETURNING', 'ACTIVE');
