--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-05-09 12:05:15

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
-- TOC entry 875 (class 1247 OID 17406)
-- Name: tipo_ora; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.tipo_ora AS ENUM (
    'NORMALE',
    'STRAORDINARIO'
);


ALTER TYPE public.tipo_ora OWNER TO postgres;

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
-- TOC entry 228 (class 1259 OID 17531)
-- Name: impiegato_lavora_ora; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.impiegato_lavora_ora (
    id_impiegato bigint NOT NULL,
    id_ora_lavorativa bigint NOT NULL,
    tipooralavorativa public.tipo_ora DEFAULT 'NORMALE'::public.tipo_ora NOT NULL,
    tipo_ora_lavorativa character varying(255) NOT NULL,
    idimpiegato bigint NOT NULL,
    idoralavorativa bigint NOT NULL,
    CONSTRAINT impiegato_lavora_ora_tipo_ora_lavorativa_check CHECK (((tipo_ora_lavorativa)::text = ANY ((ARRAY['NORMALE'::character varying, 'STRAORDINARIO'::character varying])::text[])))
);


ALTER TABLE public.impiegato_lavora_ora OWNER TO postgres;

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
-- TOC entry 222 (class 1259 OID 17036)
-- Name: manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manager (
    id bigint NOT NULL,
    stipendio bigint NOT NULL
);


ALTER TABLE public.manager OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17525)
-- Name: ora_lavorativa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ora_lavorativa (
    id bigint NOT NULL,
    data date NOT NULL,
    inizio time without time zone NOT NULL,
    fine time without time zone NOT NULL
);


ALTER TABLE public.ora_lavorativa OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17524)
-- Name: ora_lavorativa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ora_lavorativa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ora_lavorativa_id_seq OWNER TO postgres;

--
-- TOC entry 4973 (class 0 OID 0)
-- Dependencies: 226
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
-- TOC entry 4974 (class 0 OID 0)
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
-- TOC entry 4975 (class 0 OID 0)
-- Dependencies: 217
-- Name: utente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utente_id_seq OWNED BY public.utente.id;


--
-- TOC entry 4779 (class 2604 OID 17528)
-- Name: ora_lavorativa id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa ALTER COLUMN id SET DEFAULT nextval('public.ora_lavorativa_id_seq'::regclass);


--
-- TOC entry 4778 (class 2604 OID 17202)
-- Name: password_reset_token id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_reset_token ALTER COLUMN id SET DEFAULT nextval('public.password_reset_token_id_seq'::regclass);


--
-- TOC entry 4776 (class 2604 OID 17165)
-- Name: utente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente ALTER COLUMN id SET DEFAULT nextval('public.utente_id_seq'::regclass);


--
-- TOC entry 4958 (class 0 OID 17006)
-- Dependencies: 219
-- Data for Name: impiegato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato (id) FROM stdin;
7
9
\.


--
-- TOC entry 4967 (class 0 OID 17531)
-- Dependencies: 228
-- Data for Name: impiegato_lavora_ora; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato_lavora_ora (id_impiegato, id_ora_lavorativa, tipooralavorativa, tipo_ora_lavorativa, idimpiegato, idoralavorativa) FROM stdin;
\.


--
-- TOC entry 4960 (class 0 OID 17026)
-- Dependencies: 221
-- Data for Name: impiegato_pagato_ora; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato_pagato_ora (id, paga_oraria) FROM stdin;
7	25
\.


--
-- TOC entry 4959 (class 0 OID 17016)
-- Dependencies: 220
-- Data for Name: impiegato_stipendiato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.impiegato_stipendiato (id, stipendio_mensile) FROM stdin;
9	1500
\.


--
-- TOC entry 4961 (class 0 OID 17036)
-- Dependencies: 222
-- Data for Name: manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.manager (id, stipendio) FROM stdin;
1	5000
6	5000
10	5000
\.


