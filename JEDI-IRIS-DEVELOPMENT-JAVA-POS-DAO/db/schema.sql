CREATE DATABASE IF NOT EXISTS flipfit_schema;
USE flipfit_schema;

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(64) PRIMARY KEY,
    user_name VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    role_name VARCHAR(32) NOT NULL,
    pan_number VARCHAR(32),
    card_details VARCHAR(64
    is_approved TINYINT(1) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS gym_centres (
    centre_id VARCHAR(64) PRIMARY KEY,
    owner_id VARCHAR(64) NOT NULL,
    centre_name VARCHAR(255) NOT NULL,
    city VARCHAR(128) NOT NULL,
    pincode VARCHAR(16) NOT NULL,
    is_approved TINYINT(1) NOT NULL DEFAULT 0,
    CONSTRAINT fk_centre_owner FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS slots (
    slot_id VARCHAR(64) PRIMARY KEY,
    centre_id VARCHAR(64) NOT NULL,
    start_time VARCHAR(64) NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    CONSTRAINT fk_slot_centre FOREIGN KEY (centre_id) REFERENCES gym_centres(centre_id)
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id VARCHAR(64) PRIMARY KEY,
    user_id VARCHAR(64) NOT NULL,
    slot_id VARCHAR(64) NOT NULL,
    status VARCHAR(32) NOT NULL,
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_booking_slot FOREIGN KEY (slot_id) REFERENCES slots(slot_id)
);
