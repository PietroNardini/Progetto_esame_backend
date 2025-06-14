PGDMP  #                     }           HRFlow    17.4    17.4 :    v           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            w           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            x           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            y           1262    18568    HRFlow    DATABASE     k   CREATE DATABASE "HRFlow" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en';
    DROP DATABASE "HRFlow";
                     postgres    false            [           1247    18570    tipo_ora    TYPE     L   CREATE TYPE public.tipo_ora AS ENUM (
    'NORMALE',
    'STRAORDINARIO'
);
    DROP TYPE public.tipo_ora;
       public               postgres    false            �            1259    18575 	   impiegato    TABLE     :   CREATE TABLE public.impiegato (
    id bigint NOT NULL
);
    DROP TABLE public.impiegato;
       public         heap r       postgres    false            �            1259    18578    impiegato_lavora_ora    TABLE     �   CREATE TABLE public.impiegato_lavora_ora (
    id_impiegato bigint NOT NULL,
    id_ora_lavorativa bigint NOT NULL,
    tipooralavorativa character varying(255) DEFAULT 'NORMALE'::public.tipo_ora NOT NULL
);
 (   DROP TABLE public.impiegato_lavora_ora;
       public         heap r       postgres    false    859            �            1259    18582    impiegato_lavora_ora_storico    TABLE     ~  CREATE TABLE public.impiegato_lavora_ora_storico (
    id_impiegato bigint NOT NULL,
    id_ora_lavorativa bigint NOT NULL,
    tipooralavorativa character varying(255) NOT NULL,
    CONSTRAINT impiegato_lavora_ora_storico_tipooralavorativa_check CHECK (((tipooralavorativa)::text = ANY (ARRAY[('NORMALE'::character varying)::text, ('STRAORDINARIO'::character varying)::text])))
);
 0   DROP TABLE public.impiegato_lavora_ora_storico;
       public         heap r       postgres    false            �            1259    18586    impiegato_pagato_ora    TABLE     p   CREATE TABLE public.impiegato_pagato_ora (
    id bigint NOT NULL,
    paga_oraria double precision NOT NULL
);
 (   DROP TABLE public.impiegato_pagato_ora;
       public         heap r       postgres    false            �            1259    18589    impiegato_stipendiato    TABLE     w   CREATE TABLE public.impiegato_stipendiato (
    id bigint NOT NULL,
    stipendio_mensile double precision NOT NULL
);
 )   DROP TABLE public.impiegato_stipendiato;
       public         heap r       postgres    false            �            1259    18592    manager    TABLE     W   CREATE TABLE public.manager (
    id bigint NOT NULL,
    stipendio bigint NOT NULL
);
    DROP TABLE public.manager;
       public         heap r       postgres    false            �            1259    18595    ora_lavorativa    TABLE     �   CREATE TABLE public.ora_lavorativa (
    id bigint NOT NULL,
    data date NOT NULL,
    inizio time without time zone NOT NULL,
    fine time without time zone NOT NULL
);
 "   DROP TABLE public.ora_lavorativa;
       public         heap r       postgres    false            �            1259    18598    ora_lavorativa_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.ora_lavorativa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.ora_lavorativa_id_seq;
       public               postgres    false    223            z           0    0    ora_lavorativa_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.ora_lavorativa_id_seq OWNED BY public.ora_lavorativa.id;
          public               postgres    false    224            �            1259    18599    ora_lavorativa_storico    TABLE     �   CREATE TABLE public.ora_lavorativa_storico (
    id bigint NOT NULL,
    data date NOT NULL,
    inizio time without time zone NOT NULL,
    fine time without time zone NOT NULL
);
 *   DROP TABLE public.ora_lavorativa_storico;
       public         heap r       postgres    false            �            1259    18602    password_reset_token    TABLE     �   CREATE TABLE public.password_reset_token (
    id bigint NOT NULL,
    token character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    expiry_date timestamp without time zone NOT NULL
);
 (   DROP TABLE public.password_reset_token;
       public         heap r       postgres    false            �            1259    18605    password_reset_token_id_seq    SEQUENCE     �   CREATE SEQUENCE public.password_reset_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.password_reset_token_id_seq;
       public               postgres    false    226            {           0    0    password_reset_token_id_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.password_reset_token_id_seq OWNED BY public.password_reset_token.id;
          public               postgres    false    227            �            1259    18606    password_reset_token_seq    SEQUENCE     �   CREATE SEQUENCE public.password_reset_token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.password_reset_token_seq;
       public               postgres    false            �            1259    18607    utente    TABLE     �  CREATE TABLE public.utente (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    cognome character varying(255) NOT NULL,
    telefono character varying(255) NOT NULL,
    dipartimento character varying(255) NOT NULL,
    data_di_nascita date DEFAULT '2000-01-01'::date NOT NULL
);
    DROP TABLE public.utente;
       public         heap r       postgres    false            �            1259    18613    utente_id_seq    SEQUENCE     �   CREATE SEQUENCE public.utente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.utente_id_seq;
       public               postgres    false    229            |           0    0    utente_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.utente_id_seq OWNED BY public.utente.id;
          public               postgres    false    230            �           2604    18614    ora_lavorativa id    DEFAULT     v   ALTER TABLE ONLY public.ora_lavorativa ALTER COLUMN id SET DEFAULT nextval('public.ora_lavorativa_id_seq'::regclass);
 @   ALTER TABLE public.ora_lavorativa ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    224    223            �           2604    18615    password_reset_token id    DEFAULT     �   ALTER TABLE ONLY public.password_reset_token ALTER COLUMN id SET DEFAULT nextval('public.password_reset_token_id_seq'::regclass);
 F   ALTER TABLE public.password_reset_token ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    227    226            �           2604    18616 	   utente id    DEFAULT     f   ALTER TABLE ONLY public.utente ALTER COLUMN id SET DEFAULT nextval('public.utente_id_seq'::regclass);
 8   ALTER TABLE public.utente ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    230    229            f          0    18575 	   impiegato 
   TABLE DATA           '   COPY public.impiegato (id) FROM stdin;
    public               postgres    false    217   �G       g          0    18578    impiegato_lavora_ora 
   TABLE DATA           b   COPY public.impiegato_lavora_ora (id_impiegato, id_ora_lavorativa, tipooralavorativa) FROM stdin;
    public               postgres    false    218   H       h          0    18582    impiegato_lavora_ora_storico 
   TABLE DATA           j   COPY public.impiegato_lavora_ora_storico (id_impiegato, id_ora_lavorativa, tipooralavorativa) FROM stdin;
    public               postgres    false    219   ;H       i          0    18586    impiegato_pagato_ora 
   TABLE DATA           ?   COPY public.impiegato_pagato_ora (id, paga_oraria) FROM stdin;
    public               postgres    false    220   �H       j          0    18589    impiegato_stipendiato 
   TABLE DATA           F   COPY public.impiegato_stipendiato (id, stipendio_mensile) FROM stdin;
    public               postgres    false    221   �H       k          0    18592    manager 
   TABLE DATA           0   COPY public.manager (id, stipendio) FROM stdin;
    public               postgres    false    222   �H       l          0    18595    ora_lavorativa 
   TABLE DATA           @   COPY public.ora_lavorativa (id, data, inizio, fine) FROM stdin;
    public               postgres    false    223   &I       n          0    18599    ora_lavorativa_storico 
   TABLE DATA           H   COPY public.ora_lavorativa_storico (id, data, inizio, fine) FROM stdin;
    public               postgres    false    225   �R       o          0    18602    password_reset_token 
   TABLE DATA           O   COPY public.password_reset_token (id, token, user_id, expiry_date) FROM stdin;
    public               postgres    false    226   $\       r          0    18607    utente 
   TABLE DATA           m   COPY public.utente (id, email, password, nome, cognome, telefono, dipartimento, data_di_nascita) FROM stdin;
    public               postgres    false    229   ~\       }           0    0    ora_lavorativa_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.ora_lavorativa_id_seq', 993, true);
          public               postgres    false    224            ~           0    0    password_reset_token_id_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.password_reset_token_id_seq', 1, false);
          public               postgres    false    227                       0    0    password_reset_token_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.password_reset_token_seq', 101, true);
          public               postgres    false    228            �           0    0    utente_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.utente_id_seq', 13, true);
          public               postgres    false    230            �           2606    18618 .   impiegato_lavora_ora impiegato_lavora_ora_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.impiegato_lavora_ora
    ADD CONSTRAINT impiegato_lavora_ora_pkey PRIMARY KEY (id_impiegato, id_ora_lavorativa);
 X   ALTER TABLE ONLY public.impiegato_lavora_ora DROP CONSTRAINT impiegato_lavora_ora_pkey;
       public                 postgres    false    218    218            �           2606    18620 .   impiegato_pagato_ora impiegato_pagato_ora_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.impiegato_pagato_ora
    ADD CONSTRAINT impiegato_pagato_ora_pkey PRIMARY KEY (id);
 X   ALTER TABLE ONLY public.impiegato_pagato_ora DROP CONSTRAINT impiegato_pagato_ora_pkey;
       public                 postgres    false    220            �           2606    18622    impiegato impiegato_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.impiegato DROP CONSTRAINT impiegato_pkey;
       public                 postgres    false    217            �           2606    18624 0   impiegato_stipendiato impiegato_stipendiato_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.impiegato_stipendiato
    ADD CONSTRAINT impiegato_stipendiato_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.impiegato_stipendiato DROP CONSTRAINT impiegato_stipendiato_pkey;
       public                 postgres    false    221            �           2606    18626    manager manager_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.manager DROP CONSTRAINT manager_pkey;
       public                 postgres    false    222            �           2606    18628 "   ora_lavorativa ora_lavorativa_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT ora_lavorativa_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.ora_lavorativa DROP CONSTRAINT ora_lavorativa_pkey;
       public                 postgres    false    223            �           2606    18630 2   ora_lavorativa_storico ora_lavorativa_storico_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.ora_lavorativa_storico
    ADD CONSTRAINT ora_lavorativa_storico_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.ora_lavorativa_storico DROP CONSTRAINT ora_lavorativa_storico_pkey;
       public                 postgres    false    225            �           2606    18632 .   password_reset_token password_reset_token_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.password_reset_token
    ADD CONSTRAINT password_reset_token_pkey PRIMARY KEY (id);
 X   ALTER TABLE ONLY public.password_reset_token DROP CONSTRAINT password_reset_token_pkey;
       public                 postgres    false    226            �           2606    18634 *   ora_lavorativa ukiy1brfe8agck642buu5vaepgi 
   CONSTRAINT     s   ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT ukiy1brfe8agck642buu5vaepgi UNIQUE (data, inizio, fine);
 T   ALTER TABLE ONLY public.ora_lavorativa DROP CONSTRAINT ukiy1brfe8agck642buu5vaepgi;
       public                 postgres    false    223    223    223            �           2606    18636 &   ora_lavorativa unique_data_inizio_fine 
   CONSTRAINT     o   ALTER TABLE ONLY public.ora_lavorativa
    ADD CONSTRAINT unique_data_inizio_fine UNIQUE (data, inizio, fine);
 P   ALTER TABLE ONLY public.ora_lavorativa DROP CONSTRAINT unique_data_inizio_fine;
       public                 postgres    false    223    223    223            �           2606    18638    utente utente_email_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_email_key UNIQUE (email);
 A   ALTER TABLE ONLY public.utente DROP CONSTRAINT utente_email_key;
       public                 postgres    false    229            �           2606    18640    utente utente_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.utente DROP CONSTRAINT utente_pkey;
       public                 postgres    false    229            �           2606    18641    password_reset_token fk_user    FK CONSTRAINT     �   ALTER TABLE ONLY public.password_reset_token
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.utente(id) ON DELETE CASCADE;
 F   ALTER TABLE ONLY public.password_reset_token DROP CONSTRAINT fk_user;
       public               postgres    false    226    4813    229            �           2606    18646    impiegato impiegato_id_fkey    FK CONSTRAINT     v   ALTER TABLE ONLY public.impiegato
    ADD CONSTRAINT impiegato_id_fkey FOREIGN KEY (id) REFERENCES public.utente(id);
 E   ALTER TABLE ONLY public.impiegato DROP CONSTRAINT impiegato_id_fkey;
       public               postgres    false    217    4813    229            �           2606    18651 ;   impiegato_lavora_ora impiegato_lavora_ora_id_impiegato_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.impiegato_lavora_ora
    ADD CONSTRAINT impiegato_lavora_ora_id_impiegato_fkey FOREIGN KEY (id_impiegato) REFERENCES public.impiegato(id);
 e   ALTER TABLE ONLY public.impiegato_lavora_ora DROP CONSTRAINT impiegato_lavora_ora_id_impiegato_fkey;
       public               postgres    false    217    4791    218            �           2606    18656 @   impiegato_lavora_ora impiegato_lavora_ora_id_ora_lavorativa_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.impiegato_lavora_ora
    ADD CONSTRAINT impiegato_lavora_ora_id_ora_lavorativa_fkey FOREIGN KEY (id_ora_lavorativa) REFERENCES public.ora_lavorativa(id);
 j   ALTER TABLE ONLY public.impiegato_lavora_ora DROP CONSTRAINT impiegato_lavora_ora_id_ora_lavorativa_fkey;
       public               postgres    false    218    4801    223            �           2606    18661 1   impiegato_pagato_ora impiegato_pagato_ora_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.impiegato_pagato_ora
    ADD CONSTRAINT impiegato_pagato_ora_id_fkey FOREIGN KEY (id) REFERENCES public.impiegato(id);
 [   ALTER TABLE ONLY public.impiegato_pagato_ora DROP CONSTRAINT impiegato_pagato_ora_id_fkey;
       public               postgres    false    4791    217    220            �           2606    18666 3   impiegato_stipendiato impiegato_stipendiato_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.impiegato_stipendiato
    ADD CONSTRAINT impiegato_stipendiato_id_fkey FOREIGN KEY (id) REFERENCES public.impiegato(id);
 ]   ALTER TABLE ONLY public.impiegato_stipendiato DROP CONSTRAINT impiegato_stipendiato_id_fkey;
       public               postgres    false    4791    221    217            �           2606    18671    manager manager_id_fkey    FK CONSTRAINT     r   ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_id_fkey FOREIGN KEY (id) REFERENCES public.utente(id);
 A   ALTER TABLE ONLY public.manager DROP CONSTRAINT manager_id_fkey;
       public               postgres    false    4813    229    222            f      x�3��24����� ��      g      x������ � �      h   c   x�U�;� D���>�JI��B�Zz�s���{��L$��J�3;/���"���s�%�A$���]���[.Is���jg��_�a�՜1��31��6O      i      x�3�42����� �Z      j      x���4450�24��1z\\\ !]�      k      x�3�4500�2�P�P�JA�=... ��n      l   j	  x�m�A�lI���2͓�EH�����1ә�N�2�"q��w�'���y~?����������������?�?��s������'�{v������`}�����=x���������98߃c�sP߃�����������|l������ l���O~�[�����%Ö����wɰ%?�?Ͽ�A�L�aǶ���8z <D���C��<����C>vp�a7�vp��nҖҖҖҖҖҖ�,���vp�R������a�vp��<�c7����a��<�����ז^[xxmI��%��ז^[xxeɗxx�n����;�y؏�<찃���vp��<��n�����mKۖ�-	<l[x8�$�plI��Ȓ�x8�nΫ���vp�p��<������vp�p�n걃��
;�y([x([x([x([x([x([x(Y�5vp�Џ vp��i7����_;�y�m7}����nږږƖƖƖƖF�,�a��<���a�n�q�xP�)����p�q
x8�8<u��cK�<�ǖ�y8�-y�p[���<����ylɛ������Q�i���4�p�qx8�8<u�&�q�xP�i�A��u�&ҖҖҖҖҖҖ�q�xP��Ag�u�!�q�xP��Ag�u�!�q�xP��aْ�ò%��eK˖^[xxmI�A��_� �� �� �� �� �� �� �� �� ��mI�aے�ö%��mKۖ�-	<����#��'�8NP�<�8A}����#��'�8NP�<�8A}����#��'ϱ%��cKeKeKeKeK�8A}����#��'�8NP�<�8A}����#��'�8NP�<�8A}����Ӷ$�ж$�ж$�ж$�ж$�ж$� ��'�8NP�<�8A}����#��'�8NP�<�8A}����#��'�8NP��ǖ�y�ǖ�y�ǖ�y�ǖ�y�ǖ�y�ǖ�y(q��>Y�8A}��q��d�����	�%��'K'�O�8NP�,q��>Y�8A}�ҖҖ�q��d�����	�%��'K'�O�8NP�,q��>Y�8A}��q��d����Z�$�lI�aْ�ò%��eK˖�q��d�����	�%��'K��d��P�,uꓥ�C}��q�O�:��zmI��%��mKۖ�-	<l[xPǡ>Y�8�'K��d��P�,uꓥ�C}��q�O�:��Rǡ>Y�8�'�ؒ�ñ%��cKǖ�-	<[xPǡ>Y�8�'K��d��P�,uꓥ�C}��q�O�:��Rǡ>Y�8�'�mI�mI�mI�mI�mI�mI�A��d��P�,uꓥ�C}��q�O�:��Rǡ>Y�8�'K��d��P���%���%���%���%o��%o��%oZ'�O�8NR�lq��>��8I}��q��d��$���I�-���'['�O�8NR��%o:lɛ�[��Ö�q��d��$���I�-���'['�O�8NR�lq��>��8I}��q��d��$��N[xH[xX�$�lI�aْ�ò%�q��>��8I}��q��d��$���I�-���'['�O�8NR�lq��>��8I}�_[xxmI��%��ז^[xxmI�A'�O�8NR�lq��>��8I}��q��d��$���I�-���'['�O�8NR��cKǖ�-	<[x8�$�plI�A'�O�8NR�lq��>��8I}��q��d��$���I�-���'['�O�8NR��%���%���%���%���%���%�q��>��8I}��q��d��$���I�-���'['�O�8NR�lq��>��8I}�ǖƖƖƖƖƖ�q��d��$���I�#���'G����P�uꓣ�C}r�q�O�:��ylɛ�ylɛ�	[��a�y��%o&lɛ�Qǡ>9�8�'G����P�uꓣ�C}r�q�O�:��Qǡ>9�8�''mI�!mI�!mI�!mI�!mI�!mI�A����P�uꓣ�C}r�q�O�:��Qǡ>9�8�'G����P��ז^[xxmI��%��ז^[xPǡ>9�8�'G����P�uꓣ�C}r�q�O�:��Qǡ>9�8�'gے�ö%��mKۖ�-	<[x�Y�'GgQ�q�E}r�q���Y�'GgQ�q�E}r�q���Y�'GgQ���%���%���%���%���%���%�q�E}r�q���Y�'GgQ�q�E}r�q���Y�'GgQ�q�E}r�q��i[xh[x[x[x[x[��������?ilr      n   t	  x�m�]�%����Ŧ+���k������T�ĩ���H|�~t�������9���߿~����Gq������s�������<q����g}��=_q��<��;�������;_��7��w��u�����w���՗[Ep��?������*��>��]Ev��?���������o�	z�'��z�����U�*�{)�|u�W�����u�}��+�E~�E~�E~����v����;�������O������w�}�}��w�W���}��o�W�s�}�?���(����v����'���O����o�?���,����#������?��G�}�N�W�����y�}��q_��O�W�g���"��������o�E~��+���_��	�_W�W��������}��q_��G���+��[q_�����=q_�ߑ�����v�w���#��������o��X~��(������&�������������{��޸���#��7����������v�o��#������_����'�����#��PG���P|aM���/��1�5�:��P�_�pD��G	 pD�@I
T$	,P�$����C4�8�8�<�����D��Iڃ3�C{p(yhN%����=(�$.�$a�$a�$a�$a�$aN'/����=8����������Q^ڃ3�K{pHyiN)/��1�=t$	{�H�0�$��H�0�$�a"I؃���?�`�+u��W�=��A{0b���`�R����ڃAK���:h�-u��H�pF���3��=��$��H��Q$	{0z)��e�R������L�;,#�"{X�0E���a�b�9�2�)���1��$aw$	{�#I��I��H�pG�$-I���3�:�x��'�M�P,#�"�X�4EJ��i��b�I�2�)��eXS��$aO$	{x"I��I��H��F����"�X�7Ez��o��b�	�2�)2�e�S����1��H2���2��H3�$�3�$��#��{�I�=�$�tD�{2�)��2�)ҍ2�)�2�)�2�)2�2�)R�2�)r�2�)��2�)��2�)Ҏ�H��H��H��H��H��H�`�Sde�S�e�S�e�S$ e�Sd e�S� e�S� e�S$!e�Sd!e�S�!Ց$�#I�CG����$aI�&��=��H��H��H�	I9㐑�3)I9㐓�3II9㐕�3iI��$��$ag$	{8#I��I��H���CvR�8�'�C~R�8�'�C~R�8�'�C~R�8�'�C~R�8�'uE���+��=\�$��$aw$	{�#I؃3�I9㐟�3�I9㐟�3�I9㐟�3�I9㐟�3�I9㐟�Iһ�H��D���'��=<�$��$a�8�'�C~R�8�'�C~R�8�'�C~R�8�'�C~R�8�'�C~Ro$���G$���G$I/Y"Ix�rD���$�1�1��O�1��O�1��O�1��O�1��O�1��O�1��O�1��O�1��O�1��OvE����$aI�*���M�$�m�$a�8"?��8�w��8"?��8"?��8"?��8"?��8"?��8"?��8"?��8"?�I�:��=t$	{�H�Б$�#Iz�fI��lc��lc��lc��lc��lc��lc��lc��lc��lc���$aI�&��=��$��$ag$	{0��~4�?<��c�Hc�Hc�Hc��lc��lc��lc���+��=\�$��$aW$	{�"I��I��qD~��qD~��qD~��qD~��qD~��qD~��qD~��qD~��qD~��qD~��H��D���'��=<�$��$aO$	{0���6���6���6���6���6���6���6���6���6���~#I��I��H��F���7����G$	���qD~r�qD~r�qD~r�qD~r�q�O�3��q�!?9�8�'����8㐟��$��|E��p�"Ix9_�$<��H�P�$�����8㐟g��C~r�q�O�3��q�!?9�8�'����8㐟E��E��E����$aI�:��=8㐟g��C~r�q�O�3��q�!?9�8�'����8㐟g�3�$��&��=L$	{�H�0�$�a"I؃3��q�!?9�8�'����8㐟g��C~r�q�O�3��q�!?9g$	{�"I��I�/�"I��I��H�`���'���O�1N��c�&?9�8M~r�q����4��1�i�c���'���O�I��H�pG���;����E��ۻH�`���'���O�1N�/��q����4��1�i�c���'���O�1N��c�&?9o$	{x#I��I��H��F���7���Q���������?����T�      o   J   x�ɻ� �O���?�a���GH�=+��=��9��;_Q� n���EP��O�G�j��W�:iW"� {��      r     x���M��0���;��$��QED���D�b�� ��~��uf�*����z��'�R��׈f	n��
�e�3����ࠜ0�c�JmY騚��'H � /��ܒ�[��'�)�&�$�M�]�:tw�M���l�����Z]m��Ί�a_������Ai��Ɇ��_٪@hFp�
�m�+�R� ֖fa�rv0Jo)��cy�֝����Sy�f�o�2DB�����?�����4��`���~��gSU+y��
�Vg�>V�v �7�dÉ\v]b����)2��\�,E����B~�)"�=�;U�?�l� �k߮�nk�Į�}����S�I�x,�6�AN�/�k��t(�M�R�/~J�>Ͳ��7�푽�d.��A�%���j/�L�3��q��Wֳ�Br�^�#�x��"T�\�M����[�y���3��iUH+_��4rŚ���K��K�W~���ǥ����Z��k��q�-�0,5��gq�5�r�F��V�g��j5�=4�     