--
-- TOC entry 4966 (class 0 OID 17525)
-- Dependencies: 227
-- Data for Name: ora_lavorativa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ora_lavorativa (id, data, inizio, fine) FROM stdin;
1	2025-05-01	08:00:00	09:00:00
2	2025-05-01	09:00:00	10:00:00
3	2025-05-01	10:00:00	11:00:00
4	2025-05-01	11:00:00	12:00:00
5	2025-05-01	12:00:00	13:00:00
6	2025-05-01	13:00:00	14:00:00
7	2025-05-01	14:00:00	15:00:00
8	2025-05-01	15:00:00	16:00:00
9	2025-05-01	16:00:00	17:00:00
10	2025-05-01	17:00:00	18:00:00
11	2025-05-01	18:00:00	19:00:00
12	2025-05-01	19:00:00	20:00:00
13	2025-05-01	20:00:00	21:00:00
14	2025-05-01	21:00:00	22:00:00
15	2025-05-01	22:00:00	23:00:00
16	2025-05-01	23:00:00	00:00:00
17	2025-05-02	08:00:00	09:00:00
18	2025-05-02	09:00:00	10:00:00
19	2025-05-02	10:00:00	11:00:00
20	2025-05-02	11:00:00	12:00:00
21	2025-05-02	12:00:00	13:00:00
22	2025-05-02	13:00:00	14:00:00
23	2025-05-02	14:00:00	15:00:00
24	2025-05-02	15:00:00	16:00:00
25	2025-05-02	16:00:00	17:00:00
26	2025-05-02	17:00:00	18:00:00
27	2025-05-02	18:00:00	19:00:00
28	2025-05-02	19:00:00	20:00:00
29	2025-05-02	20:00:00	21:00:00
30	2025-05-02	21:00:00	22:00:00
31	2025-05-02	22:00:00	23:00:00
32	2025-05-02	23:00:00	00:00:00
33	2025-05-03	08:00:00	09:00:00
34	2025-05-03	09:00:00	10:00:00
35	2025-05-03	10:00:00	11:00:00
36	2025-05-03	11:00:00	12:00:00
37	2025-05-03	12:00:00	13:00:00
38	2025-05-03	13:00:00	14:00:00
39	2025-05-03	14:00:00	15:00:00
40	2025-05-03	15:00:00	16:00:00
41	2025-05-03	16:00:00	17:00:00
42	2025-05-03	17:00:00	18:00:00
43	2025-05-03	18:00:00	19:00:00
44	2025-05-03	19:00:00	20:00:00
45	2025-05-03	20:00:00	21:00:00
46	2025-05-03	21:00:00	22:00:00
47	2025-05-03	22:00:00	23:00:00
48	2025-05-03	23:00:00	00:00:00
49	2025-05-04	08:00:00	09:00:00
50	2025-05-04	09:00:00	10:00:00
51	2025-05-04	10:00:00	11:00:00
52	2025-05-04	11:00:00	12:00:00
53	2025-05-04	12:00:00	13:00:00
54	2025-05-04	13:00:00	14:00:00
55	2025-05-04	14:00:00	15:00:00
56	2025-05-04	15:00:00	16:00:00
57	2025-05-04	16:00:00	17:00:00
58	2025-05-04	17:00:00	18:00:00
59	2025-05-04	18:00:00	19:00:00
60	2025-05-04	19:00:00	20:00:00
61	2025-05-04	20:00:00	21:00:00
62	2025-05-04	21:00:00	22:00:00
63	2025-05-04	22:00:00	23:00:00
64	2025-05-04	23:00:00	00:00:00
65	2025-05-05	08:00:00	09:00:00
66	2025-05-05	09:00:00	10:00:00
67	2025-05-05	10:00:00	11:00:00
68	2025-05-05	11:00:00	12:00:00
69	2025-05-05	12:00:00	13:00:00
70	2025-05-05	13:00:00	14:00:00
71	2025-05-05	14:00:00	15:00:00
72	2025-05-05	15:00:00	16:00:00
73	2025-05-05	16:00:00	17:00:00
74	2025-05-05	17:00:00	18:00:00
75	2025-05-05	18:00:00	19:00:00
76	2025-05-05	19:00:00	20:00:00
77	2025-05-05	20:00:00	21:00:00
78	2025-05-05	21:00:00	22:00:00
79	2025-05-05	22:00:00	23:00:00
80	2025-05-05	23:00:00	00:00:00
81	2025-05-06	08:00:00	09:00:00
82	2025-05-06	09:00:00	10:00:00
83	2025-05-06	10:00:00	11:00:00
84	2025-05-06	11:00:00	12:00:00
85	2025-05-06	12:00:00	13:00:00
86	2025-05-06	13:00:00	14:00:00
87	2025-05-06	14:00:00	15:00:00
88	2025-05-06	15:00:00	16:00:00
89	2025-05-06	16:00:00	17:00:00
90	2025-05-06	17:00:00	18:00:00
91	2025-05-06	18:00:00	19:00:00
92	2025-05-06	19:00:00	20:00:00
93	2025-05-06	20:00:00	21:00:00
94	2025-05-06	21:00:00	22:00:00
95	2025-05-06	22:00:00	23:00:00
96	2025-05-06	23:00:00	00:00:00
97	2025-05-07	08:00:00	09:00:00
98	2025-05-07	09:00:00	10:00:00
99	2025-05-07	10:00:00	11:00:00
100	2025-05-07	11:00:00	12:00:00
101	2025-05-07	12:00:00	13:00:00
102	2025-05-07	13:00:00	14:00:00
103	2025-05-07	14:00:00	15:00:00
104	2025-05-07	15:00:00	16:00:00
105	2025-05-07	16:00:00	17:00:00
106	2025-05-07	17:00:00	18:00:00
107	2025-05-07	18:00:00	19:00:00
108	2025-05-07	19:00:00	20:00:00
109	2025-05-07	20:00:00	21:00:00
110	2025-05-07	21:00:00	22:00:00
111	2025-05-07	22:00:00	23:00:00
112	2025-05-07	23:00:00	00:00:00
113	2025-05-08	08:00:00	09:00:00
114	2025-05-08	09:00:00	10:00:00
115	2025-05-08	10:00:00	11:00:00
116	2025-05-08	11:00:00	12:00:00
117	2025-05-08	12:00:00	13:00:00
118	2025-05-08	13:00:00	14:00:00
119	2025-05-08	14:00:00	15:00:00
120	2025-05-08	15:00:00	16:00:00
121	2025-05-08	16:00:00	17:00:00
122	2025-05-08	17:00:00	18:00:00
123	2025-05-08	18:00:00	19:00:00
124	2025-05-08	19:00:00	20:00:00
125	2025-05-08	20:00:00	21:00:00
126	2025-05-08	21:00:00	22:00:00
127	2025-05-08	22:00:00	23:00:00
128	2025-05-08	23:00:00	00:00:00
129	2025-05-09	08:00:00	09:00:00
130	2025-05-09	09:00:00	10:00:00
131	2025-05-09	10:00:00	11:00:00
132	2025-05-09	11:00:00	12:00:00
133	2025-05-09	12:00:00	13:00:00
134	2025-05-09	13:00:00	14:00:00
135	2025-05-09	14:00:00	15:00:00
136	2025-05-09	15:00:00	16:00:00
137	2025-05-09	16:00:00	17:00:00
138	2025-05-09	17:00:00	18:00:00
139	2025-05-09	18:00:00	19:00:00
140	2025-05-09	19:00:00	20:00:00
141	2025-05-09	20:00:00	21:00:00
142	2025-05-09	21:00:00	22:00:00
143	2025-05-09	22:00:00	23:00:00
144	2025-05-09	23:00:00	00:00:00
145	2025-05-10	08:00:00	09:00:00
146	2025-05-10	09:00:00	10:00:00
147	2025-05-10	10:00:00	11:00:00
148	2025-05-10	11:00:00	12:00:00
149	2025-05-10	12:00:00	13:00:00
150	2025-05-10	13:00:00	14:00:00
151	2025-05-10	14:00:00	15:00:00
152	2025-05-10	15:00:00	16:00:00
153	2025-05-10	16:00:00	17:00:00
154	2025-05-10	17:00:00	18:00:00
155	2025-05-10	18:00:00	19:00:00
156	2025-05-10	19:00:00	20:00:00
157	2025-05-10	20:00:00	21:00:00
158	2025-05-10	21:00:00	22:00:00
159	2025-05-10	22:00:00	23:00:00
160	2025-05-10	23:00:00	00:00:00
161	2025-05-11	08:00:00	09:00:00
162	2025-05-11	09:00:00	10:00:00
163	2025-05-11	10:00:00	11:00:00
164	2025-05-11	11:00:00	12:00:00
165	2025-05-11	12:00:00	13:00:00
166	2025-05-11	13:00:00	14:00:00
167	2025-05-11	14:00:00	15:00:00
168	2025-05-11	15:00:00	16:00:00
169	2025-05-11	16:00:00	17:00:00
170	2025-05-11	17:00:00	18:00:00
171	2025-05-11	18:00:00	19:00:00
172	2025-05-11	19:00:00	20:00:00
173	2025-05-11	20:00:00	21:00:00
174	2025-05-11	21:00:00	22:00:00
175	2025-05-11	22:00:00	23:00:00
176	2025-05-11	23:00:00	00:00:00
177	2025-05-12	08:00:00	09:00:00
178	2025-05-12	09:00:00	10:00:00
179	2025-05-12	10:00:00	11:00:00
180	2025-05-12	11:00:00	12:00:00
181	2025-05-12	12:00:00	13:00:00
182	2025-05-12	13:00:00	14:00:00
183	2025-05-12	14:00:00	15:00:00
184	2025-05-12	15:00:00	16:00:00
185	2025-05-12	16:00:00	17:00:00
186	2025-05-12	17:00:00	18:00:00
187	2025-05-12	18:00:00	19:00:00
188	2025-05-12	19:00:00	20:00:00
189	2025-05-12	20:00:00	21:00:00
190	2025-05-12	21:00:00	22:00:00
191	2025-05-12	22:00:00	23:00:00
192	2025-05-12	23:00:00	00:00:00
193	2025-05-13	08:00:00	09:00:00
194	2025-05-13	09:00:00	10:00:00
195	2025-05-13	10:00:00	11:00:00
196	2025-05-13	11:00:00	12:00:00
197	2025-05-13	12:00:00	13:00:00
198	2025-05-13	13:00:00	14:00:00
199	2025-05-13	14:00:00	15:00:00
200	2025-05-13	15:00:00	16:00:00
201	2025-05-13	16:00:00	17:00:00
202	2025-05-13	17:00:00	18:00:00
203	2025-05-13	18:00:00	19:00:00
204	2025-05-13	19:00:00	20:00:00
205	2025-05-13	20:00:00	21:00:00
206	2025-05-13	21:00:00	22:00:00
207	2025-05-13	22:00:00	23:00:00
208	2025-05-13	23:00:00	00:00:00
209	2025-05-14	08:00:00	09:00:00
210	2025-05-14	09:00:00	10:00:00
211	2025-05-14	10:00:00	11:00:00
212	2025-05-14	11:00:00	12:00:00
213	2025-05-14	12:00:00	13:00:00
214	2025-05-14	13:00:00	14:00:00
215	2025-05-14	14:00:00	15:00:00
216	2025-05-14	15:00:00	16:00:00
217	2025-05-14	16:00:00	17:00:00
218	2025-05-14	17:00:00	18:00:00
219	2025-05-14	18:00:00	19:00:00
220	2025-05-14	19:00:00	20:00:00
221	2025-05-14	20:00:00	21:00:00
222	2025-05-14	21:00:00	22:00:00
223	2025-05-14	22:00:00	23:00:00
224	2025-05-14	23:00:00	00:00:00
225	2025-05-15	08:00:00	09:00:00
226	2025-05-15	09:00:00	10:00:00
227	2025-05-15	10:00:00	11:00:00
228	2025-05-15	11:00:00	12:00:00
229	2025-05-15	12:00:00	13:00:00
230	2025-05-15	13:00:00	14:00:00
231	2025-05-15	14:00:00	15:00:00
232	2025-05-15	15:00:00	16:00:00
233	2025-05-15	16:00:00	17:00:00
234	2025-05-15	17:00:00	18:00:00
235	2025-05-15	18:00:00	19:00:00
236	2025-05-15	19:00:00	20:00:00
237	2025-05-15	20:00:00	21:00:00
238	2025-05-15	21:00:00	22:00:00
239	2025-05-15	22:00:00	23:00:00
240	2025-05-15	23:00:00	00:00:00
241	2025-05-16	08:00:00	09:00:00
242	2025-05-16	09:00:00	10:00:00
243	2025-05-16	10:00:00	11:00:00
244	2025-05-16	11:00:00	12:00:00
245	2025-05-16	12:00:00	13:00:00
246	2025-05-16	13:00:00	14:00:00
247	2025-05-16	14:00:00	15:00:00
248	2025-05-16	15:00:00	16:00:00
249	2025-05-16	16:00:00	17:00:00
250	2025-05-16	17:00:00	18:00:00
251	2025-05-16	18:00:00	19:00:00
252	2025-05-16	19:00:00	20:00:00
253	2025-05-16	20:00:00	21:00:00
254	2025-05-16	21:00:00	22:00:00
255	2025-05-16	22:00:00	23:00:00
256	2025-05-16	23:00:00	00:00:00
257	2025-05-17	08:00:00	09:00:00
258	2025-05-17	09:00:00	10:00:00
259	2025-05-17	10:00:00	11:00:00
260	2025-05-17	11:00:00	12:00:00
261	2025-05-17	12:00:00	13:00:00
262	2025-05-17	13:00:00	14:00:00
263	2025-05-17	14:00:00	15:00:00
264	2025-05-17	15:00:00	16:00:00
265	2025-05-17	16:00:00	17:00:00
266	2025-05-17	17:00:00	18:00:00
267	2025-05-17	18:00:00	19:00:00
268	2025-05-17	19:00:00	20:00:00
269	2025-05-17	20:00:00	21:00:00
270	2025-05-17	21:00:00	22:00:00
271	2025-05-17	22:00:00	23:00:00
272	2025-05-17	23:00:00	00:00:00
273	2025-05-18	08:00:00	09:00:00
274	2025-05-18	09:00:00	10:00:00
275	2025-05-18	10:00:00	11:00:00
276	2025-05-18	11:00:00	12:00:00
277	2025-05-18	12:00:00	13:00:00
278	2025-05-18	13:00:00	14:00:00
279	2025-05-18	14:00:00	15:00:00
280	2025-05-18	15:00:00	16:00:00
281	2025-05-18	16:00:00	17:00:00
282	2025-05-18	17:00:00	18:00:00
283	2025-05-18	18:00:00	19:00:00
284	2025-05-18	19:00:00	20:00:00
285	2025-05-18	20:00:00	21:00:00
286	2025-05-18	21:00:00	22:00:00
287	2025-05-18	22:00:00	23:00:00
288	2025-05-18	23:00:00	00:00:00
289	2025-05-19	08:00:00	09:00:00
290	2025-05-19	09:00:00	10:00:00
291	2025-05-19	10:00:00	11:00:00
292	2025-05-19	11:00:00	12:00:00
293	2025-05-19	12:00:00	13:00:00
294	2025-05-19	13:00:00	14:00:00
295	2025-05-19	14:00:00	15:00:00
296	2025-05-19	15:00:00	16:00:00
297	2025-05-19	16:00:00	17:00:00
298	2025-05-19	17:00:00	18:00:00
299	2025-05-19	18:00:00	19:00:00
300	2025-05-19	19:00:00	20:00:00
301	2025-05-19	20:00:00	21:00:00
302	2025-05-19	21:00:00	22:00:00
303	2025-05-19	22:00:00	23:00:00
304	2025-05-19	23:00:00	00:00:00
305	2025-05-20	08:00:00	09:00:00
306	2025-05-20	09:00:00	10:00:00
307	2025-05-20	10:00:00	11:00:00
308	2025-05-20	11:00:00	12:00:00
309	2025-05-20	12:00:00	13:00:00
310	2025-05-20	13:00:00	14:00:00
311	2025-05-20	14:00:00	15:00:00
312	2025-05-20	15:00:00	16:00:00
313	2025-05-20	16:00:00	17:00:00
314	2025-05-20	17:00:00	18:00:00
315	2025-05-20	18:00:00	19:00:00
316	2025-05-20	19:00:00	20:00:00
317	2025-05-20	20:00:00	21:00:00
318	2025-05-20	21:00:00	22:00:00
319	2025-05-20	22:00:00	23:00:00
320	2025-05-20	23:00:00	00:00:00
321	2025-05-21	08:00:00	09:00:00
322	2025-05-21	09:00:00	10:00:00
323	2025-05-21	10:00:00	11:00:00
324	2025-05-21	11:00:00	12:00:00
325	2025-05-21	12:00:00	13:00:00
326	2025-05-21	13:00:00	14:00:00
327	2025-05-21	14:00:00	15:00:00
328	2025-05-21	15:00:00	16:00:00
329	2025-05-21	16:00:00	17:00:00
330	2025-05-21	17:00:00	18:00:00
331	2025-05-21	18:00:00	19:00:00
332	2025-05-21	19:00:00	20:00:00
333	2025-05-21	20:00:00	21:00:00
334	2025-05-21	21:00:00	22:00:00
335	2025-05-21	22:00:00	23:00:00
336	2025-05-21	23:00:00	00:00:00
337	2025-05-22	08:00:00	09:00:00
338	2025-05-22	09:00:00	10:00:00
339	2025-05-22	10:00:00	11:00:00
340	2025-05-22	11:00:00	12:00:00
341	2025-05-22	12:00:00	13:00:00
342	2025-05-22	13:00:00	14:00:00
343	2025-05-22	14:00:00	15:00:00
344	2025-05-22	15:00:00	16:00:00
345	2025-05-22	16:00:00	17:00:00
346	2025-05-22	17:00:00	18:00:00
347	2025-05-22	18:00:00	19:00:00
348	2025-05-22	19:00:00	20:00:00
349	2025-05-22	20:00:00	21:00:00
350	2025-05-22	21:00:00	22:00:00
351	2025-05-22	22:00:00	23:00:00
352	2025-05-22	23:00:00	00:00:00
353	2025-05-23	08:00:00	09:00:00
354	2025-05-23	09:00:00	10:00:00
355	2025-05-23	10:00:00	11:00:00
356	2025-05-23	11:00:00	12:00:00
357	2025-05-23	12:00:00	13:00:00
358	2025-05-23	13:00:00	14:00:00
359	2025-05-23	14:00:00	15:00:00
360	2025-05-23	15:00:00	16:00:00
361	2025-05-23	16:00:00	17:00:00
362	2025-05-23	17:00:00	18:00:00
363	2025-05-23	18:00:00	19:00:00
364	2025-05-23	19:00:00	20:00:00
365	2025-05-23	20:00:00	21:00:00
366	2025-05-23	21:00:00	22:00:00
367	2025-05-23	22:00:00	23:00:00
368	2025-05-23	23:00:00	00:00:00
369	2025-05-24	08:00:00	09:00:00
370	2025-05-24	09:00:00	10:00:00
371	2025-05-24	10:00:00	11:00:00
372	2025-05-24	11:00:00	12:00:00
373	2025-05-24	12:00:00	13:00:00
374	2025-05-24	13:00:00	14:00:00
375	2025-05-24	14:00:00	15:00:00
376	2025-05-24	15:00:00	16:00:00
377	2025-05-24	16:00:00	17:00:00
378	2025-05-24	17:00:00	18:00:00
379	2025-05-24	18:00:00	19:00:00
380	2025-05-24	19:00:00	20:00:00
381	2025-05-24	20:00:00	21:00:00
382	2025-05-24	21:00:00	22:00:00
383	2025-05-24	22:00:00	23:00:00
384	2025-05-24	23:00:00	00:00:00
385	2025-05-25	08:00:00	09:00:00
386	2025-05-25	09:00:00	10:00:00
387	2025-05-25	10:00:00	11:00:00
388	2025-05-25	11:00:00	12:00:00
389	2025-05-25	12:00:00	13:00:00
390	2025-05-25	13:00:00	14:00:00
391	2025-05-25	14:00:00	15:00:00
392	2025-05-25	15:00:00	16:00:00
393	2025-05-25	16:00:00	17:00:00
394	2025-05-25	17:00:00	18:00:00
395	2025-05-25	18:00:00	19:00:00
396	2025-05-25	19:00:00	20:00:00
397	2025-05-25	20:00:00	21:00:00
398	2025-05-25	21:00:00	22:00:00
399	2025-05-25	22:00:00	23:00:00
400	2025-05-25	23:00:00	00:00:00
401	2025-05-26	08:00:00	09:00:00
402	2025-05-26	09:00:00	10:00:00
403	2025-05-26	10:00:00	11:00:00
404	2025-05-26	11:00:00	12:00:00
405	2025-05-26	12:00:00	13:00:00
406	2025-05-26	13:00:00	14:00:00
407	2025-05-26	14:00:00	15:00:00
408	2025-05-26	15:00:00	16:00:00
409	2025-05-26	16:00:00	17:00:00
410	2025-05-26	17:00:00	18:00:00
411	2025-05-26	18:00:00	19:00:00
412	2025-05-26	19:00:00	20:00:00
413	2025-05-26	20:00:00	21:00:00
414	2025-05-26	21:00:00	22:00:00
415	2025-05-26	22:00:00	23:00:00
416	2025-05-26	23:00:00	00:00:00
417	2025-05-27	08:00:00	09:00:00
418	2025-05-27	09:00:00	10:00:00
419	2025-05-27	10:00:00	11:00:00
420	2025-05-27	11:00:00	12:00:00
421	2025-05-27	12:00:00	13:00:00
422	2025-05-27	13:00:00	14:00:00
423	2025-05-27	14:00:00	15:00:00
424	2025-05-27	15:00:00	16:00:00
425	2025-05-27	16:00:00	17:00:00
426	2025-05-27	17:00:00	18:00:00
427	2025-05-27	18:00:00	19:00:00
428	2025-05-27	19:00:00	20:00:00
429	2025-05-27	20:00:00	21:00:00
430	2025-05-27	21:00:00	22:00:00
431	2025-05-27	22:00:00	23:00:00
432	2025-05-27	23:00:00	00:00:00
433	2025-05-28	08:00:00	09:00:00
434	2025-05-28	09:00:00	10:00:00
435	2025-05-28	10:00:00	11:00:00
436	2025-05-28	11:00:00	12:00:00
437	2025-05-28	12:00:00	13:00:00
438	2025-05-28	13:00:00	14:00:00
439	2025-05-28	14:00:00	15:00:00
440	2025-05-28	15:00:00	16:00:00
441	2025-05-28	16:00:00	17:00:00
442	2025-05-28	17:00:00	18:00:00
443	2025-05-28	18:00:00	19:00:00
444	2025-05-28	19:00:00	20:00:00
445	2025-05-28	20:00:00	21:00:00
446	2025-05-28	21:00:00	22:00:00
447	2025-05-28	22:00:00	23:00:00
448	2025-05-28	23:00:00	00:00:00
449	2025-05-29	08:00:00	09:00:00
450	2025-05-29	09:00:00	10:00:00
451	2025-05-29	10:00:00	11:00:00
452	2025-05-29	11:00:00	12:00:00
453	2025-05-29	12:00:00	13:00:00
454	2025-05-29	13:00:00	14:00:00
455	2025-05-29	14:00:00	15:00:00
456	2025-05-29	15:00:00	16:00:00
457	2025-05-29	16:00:00	17:00:00
458	2025-05-29	17:00:00	18:00:00
459	2025-05-29	18:00:00	19:00:00
460	2025-05-29	19:00:00	20:00:00
461	2025-05-29	20:00:00	21:00:00
462	2025-05-29	21:00:00	22:00:00
463	2025-05-29	22:00:00	23:00:00
464	2025-05-29	23:00:00	00:00:00
465	2025-05-30	08:00:00	09:00:00
466	2025-05-30	09:00:00	10:00:00
467	2025-05-30	10:00:00	11:00:00
468	2025-05-30	11:00:00	12:00:00
469	2025-05-30	12:00:00	13:00:00
470	2025-05-30	13:00:00	14:00:00
471	2025-05-30	14:00:00	15:00:00
472	2025-05-30	15:00:00	16:00:00
473	2025-05-30	16:00:00	17:00:00
474	2025-05-30	17:00:00	18:00:00
475	2025-05-30	18:00:00	19:00:00
476	2025-05-30	19:00:00	20:00:00
477	2025-05-30	20:00:00	21:00:00
478	2025-05-30	21:00:00	22:00:00
479	2025-05-30	22:00:00	23:00:00
480	2025-05-30	23:00:00	00:00:00
481	2025-05-31	08:00:00	09:00:00
482	2025-05-31	09:00:00	10:00:00
483	2025-05-31	10:00:00	11:00:00
484	2025-05-31	11:00:00	12:00:00
485	2025-05-31	12:00:00	13:00:00
486	2025-05-31	13:00:00	14:00:00
487	2025-05-31	14:00:00	15:00:00
488	2025-05-31	15:00:00	16:00:00
489	2025-05-31	16:00:00	17:00:00
490	2025-05-31	17:00:00	18:00:00
491	2025-05-31	18:00:00	19:00:00
492	2025-05-31	19:00:00	20:00:00
493	2025-05-31	20:00:00	21:00:00
494	2025-05-31	21:00:00	22:00:00
495	2025-05-31	22:00:00	23:00:00
496	2025-05-31	23:00:00	00:00:00
\.


