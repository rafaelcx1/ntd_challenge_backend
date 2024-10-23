create table users (
    id integer not null auto_increment,
    creation_date datetime(6),
    email varchar(255),
    password varchar(255),
    status enum ('ACTIVE','INACTIVE'),
    primary key (id)
);

INSERT INTO users(creation_date, email, password, status) VALUES (CURRENT_TIMESTAMP, 'user1@user1.com', '$2a$10$0cDQYczGkeneax6WD.SoY.tFxqQ3rioQSHyt2u/eYMWT9Kymdse.a', 'ACTIVE');
INSERT INTO users(creation_date, email, password, status) VALUES (CURRENT_TIMESTAMP, 'user2@user2.com', '$2a$10$qLYJDV5ypj7nCIRztoq.H.Gr5jdTM3hmAmsUQ24lZhUXtZ8pWGx4C', 'ACTIVE');
INSERT INTO users(creation_date, email, password, status) VALUES (CURRENT_TIMESTAMP, 'user3@user3.com', '$2a$10$o3yvl2Iam6V7kkinuFuY9.CbTttJjPX9EnGlGYNLBi0EKxs2HxHbq', 'ACTIVE');
