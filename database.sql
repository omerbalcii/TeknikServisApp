--
-- PostgreSQL database dump
--

-- Dumped from database version 15.5
-- Dumped by pg_dump version 15.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: TeknikServis; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "TeknikServis" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_Türkiye.1254';


ALTER DATABASE "TeknikServis" OWNER TO postgres;

\connect "TeknikServis"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: AUTHORITIES; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."AUTHORITIES" (
    username character varying,
    authority character varying
);


ALTER TABLE public."AUTHORITIES" OWNER TO postgres;

--
-- Name: BOOKING; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."BOOKING" (
    id bigint NOT NULL,
    note text,
    booking_date timestamp with time zone NOT NULL,
    status character varying(50) NOT NULL,
    service_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public."BOOKING" OWNER TO postgres;

--
-- Name: SERVICE; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."SERVICE" (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    duration integer NOT NULL,
    laptop integer NOT NULL,
    desktop integer NOT NULL,
    mac integer NOT NULL
);


ALTER TABLE public."SERVICE" OWNER TO postgres;

--
-- Name: Islemler_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."SERVICE" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Islemler_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: USERS; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."USERS" (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public."USERS" OWNER TO postgres;

--
-- Name: Kullanicilar_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."USERS" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Kullanicilar_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: PRODUCT; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."PRODUCT" (
    id bigint NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public."PRODUCT" OWNER TO postgres;

--
-- Name: PRODUCT_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."PRODUCT" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."PRODUCT_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: PROPOSAL; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."PROPOSAL" (
    id bigint NOT NULL,
    note character varying(1000),
    price numeric NOT NULL,
    user_id bigint NOT NULL,
    status boolean NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE public."PROPOSAL" OWNER TO postgres;

--
-- Name: PROPOSAL_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."PROPOSAL" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."PROPOSAL_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ROLES; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."ROLES" (
    rolename character varying NOT NULL
);


ALTER TABLE public."ROLES" OWNER TO postgres;

--
-- Name: SALE; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."SALE" (
    id bigint NOT NULL,
    note character varying(255) NOT NULL,
    price numeric NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE public."SALE" OWNER TO postgres;

--
-- Name: SALE_LOG; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."SALE_LOG" (
    id bigint NOT NULL,
    sale_log_date date NOT NULL,
    credit_card character varying(20) NOT NULL,
    sale_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public."SALE_LOG" OWNER TO postgres;

--
-- Name: SALE_LOGG_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."SALE_LOG" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."SALE_LOGG_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: Satis_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."SALE" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Satis_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: AUTHORITIES; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: BOOKING; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: PRODUCT; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: PROPOSAL; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: ROLES; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: SALE; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: SALE_LOG; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: SERVICE; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: USERS; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: Islemler_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Islemler_id_seq"', 1, false);


--
-- Name: Kullanicilar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Kullanicilar_id_seq"', 2, true);


--
-- Name: PRODUCT_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."PRODUCT_id_seq"', 1, false);


--
-- Name: PROPOSAL_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."PROPOSAL_id_seq"', 1, false);


--
-- Name: SALE_LOGG_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."SALE_LOGG_id_seq"', 1, false);


--
-- Name: Satis_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Satis_id_seq"', 1, false);


--
-- Name: ROLES ROLES_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."ROLES"
    ADD CONSTRAINT "ROLES_pkey" PRIMARY KEY (rolename);


--
-- Name: BOOKING booking_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BOOKING"
    ADD CONSTRAINT booking_id PRIMARY KEY (id);


--
-- Name: USERS kullanici_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."USERS"
    ADD CONSTRAINT kullanici_id PRIMARY KEY (id);


--
-- Name: PRODUCT product_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PRODUCT"
    ADD CONSTRAINT product_id PRIMARY KEY (id);


--
-- Name: PROPOSAL proposal_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PROPOSAL"
    ADD CONSTRAINT proposal_id PRIMARY KEY (id);


--
-- Name: SALE_LOG saleLog_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SALE_LOG"
    ADD CONSTRAINT "saleLog_id" PRIMARY KEY (id);


--
-- Name: SALE sale_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SALE"
    ADD CONSTRAINT sale_id PRIMARY KEY (id);


--
-- Name: SERVICE service_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SERVICE"
    ADD CONSTRAINT service_id PRIMARY KEY (id);


--
-- Name: USERS username_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."USERS"
    ADD CONSTRAINT username_unique UNIQUE (username);


--
-- Name: AUTHORITIES authority; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AUTHORITIES"
    ADD CONSTRAINT authority FOREIGN KEY (authority) REFERENCES public."ROLES"(rolename);


--
-- Name: PROPOSAL product_proposal_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PROPOSAL"
    ADD CONSTRAINT "product_proposal_FK" FOREIGN KEY (product_id) REFERENCES public."PRODUCT"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: PROPOSAL proposal_user_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."PROPOSAL"
    ADD CONSTRAINT "proposal_user_FK" FOREIGN KEY (user_id) REFERENCES public."USERS"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: SALE sale_product_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SALE"
    ADD CONSTRAINT "sale_product_FK" FOREIGN KEY (product_id) REFERENCES public."PRODUCT"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: SALE_LOG sale_saleLog_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SALE_LOG"
    ADD CONSTRAINT "sale_saleLog_FK" FOREIGN KEY (sale_id) REFERENCES public."SALE"(id);


--
-- Name: BOOKING service_booking_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BOOKING"
    ADD CONSTRAINT "service_booking_FK" FOREIGN KEY (service_id) REFERENCES public."SERVICE"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- Name: SALE_LOG user_saleLog_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SALE_LOG"
    ADD CONSTRAINT "user_saleLog_FK" FOREIGN KEY (user_id) REFERENCES public."USERS"(id);


--
-- Name: AUTHORITIES username; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."AUTHORITIES"
    ADD CONSTRAINT username FOREIGN KEY (username) REFERENCES public."USERS"(username);


--
-- Name: BOOKING users_booking_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."BOOKING"
    ADD CONSTRAINT "users_booking_FK" FOREIGN KEY (user_id) REFERENCES public."USERS"(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- PostgreSQL database dump complete
--