--
-- TOC entry 4963 (class 0 OID 17191)
-- Dependencies: 224
-- Data for Name: password_reset_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.password_reset_token (id, token, user_id, expiry_date) FROM stdin;
3	f23bac48-c791-4405-b120-700bc23aa4c8	10	2025-05-07 16:54:36.489829
\.


--
-- TOC entry 4957 (class 0 OID 16996)
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
-- TOC entry 4976 (class 0 OID 0)
-- Dependencies: 226
-- Name: ora_lavorativa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ora_lavorativa_id_seq', 497, true);


--
-- TOC entry 4977 (class 0 OID 0)
-- Dependencies: 223
-- Name: password_reset_token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.password_reset_token_id_seq', 1, false);


--
-- TOC entry 4978 (class 0 OID 0)
-- Dependencies: 225
-- Name: password_reset_token_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.password_reset_token_seq', 51, true);


--
-- TOC entry 4979 (class 0 OID 0)
-- Dependencies: 217
-- Name: utente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_id_seq', 10, true);


--
-- TOC entry 4803 (class 2606 OID 17536)
-- Name: impiegato_lavora_ora impiegato_lavora_ora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_lavora_ora
    ADD CONSTRAINT impiegato_lavora_ora_pkey PRIMARY KEY (id_impiegato, id_ora_lavorativa);


