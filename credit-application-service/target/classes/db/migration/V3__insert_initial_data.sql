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
