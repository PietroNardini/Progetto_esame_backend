--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-05-08 19:00:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 883 (class 1247 OID 17412)
-- Name: day_of_week; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.day_of_week AS ENUM (
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
);


ALTER TYPE public.day_of_week OWNER TO postgres;

--
-- TOC entry 880 (class 1247 OID 17406)
-- Name: tipo_ora; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tipo_ora AS ENUM (
    'NORMALE',
    'STRAORDINARIO'
);


ALTER TYPE public.tipo_ora OWNER TO postgres;

--
-- TOC entry 877 (class 1247 OID 17399)
-- Name: tipo_turno; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tipo_turno AS ENUM (
    'MATTINA',
    'POMERIGGIO',
    'SERA'
);


ALTER TYPE public.tipo_turno OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 17006)
-- Name: impiegato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.impiegato (
    id bigint NOT NULL
);


ALTER TABLE public.impiegato OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17026)
-- Name: impiegato_pagato_ora; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.impiegato_pagato_ora (
    id bigint NOT NULL,
    paga_oraria double precision NOT NULL
);


ALTER TABLE public.impiegato_pagato_ora OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17016)
-- Name: impiegato_stipendiato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.impiegato_stipendiato (
    id bigint NOT NULL,
    stipendio_mensile double precision NOT NULL
);


ALTER TABLE public.impiegato_stipendiato OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 17448)
-- Name: impiegato_turno; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.impiegato_turno (
    turno_lavorativo_id bigint NOT NULL,
    impiegato_id bigint NOT NULL
);


ALTER TABLE public.impiegato_turno OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17036)
-- Name: manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manager (
    id bigint NOT NULL,
    stipendio bigint NOT NULL
);


ALTER TABLE public.manager OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17437)
-- Name: ora_lavorativa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ora_lavorativa (
    id bigint NOT NULL,
    tipo character varying(255) NOT NULL,
    turno_lavorativo_id bigint NOT NULL,
    fine time(6) without time zone NOT NULL,
    inizio time(6) without time zone NOT NULL
);


ALTER TABLE public.ora_lavorativa OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17436)
-- Name: ora_lavorativa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ora_lavorativa_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ora_lavorativa_id_seq OWNER TO postgres;

--
-- TOC entry 4984 (class 0 OID 0)
-- Dependencies: 228
-- Name: ora_lavorativa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ora_lavorativa_id_seq OWNED BY public.ora_lavorativa.id;


--
-- TOC entry 224 (class 1259 OID 17191)
-- Name: password_reset_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.password_reset_token (
    id bigint NOT NULL,
    token character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    expiry_date timestamp without time zone NOT NULL
);


ALTER TABLE public.password_reset_token OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17190)
-- Name: password_reset_token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.password_reset_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.password_reset_token_id_seq OWNER TO postgres;

--
-- TOC entry 4985 (class 0 OID 0)
-- Dependencies: 223
-- Name: password_reset_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.password_reset_token_id_seq OWNED BY public.password_reset_token.id;


--
-- TOC entry 225 (class 1259 OID 17213)
-- Name: password_reset_token_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.password_reset_token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.password_reset_token_seq OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17428)
-- Name: turno_lavorativo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.turno_lavorativo (
    id bigint NOT NULL,
    tipo_turno character varying(255) NOT NULL,
    data date NOT NULL
);


ALTER TABLE public.turno_lavorativo OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17427)
-- Name: turno_lavorativo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.turno_lavorativo_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.turno_lavorativo_id_seq OWNER TO postgres;

--
-- TOC entry 4986 (class 0 OID 0)
-- Dependencies: 226
-- Name: turno_lavorativo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.turno_lavorativo_id_seq OWNED BY public.turno_lavorativo.id;


--
-- TOC entry 218 (class 1259 OID 16996)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    cognome character varying(255) NOT NULL,
    telefono character varying(255) NOT NULL,
    dipartimento character varying(255) NOT NULL,
    data_di_nascita date DEFAULT '2000-01-01'::date NOT NULL
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16995)
-- Name: utente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.utente_id_seq OWNER TO postgres;

--
-- TOC entry 4987 (class 0 OID 0)
-- Dependencies: 217
-- Name: utente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utente_id_seq OWNED BY public.utente.id;


