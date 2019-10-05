insert into password_reset_token (password_reset_token_id, user_id, token_hash, generation_time)
values (101, 1, '$2a$10$MA98uYq4K5vmhp9.nPkPtudhQhE8Jj4mL5gNFOvZnXy7kndA5vzT2', now());

INSERT INTO `user` (`user_id`,`email`,`password`,`username`) VALUES (2,'test2@gmail.com','$2a$10$MA98uYq4K5vmhp9.nPkPtudhQhE8Jj4mL5gNFOvZnXy7kndA5vzT2','test2');
insert into password_reset_token (password_reset_token_id, user_id, token_hash, generation_time)
values (102, 2, '$2a$10$MA98uYq4K5vmhp9.nPkPtudhQhE8Jj4mL5gNFOvZnXy7kndA5vzT2', now());