--
-- TOC entry 4791 (class 2606 OID 17117)
-- Name: impiegato_pagato_ora impiegato_pagato_ora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_pagato_ora
    ADD CONSTRAINT impiegato_pagato_ora_pkey PRIMARY KEY (id);


--
-- TOC entry 4787 (class 2606 OID 17069)
-- Name: impiegato impiegato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_pkey PRIMARY KEY (id);


--
-- TOC entry 4789 (class 2606 OID 17128)
-- Name: impiegato_stipendiato impiegato_stipendiato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_stipendiato
    ADD CONSTRAINT impiegato_stipendiato_pkey PRIMARY KEY (id);


--
-- TOC entry 4793 (class 2606 OID 17143)
-- Name: manager manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (id);


--
-- TOC entry 4797 (class 2606 OID 17530)
-- Name: ora_lavorativa ora_lavorativa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT ora_lavorativa_pkey PRIMARY KEY (id);


--
-- TOC entry 4795 (class 2606 OID 17204)
-- Name: password_reset_token password_reset_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_reset_token
    ADD CONSTRAINT password_reset_token_pkey PRIMARY KEY (id);


--
-- TOC entry 4799 (class 2606 OID 17551)
-- Name: ora_lavorativa ukiy1brfe8agck642buu5vaepgi; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT ukiy1brfe8agck642buu5vaepgi UNIQUE (data, inizio, fine);