--
-- TOC entry 4791 (class 2604 OID 17463)
-- Name: ora_lavorativa id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa ALTER COLUMN id SET DEFAULT nextval('public.ora_lavorativa_id_seq'::regclass);


--
-- TOC entry 4789 (class 2604 OID 17202)
-- Name: password_reset_token id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_reset_token ALTER COLUMN id SET DEFAULT nextval('public.password_reset_token_id_seq'::regclass);


--
-- TOC entry 4790 (class 2604 OID 17474)
-- Name: turno_lavorativo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turno_lavorativo ALTER COLUMN id SET DEFAULT nextval('public.turno_lavorativo_id_seq'::regclass);


--
-- TOC entry 4787 (class 2604 OID 17165)
-- Name: utente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente ALTER COLUMN id SET DEFAULT nextval('public.utente_id_seq'::regclass);


--
-- TOC entry 4967 (class 0 OID 17006)
-- Dependencies: 219
-- Data for Name: impiegato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato (id) FROM stdin;
7
9
\.


--
-- TOC entry 4969 (class 0 OID 17026)
-- Dependencies: 221
-- Data for Name: impiegato_pagato_ora; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato_pagato_ora (id, paga_oraria) FROM stdin;
7	25
\.


--
-- TOC entry 4968 (class 0 OID 17016)
-- Dependencies: 220
-- Data for Name: impiegato_stipendiato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato_stipendiato (id, stipendio_mensile) FROM stdin;
9	1500
\.


--
-- TOC entry 4978 (class 0 OID 17448)
-- Dependencies: 230
-- Data for Name: impiegato_turno; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato_turno (turno_lavorativo_id, impiegato_id) FROM stdin;
\.


--
-- TOC entry 4970 (class 0 OID 17036)
-- Dependencies: 222
-- Data for Name: manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.manager (id, stipendio) FROM stdin;
1	5000
6	5000
10	5000
\.


