-- Insert sample accounts
INSERT INTO accounts (account_number, balance, account_type, status, holder_name, holder_email, created_date, updated_date, version)
VALUES 
('ACC1234567890', 5000.00, 'SAVINGS', 'ACTIVE', 'John Doe', 'john.doe@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ACC1234567891', 10000.00, 'CHECKING', 'ACTIVE', 'Jane Smith', 'jane.smith@email.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ACC1234567892', 25000.00, 'BUSINESS', 'ACTIVE', 'Acme Corp', 'info@acmecorp.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0)
ON CONFLICT (account_number) DO NOTHING;

-- Insert sample transactions
INSERT INTO transactions (amount, type, transaction_date, description, reference_number, balance_after, account_id, related_account_number)
SELECT 
    5000.00, 
    'DEPOSIT', 
    CURRENT_TIMESTAMP, 
    'Initial deposit', 
    'TXNINIT000001', 
    5000.00, 
    a.id, 
    NULL
FROM accounts a WHERE a.account_number = 'ACC1234567890'
ON CONFLICT DO NOTHING;

INSERT INTO transactions (amount, type, transaction_date, description, reference_number, balance_after, account_id, related_account_number)
SELECT 
    10000.00, 
    'DEPOSIT', 
    CURRENT_TIMESTAMP, 
    'Initial deposit', 
    'TXNINIT000002', 
    10000.00, 
    a.id, 
    NULL
FROM accounts a WHERE a.account_number = 'ACC1234567891'
ON CONFLICT DO NOTHING;

INSERT INTO transactions (amount, type, transaction_date, description, reference_number, balance_after, account_id, related_account_number)
SELECT 
    25000.00, 
    'DEPOSIT', 
    CURRENT_TIMESTAMP, 
    'Initial deposit', 
    'TXNINIT000003', 
    25000.00, 
    a.id, 
    NULL
FROM accounts a WHERE a.account_number = 'ACC1234567892'
ON CONFLICT DO NOTHING;