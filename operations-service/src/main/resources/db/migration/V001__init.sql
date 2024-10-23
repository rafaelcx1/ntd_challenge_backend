create table operations (
    id integer not null auto_increment,
    creation_date datetime(6),
    cost decimal(2),
    type enum ('ADDITION','SUBTRACTION','MULTIPLICATION','DIVISION','SQUARE_ROOT','RANDOM_STRING'),
    primary key (id)
);

INSERT INTO operations(creation_date, cost, type) VALUES(CURRENT_TIMESTAMP, 1, 'ADDITION');
INSERT INTO operations(creation_date, cost, type) VALUES(CURRENT_TIMESTAMP, 1, 'SUBTRACTION');
INSERT INTO operations(creation_date, cost, type) VALUES(CURRENT_TIMESTAMP, 3, 'MULTIPLICATION');
INSERT INTO operations(creation_date, cost, type) VALUES(CURRENT_TIMESTAMP, 3, 'DIVISION');
INSERT INTO operations(creation_date, cost, type) VALUES(CURRENT_TIMESTAMP, 3, 'SQUARE_ROOT');
INSERT INTO operations(creation_date, cost, type) VALUES(CURRENT_TIMESTAMP, 5, 'RANDOM_STRING');