--
-- TOC entry 4801 (class 2606 OID 17548)
-- Name: ora_lavorativa unique_data_inizio_fine; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT unique_data_inizio_fine UNIQUE (data, inizio, fine);


--
-- TOC entry 4783 (class 2606 OID 17005)
-- Name: utente utente_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_email_key UNIQUE (email);


--
-- TOC entry 4785 (class 2606 OID 17167)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id);


--
-- TOC entry 4808 (class 2606 OID 17197)
-- Name: password_reset_token fk_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.password_reset_token
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.utente(id) ON DELETE CASCADE;


--
-- TOC entry 4804 (class 2606 OID 17168)
-- Name: impiegato impiegato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_id_fkey FOREIGN KEY (id) REFERENCES public.utente(id);


--
-- TOC entry 4809 (class 2606 OID 17537)
-- Name: impiegato_lavora_ora impiegato_lavora_ora_id_impiegato_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_lavora_ora
    ADD CONSTRAINT impiegato_lavora_ora_id_impiegato_fkey FOREIGN KEY (id_impiegato) REFERENCES public.impiegato(id);


--
-- TOC entry 4810 (class 2606 OID 17542)
-- Name: impiegato_lavora_ora impiegato_lavora_ora_id_ora_lavorativa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_lavora_ora
    ADD CONSTRAINT impiegato_lavora_ora_id_ora_lavorativa_fkey FOREIGN KEY (id_ora_lavorativa) REFERENCES public.ora_lavorativa(id);


--
-- TOC entry 4806 (class 2606 OID 17118)
-- Name: impiegato_pagato_ora impiegato_pagato_ora_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_pagato_ora
    ADD CONSTRAINT impiegato_pagato_ora_id_fkey FOREIGN KEY (id) REFERENCES public.impiegato(id);


--
-- TOC entry 4805 (class 2606 OID 17129)
-- Name: impiegato_stipendiato impiegato_stipendiato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.impiegato_stipendiato
    ADD CONSTRAINT impiegato_stipendiato_id_fkey FOREIGN KEY (id) REFERENCES public.impiegato(id);


--
-- TOC entry 4807 (class 2606 OID 17173)
-- Name: manager manager_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_id_fkey FOREIGN KEY (id) REFERENCES public.utente(id);


-- Completed on 2025-05-09 12:05:16

--
-- PostgreSQL database dump complete
--