--
-- TOC entry 4977 (class 0 OID 17437)
-- Dependencies: 229
-- Data for Name: ora_lavorativa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ora_lavorativa (id, tipo, turno_lavorativo_id, fine, inizio) FROM stdin;
22	NORMALE	22	22:00:00	18:00:00
23	NORMALE	23	22:00:00	18:00:00
24	NORMALE	24	22:00:00	18:00:00
25	NORMALE	25	22:00:00	18:00:00
26	NORMALE	26	22:00:00	18:00:00
27	NORMALE	27	22:00:00	18:00:00
28	NORMALE	28	22:00:00	18:00:00
29	NORMALE	29	22:00:00	18:00:00
30	NORMALE	30	22:00:00	18:00:00
31	NORMALE	31	22:00:00	18:00:00
32	NORMALE	32	22:00:00	18:00:00
33	NORMALE	33	22:00:00	18:00:00
34	NORMALE	34	22:00:00	18:00:00
35	NORMALE	35	22:00:00	18:00:00
36	NORMALE	36	22:00:00	18:00:00
37	NORMALE	37	17:00:00	13:00:00
38	NORMALE	38	17:00:00	13:00:00
39	NORMALE	39	17:00:00	13:00:00
40	NORMALE	40	17:00:00	13:00:00
41	NORMALE	41	17:00:00	13:00:00
42	NORMALE	42	17:00:00	13:00:00
43	NORMALE	43	17:00:00	13:00:00
44	NORMALE	44	17:00:00	13:00:00
45	NORMALE	45	17:00:00	13:00:00
46	NORMALE	46	17:00:00	13:00:00
47	NORMALE	47	17:00:00	13:00:00
48	NORMALE	48	17:00:00	13:00:00
49	NORMALE	49	17:00:00	13:00:00
50	NORMALE	50	17:00:00	13:00:00
51	NORMALE	51	17:00:00	13:00:00
52	NORMALE	52	17:00:00	13:00:00
53	NORMALE	53	17:00:00	13:00:00
54	NORMALE	54	17:00:00	13:00:00
55	NORMALE	55	17:00:00	13:00:00
56	NORMALE	56	17:00:00	13:00:00
57	NORMALE	57	17:00:00	13:00:00
58	NORMALE	58	17:00:00	13:00:00
59	NORMALE	59	17:00:00	13:00:00
60	NORMALE	60	17:00:00	13:00:00
61	NORMALE	61	17:00:00	13:00:00
62	NORMALE	62	17:00:00	13:00:00
63	NORMALE	63	17:00:00	13:00:00
64	NORMALE	64	17:00:00	13:00:00
65	NORMALE	65	17:00:00	13:00:00
66	NORMALE	66	17:00:00	13:00:00
67	NORMALE	67	17:00:00	13:00:00
68	NORMALE	68	22:00:00	18:00:00
69	NORMALE	69	22:00:00	18:00:00
70	NORMALE	70	22:00:00	18:00:00
71	NORMALE	71	22:00:00	18:00:00
72	NORMALE	72	12:00:00	08:00:00
73	NORMALE	73	22:00:00	18:00:00
74	NORMALE	74	12:00:00	08:00:00
75	NORMALE	75	22:00:00	18:00:00
76	NORMALE	76	12:00:00	08:00:00
77	NORMALE	77	22:00:00	18:00:00
78	NORMALE	78	12:00:00	08:00:00
79	NORMALE	79	22:00:00	18:00:00
80	NORMALE	80	22:00:00	18:00:00
81	NORMALE	81	22:00:00	18:00:00
82	NORMALE	82	22:00:00	18:00:00
83	NORMALE	83	22:00:00	18:00:00
84	NORMALE	84	22:00:00	18:00:00
85	NORMALE	85	22:00:00	18:00:00
86	NORMALE	86	22:00:00	18:00:00
87	NORMALE	87	22:00:00	18:00:00
88	NORMALE	88	12:00:00	08:00:00
89	NORMALE	89	12:00:00	08:00:00
90	NORMALE	90	12:00:00	08:00:00
91	NORMALE	91	12:00:00	08:00:00
92	NORMALE	92	12:00:00	08:00:00
93	NORMALE	93	12:00:00	08:00:00
94	NORMALE	94	12:00:00	08:00:00
95	NORMALE	95	12:00:00	08:00:00
96	NORMALE	96	12:00:00	08:00:00
97	NORMALE	97	12:00:00	08:00:00
98	NORMALE	98	12:00:00	08:00:00
99	NORMALE	99	12:00:00	08:00:00
100	NORMALE	100	12:00:00	08:00:00
101	NORMALE	101	12:00:00	08:00:00
102	NORMALE	102	12:00:00	08:00:00
103	NORMALE	103	12:00:00	08:00:00
104	NORMALE	104	12:00:00	08:00:00
105	NORMALE	105	12:00:00	08:00:00
106	NORMALE	106	12:00:00	08:00:00
107	NORMALE	107	12:00:00	08:00:00
108	NORMALE	108	12:00:00	08:00:00
109	NORMALE	109	12:00:00	08:00:00
110	NORMALE	110	12:00:00	08:00:00
111	NORMALE	111	12:00:00	08:00:00
112	NORMALE	112	12:00:00	08:00:00
113	NORMALE	113	12:00:00	08:00:00
114	NORMALE	114	12:00:00	08:00:00
\.


--
-- TOC entry 4972 (class 0 OID 17191)
-- Dependencies: 224
-- Data for Name: password_reset_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.password_reset_token (id, token, user_id, expiry_date) FROM stdin;
3	f23bac48-c791-4405-b120-700bc23aa4c8	10	2025-05-07 16:54:36.489829
\.


