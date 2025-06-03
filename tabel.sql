CREATE TABLE math_questions (
    id SERIAL PRIMARY KEY,
    question_id VARCHAR(20),
    type VARCHAR(20), -- "red", "blue", etc.
    template TEXT
);
