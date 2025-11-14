CREATE TABLE "user" (
	id varchar(36) NOT NULL,
	login varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	create_at timestamp NULL,
	update_at timestamp NULL,
	access_at timestamp NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE INDEX user_login_idx ON "user" (login);

CREATE TABLE city (
	id varchar(36) NOT NULL,
	city varchar(100) NOT NULL,
	state varchar(50) NULL,
	country varchar(50) NULL,
	CONSTRAINT city_pk PRIMARY KEY (id)
);

CREATE INDEX city_country_idx ON city USING btree (country);

CREATE INDEX city_state_idx ON city USING btree (state);

CREATE TABLE address (
	id varchar(36) NOT NULL,
	street varchar(100) NULL,
	street_number int4 NULL,
	apartment varchar(100) NULL,
	neighborhood varchar(100) NULL,
	city_id varchar(36) NOT NULL,
	zip_code varchar(30) NULL,
	CONSTRAINT address_pk PRIMARY KEY (id)
);

CREATE INDEX address_city_id_idx ON address USING btree (city_id);

CREATE TABLE "member" (
	id varchar(36) NOT NULL,
	user_id varchar(36) NOT NULL,
	address_id varchar(36) NOT NULL,
	national_id varchar(50) NOT NULL,
	"name" varchar(100) NOT NULL,
	email varchar(255) NOT NULL,
	create_at timestamp NOT NULL,
	update_at timestamp NOT NULL,
	phone_number varchar(50) NOT NULL,
	"type" varchar(20) NULL,
	CONSTRAINT member_pkey PRIMARY KEY (id)
);

CREATE INDEX member_address_id_idx ON member USING btree (address_id);

CREATE INDEX member_user_id_idx ON member USING btree (user_id);
