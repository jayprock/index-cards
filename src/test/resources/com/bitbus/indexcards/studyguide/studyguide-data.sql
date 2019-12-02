insert into STUDY_GUIDE (study_guide_id, name, description, user_id) values(2, 'The History of Math', 'The origination of math', 1);
insert into STUDY_GUIDE (study_guide_id, name, description, user_id) values(3, 'Physics 101', 'Basic physics concepts', 1);
insert into STUDY_GUIDE (study_guide_id, name, description, user_id) values(4, 'Algebra', 'Algebra lessons', 1);
insert into STUDY_GUIDE (study_guide_id, name, description, user_id) values(5, 'Zach Study Guide', 'Things that Zach likes', 1);


insert into STUDY_GUIDE_TAG values(2, 'History');
insert into STUDY_GUIDE_TAG values(3, 'Physics');
insert into STUDY_GUIDE_TAG values(4, 'Mathematics');
insert into STUDY_GUIDE_TAG values(5, 'Algebra');
insert into STUDY_GUIDE_TAG values(6, 'Test');

insert into MAP_STUDY_GUIDE_TAG values (2, 2);
insert into MAP_STUDY_GUIDE_TAG values (3, 3);
insert into MAP_STUDY_GUIDE_TAG values (4, 4);
insert into MAP_STUDY_GUIDE_TAG values (4, 5);
insert into MAP_STUDY_GUIDE_TAG values (5, 6);