--
-- TOC entry 4975 (class 0 OID 17428)
-- Dependencies: 227
-- Data for Name: turno_lavorativo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.turno_lavorativo (id, tipo_turno, data) FROM stdin;
22	SERA	2025-05-11
23	SERA	2025-05-10
24	SERA	2025-05-09
25	SERA	2025-05-08
26	SERA	2025-05-15
27	SERA	2025-05-14
28	SERA	2025-05-13
29	SERA	2025-05-12
30	SERA	2025-05-03
31	SERA	2025-05-02
32	SERA	2025-05-01
33	SERA	2025-05-07
34	SERA	2025-05-06
35	SERA	2025-05-05
36	SERA	2025-05-04
37	POMERIGGIO	2025-05-18
38	POMERIGGIO	2025-05-17
39	POMERIGGIO	2025-05-20
40	POMERIGGIO	2025-05-19
41	POMERIGGIO	2025-05-22
42	POMERIGGIO	2025-05-21
43	POMERIGGIO	2025-05-24
44	POMERIGGIO	2025-05-23
45	POMERIGGIO	2025-05-26
46	POMERIGGIO	2025-05-25
47	POMERIGGIO	2025-05-28
48	POMERIGGIO	2025-05-27
49	POMERIGGIO	2025-05-30
50	POMERIGGIO	2025-05-29
51	POMERIGGIO	2025-05-31
52	POMERIGGIO	2025-05-02
53	POMERIGGIO	2025-05-01
54	POMERIGGIO	2025-05-04
55	POMERIGGIO	2025-05-03
56	POMERIGGIO	2025-05-06
57	POMERIGGIO	2025-05-05
58	POMERIGGIO	2025-05-08
59	POMERIGGIO	2025-05-07
60	POMERIGGIO	2025-05-10
61	POMERIGGIO	2025-05-09
62	POMERIGGIO	2025-05-12
63	POMERIGGIO	2025-05-11
64	POMERIGGIO	2025-05-14
65	POMERIGGIO	2025-05-13
66	POMERIGGIO	2025-05-16
67	POMERIGGIO	2025-05-15
68	SERA	2025-05-27
69	SERA	2025-05-26
70	SERA	2025-05-25
71	SERA	2025-05-24
72	MATTINA	2025-05-03
73	SERA	2025-05-31
74	MATTINA	2025-05-04
75	SERA	2025-05-30
76	MATTINA	2025-05-01
77	SERA	2025-05-29
78	MATTINA	2025-05-02
79	SERA	2025-05-28
80	SERA	2025-05-19
81	SERA	2025-05-18
82	SERA	2025-05-17
83	SERA	2025-05-16
84	SERA	2025-05-23
85	SERA	2025-05-22
86	SERA	2025-05-21
87	SERA	2025-05-20
88	MATTINA	2025-05-15
89	MATTINA	2025-05-16
90	MATTINA	2025-05-13
91	MATTINA	2025-05-14
92	MATTINA	2025-05-19
93	MATTINA	2025-05-20
94	MATTINA	2025-05-17
95	MATTINA	2025-05-18
96	MATTINA	2025-05-07
97	MATTINA	2025-05-08
98	MATTINA	2025-05-05
99	MATTINA	2025-05-06
100	MATTINA	2025-05-11
101	MATTINA	2025-05-12
102	MATTINA	2025-05-09
103	MATTINA	2025-05-10
104	MATTINA	2025-05-31
105	MATTINA	2025-05-29
106	MATTINA	2025-05-30
107	MATTINA	2025-05-23
108	MATTINA	2025-05-24
109	MATTINA	2025-05-21
110	MATTINA	2025-05-22
111	MATTINA	2025-05-27
112	MATTINA	2025-05-28
113	MATTINA	2025-05-25
114	MATTINA	2025-05-26
\.


--
-- TOC entry 4966 (class 0 OID 16996)
-- Dependencies: 218
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente (id, email, password, nome, cognome, telefono, dipartimento, data_di_nascita) FROM stdin;
1	manager@example.com	securepassword	Mario	Rossi	1234567890	HR	2000-01-01
6	manager2@example2.com	$2a$10$lgEjImP/A080mGPhdsBOKuPVm4LjGaCKTvZY7XZ3lhKdKGD/vlEFa	Mario	Bianchi	1234567890	HR	1999-01-01
7	impiegato@example.com	$2a$10$vGma5ao32QiaeodFVWQkFuRW4Lq8MKRtbPS.57kT1yuzApK0J9C8i	Luca	Verdi	1234567890	IT	1990-05-15
9	impiegato2@example.com	$2a$10$meDufToO78uslcsQmvt80.XzsYwvRbE0bMsFRlZsi4uAPiKoxK6i2	Luca	Verdi	1234567890	IT	1990-05-15
10	pietronardini124@gmail.com	$2a$10$OVkGeD4fdnyf2ofv.SM/pO63pKjXUow8HemHB5fs4IDrFbvYnvkb.	Mario	Bianchi	1234567890	IT	1999-01-01
\.


--
-- TOC entry 4988 (class 0 OID 0)
-- Dependencies: 228
-- Name: ora_lavorativa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ora_lavorativa_id_seq', 114, true);


--
-- TOC entry 4989 (class 0 OID 0)
-- Dependencies: 223
-- Name: password_reset_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.password_reset_token_id_seq', 1, false);


