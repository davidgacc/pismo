CREATE TABLE operation_types (
    operation_type_id SERIAL PRIMARY KEY,  -- Auto-incrementing primary key
    description VARCHAR(255) NOT NULL
);

CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,           -- Auto-incrementing primary key
    document_number VARCHAR(255) NOT NULL       -- Add more fields as needed
);

CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,       -- Auto-incrementing primary key
    account_id INT,                          -- Foreign key reference
    operation_type_id INT,                   -- Foreign key reference
    amount DOUBLE PRECISION NOT NULL,        -- Amount of the transaction
    event_date TIMESTAMP NOT NULL,           -- Date and time of the event
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),  -- Foreign key constraint
    FOREIGN KEY (operation_type_id) REFERENCES operation_types(operation_type_id)  -- Foreign key constraint
);