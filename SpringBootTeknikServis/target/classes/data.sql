INSERT INTO public."SERVICE" ("description", "desktop", "laptop", "mac", "duration")
VALUES ('Formatlama', 50, 50, 100, 2);
INSERT INTO public."SERVICE" ("description", "desktop", "laptop", "mac", "duration")
VALUES ('Virüs Temizliği', 100, 100, 100, 4);
INSERT INTO public."SERVICE" ("description", "desktop", "laptop", "mac", "duration")
VALUES ('Diskten Veri Kurtarma', 200, 200, 400, 10);
INSERT INTO public."SERVICE" ("description", "desktop", "laptop", "mac", "duration")
VALUES ('Fan ve Termal Macun Temizliği', 30, 100, 200, 1);

INSERT INTO public."PRODUCT" ("name")
VALUES ('CPU');
INSERT INTO public."PRODUCT" ("name")
VALUES ('GPU');
INSERT INTO public."PRODUCT" ("name")
VALUES ('RAM');
INSERT INTO public."PRODUCT" ("name")
VALUES ('MOTHERBOARD');

INSERT INTO public."ROLES"("rolename") VALUES ('ROLE_USER');
INSERT INTO public."ROLES"("rolename") VALUES ('ROLE_ADMIN');

-- Sifreler = 1234
INSERT INTO public."USERS"("username", "email", "password")
VALUES ('admin', 'admin@gmail.com', '$2a$10$TXOKfdBtulAHucfVhzVyoOi0I1FROjRbShr/gKlaC19Lh6dsGuxAu');
INSERT INTO public."USERS"("username", "email", "password")
VALUES ('user', 'user@gmail.com', '$2a$10$IWh0iCO.NuUWSAcXqQFuC.yHSBKZYyhWUWk3vJvs5m11iWtvkPvs6');

INSERT INTO public."AUTHORITIES" ("username", "authority") values ('user','ROLE_USER');
INSERT INTO public."AUTHORITIES" ("username", "authority") values ('admin','ROLE_ADMIN');