--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.11
-- Dumped by pg_dump version 9.5.11

-- Started on 2018-02-27 21:36:00 CET

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12395)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 116030)
-- Name: posts; Type: TABLE; Schema: public; Owner: markkovacs
--

CREATE TABLE posts (
    id bigint NOT NULL,
    content character varying(255),
    posted_at timestamp without time zone,
    user_id bigint
);


ALTER TABLE posts OWNER TO markkovacs;

--
-- TOC entry 181 (class 1259 OID 116028)
-- Name: posts_id_seq; Type: SEQUENCE; Schema: public; Owner: markkovacs
--

CREATE SEQUENCE posts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE posts_id_seq OWNER TO markkovacs;

--
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 181
-- Name: posts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: markkovacs
--

ALTER SEQUENCE posts_id_seq OWNED BY posts.id;


--
-- TOC entry 184 (class 1259 OID 116038)
-- Name: roles; Type: TABLE; Schema: public; Owner: markkovacs
--

CREATE TABLE roles (
    id bigint NOT NULL,
    role_type character varying(255) NOT NULL
);


ALTER TABLE roles OWNER TO markkovacs;

--
-- TOC entry 183 (class 1259 OID 116036)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: markkovacs
--

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE roles_id_seq OWNER TO markkovacs;

--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 183
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: markkovacs
--

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;


--
-- TOC entry 186 (class 1259 OID 116046)
-- Name: users; Type: TABLE; Schema: public; Owner: markkovacs
--

CREATE TABLE users (
    id bigint NOT NULL,
    city character varying(255),
    email character varying(255) NOT NULL,
    family_name character varying(255),
    given_name character varying(255),
    middle_name character varying(255),
    registered_at timestamp without time zone,
    user_hash character varying(255) NOT NULL
);


ALTER TABLE users OWNER TO markkovacs;

--
-- TOC entry 185 (class 1259 OID 116044)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: markkovacs
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO markkovacs;

--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: markkovacs
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 187 (class 1259 OID 116055)
-- Name: users_roles; Type: TABLE; Schema: public; Owner: markkovacs
--

CREATE TABLE users_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE users_roles OWNER TO markkovacs;

--
-- TOC entry 2036 (class 2604 OID 116033)
-- Name: id; Type: DEFAULT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY posts ALTER COLUMN id SET DEFAULT nextval('posts_id_seq'::regclass);


--
-- TOC entry 2037 (class 2604 OID 116041)
-- Name: id; Type: DEFAULT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);


--
-- TOC entry 2038 (class 2604 OID 116049)
-- Name: id; Type: DEFAULT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 2169 (class 0 OID 116030)
-- Dependencies: 182
-- Data for Name: posts; Type: TABLE DATA; Schema: public; Owner: markkovacs
--

COPY posts (id, content, posted_at, user_id) FROM stdin;
1	A very good content for this post.	2018-02-27 21:18:35.693	1
2	A bit worse content here.	2018-02-27 21:18:35.693	1
3	Generating some cool content.	2018-02-27 21:18:35.693	1
4	I like to write for nothing.	2018-02-27 21:18:35.693	2
5	Typing in vain, what a pleasure.	2018-02-27 21:18:35.693	2
\.


--
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 181
-- Name: posts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: markkovacs
--

SELECT pg_catalog.setval('posts_id_seq', 5, true);


--
-- TOC entry 2171 (class 0 OID 116038)
-- Dependencies: 184
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: markkovacs
--

COPY roles (id, role_type) FROM stdin;
1	ADMIN
2	USER
\.


--
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 183
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: markkovacs
--

SELECT pg_catalog.setval('roles_id_seq', 2, true);


--
-- TOC entry 2173 (class 0 OID 116046)
-- Dependencies: 186
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: markkovacs
--

COPY users (id, city, email, family_name, given_name, middle_name, registered_at, user_hash) FROM stdin;
1	BDP	johndoe@raven.com	Doe	John	\N	2018-02-27 21:18:35.693	fa8d05c2-7a31-41ed-9115-90e2b87fa81f
2	WRS	maryjane@raven.com	Jane	Mary	Rose	2018-02-27 21:18:35.693	02a706db-4cde-4408-b2cb-0a81337410f1
3	KRK	billsmith@raven.com	Smith	Bill	\N	2018-02-27 21:18:35.693	4ec90490-2ea3-4c9b-8a61-1211a0bac6ec
\.


--
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: markkovacs
--

SELECT pg_catalog.setval('users_id_seq', 3, true);


--
-- TOC entry 2174 (class 0 OID 116055)
-- Dependencies: 187
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: markkovacs
--

COPY users_roles (user_id, role_id) FROM stdin;
1	1
1	2
2	1
2	2
3	2
\.


--
-- TOC entry 2040 (class 2606 OID 116035)
-- Name: posts_pkey; Type: CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- TOC entry 2042 (class 2606 OID 116043)
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 2046 (class 2606 OID 116063)
-- Name: uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 2044 (class 2606 OID 116061)
-- Name: uk_6kpjgt1lwdofsckw70uo9eo0; Type: CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT uk_6kpjgt1lwdofsckw70uo9eo0 UNIQUE (role_type);


--
-- TOC entry 2048 (class 2606 OID 116054)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2050 (class 2606 OID 116059)
-- Name: users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 2053 (class 2606 OID 116074)
-- Name: fk2o0jvgh89lemvvo17cbqvdxaa; Type: FK CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2051 (class 2606 OID 116064)
-- Name: fk5lidm6cqbc7u4xhqpxm898qme; Type: FK CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY posts
    ADD CONSTRAINT fk5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2052 (class 2606 OID 116069)
-- Name: fkj6m8fwv7oqv74fcehir1a9ffy; Type: FK CONSTRAINT; Schema: public; Owner: markkovacs
--

ALTER TABLE ONLY users_roles
    ADD CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-02-27 21:36:00 CET

--
-- PostgreSQL database dump complete
--

