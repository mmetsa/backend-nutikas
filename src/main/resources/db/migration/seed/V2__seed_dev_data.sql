INSERT INTO school (name, short_name, address, email, phone) VALUES
('Tamsalu Gümnaasium', 'TMG', 'Kesk-1, Tamsalu, L-Virumaa', 'tamsalukool@tamsalu.ee', '55331234'),
('Rakvere Gümnaasium', 'RG', 'Kastani pst 12, Rakvere', 'rg@rakvere.ee', '55221234'),
('Tallinna Tehnikakool', 'TalTek', 'Sõpruse pst 12, Mustamäe, Tallinn', 'taltek@taltek,ee', '56068793')
ON CONFLICT DO NOTHING;

INSERT INTO class (name, number, school_id) VALUES
('1a', 1, (SELECT id FROM school WHERE name = 'Tamsalu Gümnaasium')),
('1b', 1, (SELECT id FROM school WHERE name = 'Tamsalu Gümnaasium')),
('2a', 2, (SELECT id FROM school WHERE name = 'Tamsalu Gümnaasium')),

('7', 7, (SELECT id FROM school WHERE name = 'Rakvere Gümnaasium')),
('8', 8, (SELECT id FROM school WHERE name = 'Rakvere Gümnaasium')),
('9', 9, (SELECT id FROM school WHERE name = 'Rakvere Gümnaasium')),

('10', 10, (SELECT id FROM school WHERE name = 'Tallinna Tehnikakool')),
('11', 11, (SELECT id FROM school WHERE name = 'Tallinna Tehnikakool')),
('12a', 12, (SELECT id FROM school WHERE name = 'Tallinna Tehnikakool'))
ON CONFLICT DO NOTHING;

INSERT INTO roles (name) VALUES
('STUDENT'), ('TEACHER'), ('ADMIN'), ('SCHOOL_ADMIN');

INSERT INTO users (first_name, last_name, nickname, birthday, email, password_hash) VALUES
('Maiko 1a', 'Metsalu 1a', 'Maiko1a', now() - interval '7 years', 'metsalumaiko1a@gmail.com', 'hashedpsw'),
('Maiko 1b', 'Metsalu 1b', 'Maiko1b', now() - interval '7 years', 'metsalumaiko1b@gmail.com', 'hashedpsw'),
('Maiko 2a', 'Metsalu 2a', 'Maiko2a', now() - interval '8 years', 'metsalumaiko2a@gmail.com', 'hashedpsw'),


('Maiko 7', 'Metsalu 7', 'Maiko7', now() - interval '13 years', 'metsalumaiko7@gmail.com', 'hashedpsw'),
('Maiko 8', 'Metsalu 8', 'Maiko8', now() - interval '14 years', 'metsalumaiko8@gmail.com', 'hashedpsw'),
('Maiko 9', 'Metsalu 9', 'Maiko9', now() - interval '15 years', 'metsalumaiko9@gmail.com', 'hashedpsw'),


('Maiko õpetaja', 'Metsalu', 'Maiko op', now() - interval '16 years', 'metsalumaiko10@gmail.com', 'hashedpsw'),
('Maiko admin', 'Metsalu', 'Maiko adm', now() - interval '17 years', 'metsalumaiko11@gmail.com', 'hashedpsw'),
('Maiko Kooli admin', 'Metsalu', 'Maiko sc_adm', now() - interval '18 years', 'metsalumaiko12@gmail.com', 'hashedpsw');

INSERT INTO users_roles (roles_id, users_id, school_id) VALUES
((SELECT id from roles where name = 'STUDENT'),
 (SELECT id from users where first_name = 'Maiko 1a'),
 (SELECT id from school where name = 'Tamsalu Gümnaasium')
 ),
((SELECT id from roles where name = 'STUDENT'),
 (SELECT id from users where first_name = 'Maiko 1b'),
 (SELECT id from school where name = 'Tamsalu Gümnaasium')
),
((SELECT id from roles where name = 'STUDENT'),
   (SELECT id from users where first_name = 'Maiko 2a'),
   (SELECT id from school where name = 'Tamsalu Gümnaasium')
),
((SELECT id from roles where name = 'STUDENT'),
 (SELECT id from users where first_name = 'Maiko 7'),
 (SELECT id from school where name = 'Rakvere Gümnaasium')
),
((SELECT id from roles where name = 'STUDENT'),
 (SELECT id from users where first_name = 'Maiko 8'),
 (SELECT id from school where name = 'Rakvere Gümnaasium')
),
((SELECT id from roles where name = 'STUDENT'),
 (SELECT id from users where first_name = 'Maiko 9'),
 (SELECT id from school where name = 'Rakvere Gümnaasium')
),
((SELECT id from roles where name = 'TEACHER'),
 (SELECT id from users where first_name = 'Maiko õpetaja'),
 (SELECT id from school where name = 'Tallinna Tehnikakool')
),
((SELECT id from roles where name = 'ADMIN'),
 (SELECT id from users where first_name = 'Maiko admin'),
 (SELECT id from school where name = 'Tallinna Tehnikakool')
),
((SELECT id from roles where name = 'SCHOOL_ADMIN'),
 (SELECT id from users where first_name = 'Maiko Kooli admin'),
 (SELECT id from school where name = 'Tallinna Tehnikakool')
);