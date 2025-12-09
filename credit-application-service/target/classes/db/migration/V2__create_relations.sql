-- Add foreign key constraints
ALTER TABLE credit_applications
    ADD CONSTRAINT fk_application_affiliate
    FOREIGN KEY (affiliate_id) REFERENCES affiliates(id)
    ON DELETE CASCADE;

ALTER TABLE risk_evaluations
    ADD CONSTRAINT fk_evaluation_application
    FOREIGN KEY (credit_application_id) REFERENCES credit_applications(id)
    ON DELETE CASCADE;
