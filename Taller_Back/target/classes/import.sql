INSERT INTO country_entity (id, name, iso_code) VALUES (1, 'Colombia', 'CO');
INSERT INTO country_entity (id, name, iso_code) VALUES (2, 'United Kingdom', 'UK');
INSERT INTO country_entity (id, name, iso_code) VALUES (3, 'Japan', 'JP');
INSERT INTO country_entity (id, name, iso_code) VALUES (4, 'Germany', 'DE');
ALTER TABLE country_entity ALTER COLUMN id RESTART WITH 5;

INSERT INTO city_entity (id, name, country_id) VALUES (1, 'Bogota', 1);
INSERT INTO city_entity (id, name, country_id) VALUES (2, 'London', 2);
INSERT INTO city_entity (id, name, country_id) VALUES (3, 'Berlin', 4);
INSERT INTO city_entity (id, name, country_id) VALUES (4, 'Tokio', 3);
ALTER TABLE city_entity ALTER COLUMN id RESTART WITH 5;

INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (1,  18.5, 'Partly cloudy', 72, '2025-03-01 08:00:00', 1);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (2,  20.1, 'Sunny',         65, '2025-03-15 14:00:00', 1);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (3,  16.3, 'Overcast',      80, '2025-04-01 10:30:00', 1);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (4,  12.3, 'Light rain',    90, '2025-03-01 09:00:00', 2);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (5,  11.8, 'Cloudy',        85, '2025-03-20 15:00:00', 2);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (6,   6.5, 'Clear',         70, '2025-03-01 07:00:00', 3);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (7,   9.2, 'Cloudy',        75, '2025-04-01 12:00:00', 3);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (8,  15.0, 'Sunny',         60, '2025-03-01 10:00:00', 4);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (9,  19.5, 'Partly cloudy', 68, '2025-03-20 16:00:00', 4);
INSERT INTO weather_record_entity (id, tempc, condition, humidity, recorded_at, city_id) VALUES (10, 22.0, 'Sunny',         55, '2025-04-01 09:00:00', 4);
ALTER TABLE weather_record_entity ALTER COLUMN id RESTART WITH 11;