--
-- TOC entry 4990 (class 0 OID 0)
-- Dependencies: 225
-- Name: password_reset_token_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.password_reset_token_seq', 51, true);


--
-- TOC entry 4991 (class 0 OID 0)
-- Dependencies: 226
-- Name: turno_lavorativo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.turno_lavorativo_id_seq', 114, true);


--
-- TOC entry 4992 (class 0 OID 0)
-- Dependencies: 217
-- Name: utente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_id_seq', 10, true);


--
-- TOC entry 4801 (class 2606 OID 17117)
-- Name: impiegato_pagato_ora impiegato_pagato_ora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_pagato_ora
    ADD CONSTRAINT impiegato_pagato_ora_pkey PRIMARY KEY (id);


--
-- TOC entry 4797 (class 2606 OID 17069)
-- Name: impiegato impiegato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_pkey PRIMARY KEY (id);


--
-- TOC entry 4799 (class 2606 OID 17128)
-- Name: impiegato_stipendiato impiegato_stipendiato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_stipendiato
    ADD CONSTRAINT impiegato_stipendiato_pkey PRIMARY KEY (id);


--
-- TOC entry 4811 (class 2606 OID 17452)
-- Name: impiegato_turno impiegato_turno_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_turno
    ADD CONSTRAINT impiegato_turno_pkey PRIMARY KEY (turno_lavorativo_id, impiegato_id);


--
-- TOC entry 4803 (class 2606 OID 17143)
-- Name: manager manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (id);


--
-- TOC entry 4809 (class 2606 OID 17465)
-- Name: ora_lavorativa ora_lavorativa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT ora_lavorativa_pkey PRIMARY KEY (id);


--
-- TOC entry 4805 (class 2606 OID 17204)
-- Name: password_reset_token password_reset_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_reset_token
    ADD CONSTRAINT password_reset_token_pkey PRIMARY KEY (id);


--
-- TOC entry 4807 (class 2606 OID 17476)
-- Name: turno_lavorativo turno_lavorativo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.turno_lavorativo
    ADD CONSTRAINT turno_lavorativo_pkey PRIMARY KEY (id);


--
-- TOC entry 4793 (class 2606 OID 17005)
-- Name: utente utente_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_email_key UNIQUE (email);


--
-- TOC entry 4795 (class 2606 OID 17167)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id);


--
-- TOC entry 4818 (class 2606 OID 17458)
-- Name: impiegato_turno fk_impiegato; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_turno
    ADD CONSTRAINT fk_impiegato FOREIGN KEY (impiegato_id) REFERENCES public.impiegato(id) ON DELETE CASCADE;


--
-- TOC entry 4819 (class 2606 OID 17482)
-- Name: impiegato_turno fk_turno; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_turno
    ADD CONSTRAINT fk_turno FOREIGN KEY (turno_lavorativo_id) REFERENCES public.turno_lavorativo(id) ON DELETE CASCADE;


--
-- TOC entry 4817 (class 2606 OID 17477)
-- Name: ora_lavorativa fk_turno_lavorativo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT fk_turno_lavorativo FOREIGN KEY (turno_lavorativo_id) REFERENCES public.turno_lavorativo(id) ON DELETE CASCADE;


--
-- TOC entry 4816 (class 2606 OID 17197)
-- Name: password_reset_token fk_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_reset_token
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.utente(id) ON DELETE CASCADE;


--
-- TOC entry 4812 (class 2606 OID 17168)
-- Name: impiegato impiegato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_id_fkey FOREIGN KEY (id) REFERENCES public.utente(id);


--
-- TOC entry 4814 (class 2606 OID 17118)
-- Name: impiegato_pagato_ora impiegato_pagato_ora_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_pagato_ora
    ADD CONSTRAINT impiegato_pagato_ora_id_fkey FOREIGN KEY (id) REFERENCES public.impiegato(id);


--
-- TOC entry 4813 (class 2606 OID 17129)
-- Name: impiegato_stipendiato impiegato_stipendiato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_stipendiato
    ADD CONSTRAINT impiegato_stipendiato_id_fkey FOREIGN KEY (id) REFERENCES public.impiegato(id);


--
-- TOC entry 4815 (class 2606 OID 17173)
-- Name: manager manager_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_id_fkey FOREIGN KEY (id) REFERENCES public.utente(id);


-- Completed on 2025-05-08 19:00:47

--
-- PostgreSQL database dump complete
--

