CREATE TABLE address
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    full_address VARCHAR(255),
    longitude    DOUBLE PRECISION,
    latitude     DOUBLE PRECISION,
    date_created TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE delivery
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    delivery_status VARCHAR(255) NOT NULL,
    start_time      TIMESTAMP WITHOUT TIME ZONE,
    end_time        TIMESTAMP WITHOUT TIME ZONE,
    date_created    TIMESTAMP DEFAULT now() NOT NULL ,
    date_updated    TIMESTAMP,
    drone_id        BIGINT       NOT NULL,
    CONSTRAINT pk_delivery PRIMARY KEY (id)
);

CREATE TABLE drone
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    serial_number  VARCHAR(100)     NOT NULL,
    weight_limit   DOUBLE PRECISION NOT NULL,
    battery_level  INTEGER          NOT NULL,
    status          VARCHAR(255)     NOT NULL,
    date_created   TIMESTAMP DEFAULT now() NOT NULL,
    date_updated   TIMESTAMP,
    drone_state    VARCHAR(255),
    drone_model_id BIGINT           NOT NULL,
    CONSTRAINT pk_drone PRIMARY KEY (id)
);

CREATE TABLE drone_audit
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
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
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    model_name         VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    date_created       TIMESTAMP DEFAULT now() NOT NULL,
    date_updated       TIMESTAMP,
    CONSTRAINT pk_drone_model PRIMARY KEY (id)
);

CREATE TABLE drone_state
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    state              VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    date_created       TIMESTAMP DEFAULT now() NOT NULL,
    date_updated       TIMESTAMP,
    CONSTRAINT pk_drone_state PRIMARY KEY (id)
);

CREATE TABLE item
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name           VARCHAR(255)     NOT NULL,
    weight         DOUBLE PRECISION NOT NULL,
    code           VARCHAR(255)     NOT NULL,
    image_url      VARCHAR(255)     NOT NULL,
    item_type   VARCHAR(255)     NOT NULL,
    item_status VARCHAR(255)     NOT NULL,
    time_delivered TIMESTAMP WITHOUT TIME ZONE,
    date_created   TIMESTAMP DEFAULT now() NOT NULL,
    date_updated   TIMESTAMP,
    address_id     BIGINT,
    delivery_id    BIGINT           NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id)
);

ALTER TABLE drone_model
    ADD CONSTRAINT uc_drone_model_model_name UNIQUE (model_name);

ALTER TABLE drone
    ADD CONSTRAINT uc_drone_serial_number UNIQUE (serial_number);

ALTER TABLE drone_state
    ADD CONSTRAINT uc_drone_state_state UNIQUE (state);

ALTER TABLE item
    ADD CONSTRAINT uc_item_code UNIQUE (code);

ALTER TABLE delivery
    ADD CONSTRAINT FK_DELIVERY_ON_DRONE FOREIGN KEY (drone_id) REFERENCES drone (id);

ALTER TABLE drone_audit
    ADD CONSTRAINT FK_DRONE_AUDIT_ON_DRONE FOREIGN KEY (drone_id) REFERENCES drone (id);

ALTER TABLE drone_audit
    ADD CONSTRAINT FK_DRONE_AUDIT_ON_DRONESTATE FOREIGN KEY (drone_state_id) REFERENCES drone_state (id);

ALTER TABLE drone
    ADD CONSTRAINT FK_DRONE_ON_DRONEMODEL FOREIGN KEY (drone_model_id) REFERENCES drone_model (id);

ALTER TABLE item
    ADD CONSTRAINT FK_item_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE item
    ADD CONSTRAINT FK_item_ON_DELIVERY FOREIGN KEY (delivery_id) REFERENCES delivery (id);


INSERT INTO drone_model (model_name, status)
VALUES ('Lightweight', 'ACTIVE');

INSERT INTO drone_model (model_name, status)
VALUES ('Middleweight', 'ACTIVE');

INSERT INTO drone_model ( model_name, status)
VALUES ('Cruiserweight', 'ACTIVE');

INSERT INTO drone_model (  model_name, status)
VALUES ('Heavyweight', 'ACTIVE');


INSERT INTO drone_state ( state, status)
VALUES ('IDLE', 'ACTIVE');

INSERT INTO drone_state (state, status)
VALUES ('LOADING', 'ACTIVE');

INSERT INTO drone_state ( state, status)
VALUES ('LOADED', 'ACTIVE');

INSERT INTO drone_state (state, status)
VALUES ('DELIVERING', 'ACTIVE');

INSERT INTO drone_state (  state, status)
VALUES ('DELIVERED', 'ACTIVE');

INSERT INTO drone_state (state, status)
VALUES ('RETURNING', 'ACTIVE');
