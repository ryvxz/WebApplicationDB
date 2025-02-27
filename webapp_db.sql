-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS webapplicationdb;
USE webapplicationdb;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS account;

-- Create account table
CREATE TABLE account (
    user_name VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    user_role VARCHAR(20) NOT NULL CHECK (user_role IN ('USER', 'ADMIN', 'SUPER_ADMIN'))
);

-- Create posts table
CREATE TABLE posts (
    user_name VARCHAR(50) PRIMARY KEY,
    post1 VARCHAR(200),
    post2 VARCHAR(200),
    post3 VARCHAR(200),
    post4 VARCHAR(200),
    post5 VARCHAR(200),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

-- Create follows table
CREATE TABLE follows (
    user_name VARCHAR(50) PRIMARY KEY,
    follow1 VARCHAR(50),
    follow2 VARCHAR(50),
    follow3 VARCHAR(50),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE,
    FOREIGN KEY (follow1) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow2) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow3) REFERENCES account(user_name) ON DELETE SET NULL
);

-- Create messages table for help page
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

-- Insert a super admin user (username: superadmin, password: admin123)
INSERT INTO account (user_name, password, user_role) VALUES ('superadmin', 'admin123', 'SUPER_ADMIN');-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS webapplicationdb;
USE webapplicationdb;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS account;

-- Create account table
CREATE TABLE account (
    user_name VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    user_role VARCHAR(20) NOT NULL CHECK (user_role IN ('USER', 'ADMIN', 'SUPER_ADMIN'))
);

-- Create posts table
CREATE TABLE posts (
    user_name VARCHAR(50) PRIMARY KEY,
    post1 VARCHAR(200),
    post2 VARCHAR(200),
    post3 VARCHAR(200),
    post4 VARCHAR(200),
    post5 VARCHAR(200),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

-- Create follows table
CREATE TABLE follows (
    user_name VARCHAR(50) PRIMARY KEY,
    follow1 VARCHAR(50),
    follow2 VARCHAR(50),
    follow3 VARCHAR(50),
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE,
    FOREIGN KEY (follow1) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow2) REFERENCES account(user_name) ON DELETE SET NULL,
    FOREIGN KEY (follow3) REFERENCES account(user_name) ON DELETE SET NULL
);

-- Create messages table for help page
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_name) REFERENCES account(user_name) ON DELETE CASCADE
);

-- Insert a super admin user (username: superadmin, password: admin123)
INSERT INTO account (user_name, password, user_role) VALUES ('superadmin', 'admin123', 'SUPER_ADMIN');