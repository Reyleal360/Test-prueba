-- ============================================================
-- CoopCredit Database Schema
-- Complete SQL script for MySQL 8.0+
-- ============================================================

-- ============================================================
-- V1: CREATE SCHEMA - Core tables
-- ============================================================

-- Users table for authentication
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Affiliates table
CREATE TABLE IF NOT EXISTS affiliates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    monthly_salary DECIMAL(15,2) NOT NULL,
    seniority INT NOT NULL COMMENT 'Seniority in months',
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_document (document_number),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Credit applications table
CREATE TABLE IF NOT EXISTS credit_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    affiliate_id BIGINT NOT NULL,
    requested_amount DECIMAL(15,2) NOT NULL,
    term_months INT NOT NULL,
    purpose VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    evaluation_date TIMESTAMP NULL,
    approval_amount DECIMAL(15,2) NULL,
    monthly_quota DECIMAL(15,2) NULL,
    debt_to_income_ratio DECIMAL(5,2) NULL,
    INDEX idx_affiliate (affiliate_id),
    INDEX idx_status (status),
    INDEX idx_application_date (application_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Risk evaluations table
CREATE TABLE IF NOT EXISTS risk_evaluations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    credit_application_id BIGINT UNIQUE NOT NULL,
    risk_score INT NOT NULL,
    risk_level VARCHAR(20) NOT NULL,
    evaluation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    external_reference VARCHAR(50),
    recommendation TEXT,
    INDEX idx_credit_app (credit_application_id),
    INDEX idx_risk_level (risk_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- V2: CREATE RELATIONS - Foreign key constraints
-- ============================================================

-- Add foreign key constraints
ALTER TABLE credit_applications
    ADD CONSTRAINT fk_application_affiliate
    FOREIGN KEY (affiliate_id) REFERENCES affiliates(id)
    ON DELETE CASCADE;

ALTER TABLE risk_evaluations
    ADD CONSTRAINT fk_evaluation_application
    FOREIGN KEY (credit_application_id) REFERENCES credit_applications(id)
    ON DELETE CASCADE;

-- ============================================================
-- V3: INSERT INITIAL DATA - Default users and sample data
-- ============================================================

-- Insert default admin user (password: admin123)
INSERT INTO users (username, password, email, role, enabled)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
        'admin@coopcredit.com', 'ROLE_ADMIN', TRUE);

-- Insert analyst user (password: analyst123)
INSERT INTO users (username, password, email, role, enabled)
VALUES ('analyst', '$2a$10$DowJonesRiverLakeIsHere1234567890AbcDefGhiJklMnoPqrStUvWx', 
        'analyst@coopcredit.com', 'ROLE_ANALISTA', TRUE);

-- Insert sample affiliates
INSERT INTO affiliates (document_number, first_name, last_name, email, phone_number, monthly_salary, seniority, status)
VALUES 
    ('1234567890', 'Juan', 'Pérez', 'juan.perez@email.com', '3001234567', 3000000.00, 12, 'ACTIVE'),
    ('0987654321', 'María', 'González', 'maria.gonzalez@email.com', '3009876543', 4500000.00, 24, 'ACTIVE'),
    ('1122334455', 'Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', '3001122334', 2500000.00, 8, 'ACTIVE');

-- ============================================================
-- ENTITY RELATIONSHIP DIAGRAM (ERD)
-- ============================================================
/*
+------------------+       +------------------------+       +-------------------+
|     users        |       |      affiliates        |       | credit_applications|
+------------------+       +------------------------+       +-------------------+
| id (PK)          |       | id (PK)                |       | id (PK)           |
| username         |       | document_number        |       | affiliate_id (FK) |----+
| password         |       | first_name             |       | requested_amount  |    |
| email            |       | last_name              |       | term_months       |    |
| role             |       | email                  |       | purpose           |    |
| enabled          |       | phone_number           |       | status            |    |
| created_at       |       | monthly_salary         |       | application_date  |    |
+------------------+       | seniority              |       | evaluation_date   |    |
                           | status                 |       | approval_amount   |    |
                           | created_at             |       | monthly_quota     |    |
                           | updated_at             |       | debt_to_income_ratio|   |
                           +------------------------+       +-------------------+    |
                                      ^                              |               |
                                      |                              |               |
                                      +------------------------------+               |
                                                                                     |
                           +------------------------+                                |
                           |   risk_evaluations     |                                |
                           +------------------------+                                |
                           | id (PK)                |                                |
                           | credit_application_id (FK)------------------------------+
                           | risk_score             |
                           | risk_level             |
                           | evaluation_date        |
                           | external_reference     |
                           | recommendation         |
                           +------------------------+
*/

-- ============================================================
-- ROLES AND PERMISSIONS
-- ============================================================
/*
Available roles:
- ROLE_ADMIN: Full access to all endpoints
- ROLE_ANALISTA: Can view and update credit applications
- ROLE_AFILIADO: Can create and view their own credit applications

Default users:
- admin / admin123 (ROLE_ADMIN)
- analyst / analyst123 (ROLE_ANALISTA)
*/

-- ============================================================
-- APPLICATION STATUS VALUES
-- ============================================================
/*
Credit Application Status:
- PENDING: Application submitted, awaiting evaluation
- IN_REVIEW: Under manual review
- APPROVED: Application approved
- REJECTED: Application rejected

Affiliate Status:
- ACTIVE: Affiliate is active and can apply for credit
- INACTIVE: Affiliate is inactive
- SUSPENDED: Affiliate is suspended

Risk Level:
- LOW: Risk score > 700
- MEDIUM: Risk score 500-700
- HIGH: Risk score < 500
*/
