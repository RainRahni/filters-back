-- Insert a Filter
INSERT INTO filters (name) VALUES ('FilterOne');
INSERT INTO filters (name) VALUES ('FilterTwo');


INSERT INTO criterias (type, comparator, metric, filter_id) VALUES ('AMOUNT', 'More', '5', 1);
INSERT INTO criterias (type, comparator, metric, filter_id) VALUES ('TITLE', 'Starts with', 'Meow', 1);

INSERT INTO criterias (type, comparator, metric, filter_id) VALUES ('AMOUNT', 'Less', '4', 2);