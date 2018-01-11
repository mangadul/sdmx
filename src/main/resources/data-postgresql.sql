/***************
 * PUBLICATION *
 ***************/

insert into publication (id, description, isnotice, title, created, updated, account_id) values
  (5, 'asdfasdfasdf', true, 'Maintenance Notice', now(), now(), 1),
  (6, 'sdfsdfsdfsdfsdf', false, 'Changes in methods of calculating and announcing the Chinese', now(), now(), 1)
;

select setval('publication_id_seq'::regclass, (select max(id) from publication));

/**************
 * STATISTICS *
 **************/

-- Category
insert into category (id, name, parent_id) values
  (1, 'Publication', null),
    (11, 'SEKI', 1),
    (12, 'ITEMS', 1),
    (13, 'SEKDA', 1),
  (2, 'Exchange Rate Policy', null),
  (3, 'Money, Credit and Banking', null),
  (4, 'Financial Corporations', null),
  (5, 'Financial Markets', null),
  (6, 'Macroeconomic', null),
  (7, 'Balance of Payments', null),
  (8, 'Supervisory', null),
  (9, 'Payment Statistics', null),
  (10, 'BI Surveys', null)
;

select setval('category_id_seq'::regclass, (select max(id) from category));

-- Variable
insert into variable (id, name, parent_id) values
  (1, 'Bank Notes and Coins in Circulation', null),
  (2, 'Monetary Base', null)
;

select setval('variable_id_seq'::regclass, (select max(id) from variable));

-- Data Set
insert into data_set (attributes, month, year, value, variable_id) values
  ('{"country":"INA"}', '01', '2017', 12345, 1),
  ('{"country":"INA"}', '02', '2017', 12345, 1),
  ('{"country":"INA"}', '03', '2017', 12345, 1),
  ('{"country":"INA"}', '04', '2017', 12345, 1),
  ('{"country":"INA"}', '05', '2017', 12345, 1),

  ('{"country":"INA"}', '01', '2016', 12345, 2),
  ('{"country":"INA"}', '02', '2016', 12345, 2),
  ('{"country":"INA"}', '03', '2016', 32042, 2),
  ('{"country":"INA"}', '04', '2016', 12345, 2),
  ('{"country":"INA"}', '05', '2016', 12345, 2)
;

-- Data Flow
insert into data_flow (id, name, category_id, note, meta) values
  (1, 'Money & Banking', 3, ';lkajsdf l;aksdjf;aoisdflaksjdfisjdflkjsdf osidjflksdjf oisjdf', '{"author":"admin"}'),
  (2, 'Balance of Payments', 3, 'klqwelkqw elqkwje qowieqwlkejqw eoiqwjelqkwjeqlkwejoi', '{"author":"admin"}')
;

insert into data_flow_item (data_flow_id, variable_id) values
  (1, 1),
  (1, 2),
  (2, 1),
  (2, 2)
;
