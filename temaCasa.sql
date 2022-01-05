CREATE TABLE jucatori(
    id_jucator varchar2(6),
    nume VARCHAR2(20) CONSTRAINT nume_nn NOT NULL,
    prenume VARCHAR2(20) CONSTRAINT prenume_nn NOT NULL,
    tara VARCHAR2(30) CONSTRAINT tara_jucator_nn NOT NULL,
    echipa_id VARCHAR2(6), --fk
    data_nastere DATE,
    --poz

    CONSTRAINT id_jucator_pk PRIMARY KEY(id_jucator),
    CONSTRAINT nume_check CHECK (LENGTH('nume') > 1),
    CONSTRAINT prenume_check CHECK (LENGTH('prenume') > 1)
    --check fara cifre unde nu trebuie, dar asta in java
);

CREATE TABLE ECHIPE(
    id_echipa varchar2(6),
    nume VARCHAR2(30) CONSTRAINT nume_echipa_nn NOT NULL,
    tara VARCHAR2(30) CONSTRAINT tara_echipa_nn NOT NULL,
    
    CONSTRAINT id_echipa_pk PRIMARY KEY(id_echipa),
    CONSTRAINT nume_echipa_uq UNIQUE(nume),
    CONSTRAINT nume_echipa_check CHECK (LENGTH('nume') > 2)
);

CREATE TABLE MECIURI (
    id_meci varchar2(6),
    echipa1_id VARCHAR2(30), --fk
    echipa2_id VARCHAR2(30), --fk
    data_meci DATE,
    scor_e1 NUMBER(2),
    scor_e2 NUMBER(2),
    acasa varchar2(6), --fk
    
    CONSTRAINT id_meci_pk PRIMARY KEY(id_meci)
);

CREATE TABLE STADIOANE (
    id_echipa varchar2(6), --pk si fk
    nume VARCHAR2(30) CONSTRAINT nume_stadion_nn NOT NULL,
    locatie VARCHAR2(30) CONSTRAINT locatie_nn NOT NULL,
    locuri_totale NUMBER(10),
    pret_bilet NUMBER(4),
    
    CONSTRAINT id_echipa_stadion_pk PRIMARY KEY(id_echipa),
    CONSTRAINT nume_stadion_uq UNIQUE(nume),
    CONSTRAINT locuri_totale_ck CHECK(locuri_totale > 0),
    CONSTRAINT pret_bilet_ck CHECK(pret_bilet >= 0)
);

CREATE TABLE MECI_STAT (
    id_meci varchar2(6), --fk
    jucator VARCHAR2(6), --fk
    goluri NUMBER(2),
    asist NUMBER(2),
    cart NUMBER(1),
    --moment
    
    CONSTRAINT goluri_ck CHECK(goluri >= 0),
    CONSTRAINT asist_ck CHECK(asist >=  0),
    CONSTRAINT cart_ck CHECK(cart >= 0)
);

ALTER TABLE STADIOANE MODIFY ( locuri_totale NOT NULL);

ALTER TABLE ECHIPE
DROP COLUMN stadion_id;

ALTER TABLE JUCATORI
ADD CONSTRAINT jucator_echipa_fk FOREIGN KEY(echipa_id) REFERENCES ECHIPE(id_echipa) ON DELETE CASCADE;

ALTER TABLE ECHIPE
DROP CONSTRAINT echipa_stadion_fk;

ALTER TABLE STADIOANE
ADD CONSTRAINT stadion_echipa_fk FOREIGN KEY(id_echipa) REFERENCES ECHIPE(id_echipa) ON DELETE CASCADE;

ALTER TABLE MECIURI
ADD (CONSTRAINT meci_echipa1_fk FOREIGN KEY(echipa1_id) REFERENCES ECHIPE(id_echipa) ON DELETE CASCADE,
     CONSTRAINT meci_echipa2_fk FOREIGN KEY(echipa2_id) REFERENCES ECHIPE(id_echipa) ON DELETE CASCADE);

ALTER TABLE MECIURI
ADD CONSTRAINT meci_acasa_fk FOREIGN KEY(acasa) REFERENCES ECHIPE(id_echipa) ON DELETE CASCADE;
     
ALTER TABLE MECI_STAT
ADD (CONSTRAINT meci_stat_meci_fk FOREIGN KEY(id_meci) REFERENCES MECIURI(id_meci) ON DELETE CASCADE,
     CONSTRAINT meci_stat_jucator_fk FOREIGN KEY(jucator) REFERENCES JUCATORI(id_jucator) ON DELETE CASCADE);

ALTER TABLE MECI_STAT
ADD (moment TIMESTAMP WITH LOCAL TIME ZONE);

ALTER TABLE MECI_STAT
MODIFY (moment DATE);

ALTER TABLE MECI_STAT
ADD (id_stat varchar2(6));

ALTER TABLE MECI_STAT
DROP COLUMN id_stat;


ALTER TABLE MECI_STAT
ADD CONSTRAINT id_stat_pk PRIMARY KEY (id_stat);

ALTER TABLE JUCATORI
ADD (poz varchar2(3));

INSERT INTO ECHIPE(id_echipa,nume,tara) VALUES ('St123','Steaua','Romania');
INSERT INTO JUCATORI(id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz) VALUES ('da123','nu', 'dada', 'da', 'St123', TO_DATE('24.07.2000','DD.MM.YYYY'), 'gk');

INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment) 
SELECT 'fcst94','eutoFC',0,0,0, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS') 
from( SELECT to_char(data_meci,'DD.MM.YYYY') d, ' 1:02:50' t
      FROM MECIURI WHERE MECIURI.id_meci='fcst94' 
);

SELECT TO_CHAR(moment, 'HH24:MI:SS')
FROM MECI_STAT;

UPDATE MECI_STAT SET id_meci='fcst94',jucator='eutoFC',goluri=5,asist=5,cart=5,moment=(
    SELECT to_date(d || t, 'DD.MM.YYYY HH24:MI:SS')
    FROM( SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || '01:05:45' t
          FROM MECIURI WHERE MECIURI.id_meci='fcst94' ))
WHERE id_meci='fcst94' AND jucator='eutoFC';

SELECT e1.nume, m.scor_e1, m.scor_e2, e2.nume, TO_CHAR(data_meci,'DD.MM.YYYY')
FROM MECIURI m, ECHIPE e1, ECHIPE e2
WHERE m.echipa1_id=e1.id_echipa AND
      m.echipa2_id=e2.id_echipa AND
      m.data_meci > TO_DATE('02.02.2010','DD.MM.YYYY');

SELECT TO_NUMBER(TO_CHAR(ms.moment,'HH24')) * 60 + TO_NUMBER(TO_CHAR(ms.moment,'MI')), TO_NUMBER(TO_CHAR(ms.moment,'SS')), ms.goluri, ms.cart, j.nume, j.echipa_id
FROM MECI_STAT ms, JUCATORI j
WHERE j.id_jucator=ms.jucator AND
      (ms.goluri > 0 OR ms.cart > 0);
      
SELECT SUM(ms.goluri) gol
FROM MECI_STAT ms, JUCATORI j
     --(SELECT cart galben FROM MECI_STAT ms, JUCATORI j WHERE cart=1 AND j.id_jucator=ms.jucator AND ms.id_meci='stdi24' AND j.echipa_id='st173')
     --(SELECT cart rosu FROM MECI_STAT ms, JUCATORI j WHERE cart=2 AND j.id_jucator=ms.jucator AND ms.id_meci='stdi24' AND j.echipa_id='st173')
WHERE j.id_jucator=ms.jucator AND 
      ms.id_meci='stdi24' AND 
      j.echipa_id='st173';
      
SELECT jucator, NVL(s,0) FROM MECI_STAT, (SELECT jucator j, NVL(SUM(cart),0) s FROM MECI_STAT WHERE cart=2 GROUP BY jucator) WHERE jucator = j(+);

SELECT cart galben FROM MECI_STAT ms, JUCATORI j WHERE cart=1 AND j.id_jucator=ms.jucator AND ms.id_meci='stdi24' AND j.echipa_id='st173'

SELECT DISTINCT jo.nume || ' ' || jo.prenume full_name, eo.nume, jo.poz, jo.tara, TRUNC((SYSDATE - jo.data_nastere)/365), gol, g.asist, galbene, rosii
FROM JUCATORI jo, ECHIPE eo, MECI_STAT mso, 
    (SELECT id_jucator jucator, NVL(SUM(goluri),0) gol, NVL(SUM(asist),0) asist FROM MECI_STAT m, JUCATORI j WHERE m.jucator(+)=j.id_jucator GROUP BY id_jucator) g,
    (SELECT id_jucator jucator, ss galbene FROM
        (SELECT id_jucator, NVL(s,0) ss FROM JUCATORI, (SELECT jucator ju, NVL(SUM(cart),0) s FROM MECI_STAT WHERE cart=1 GROUP BY jucator) WHERE id_jucator = ju(+))
    )cg,
    (SELECT jucator, ss rosii FROM
        (SELECT id_jucator jucator, NVL(s,0) ss FROM JUCATORI, (SELECT jucator j, NVL(SUM(cart),0)/2 s FROM MECI_STAT WHERE cart=2 GROUP BY jucator) WHERE id_jucator = j(+))
    )cr
WHERE jo.id_jucator=mso.jucator(+) AND
      eo.id_echipa=jo.echipa_id AND
      g.jucator=jo.id_jucator AND
      cg.jucator=jo.id_jucator AND
      cr.jucator=jo.id_jucator
ORDER BY full_name;

SELECT nume, tara, wins*3+draws "points", wins, draws, defeats, games, CASE WHEN games=0 THEN 0 ELSE goals/games END goalavg
FROM ECHIPE e,
    (SELECT e.id_echipa id, NVL(SUM(
        CASE WHEN scor_e1 > scor_e2 AND e.id_echipa=m.echipa1_id THEN 1
             WHEN scor_e1 < scor_e2 AND e.id_echipa=m.echipa2_id THEN 1
             ELSE 0 END
    ),0) wins, NVL(SUM(
        CASE WHEN scor_e1=scor_e2 THEN 1 ELSE 0 END
    ),0) draws, NVL(SUM(
        CASE WHEN scor_e1 < scor_e2 AND e.id_echipa=m.echipa1_id THEN 1
             WHEN scor_e1 > scor_e2 AND e.id_echipa=m.echipa2_id THEN 1
             ELSE 0 END
    ),0) defeats
    FROM ECHIPE e, MECIURI m
    WHERE ((e.id_echipa=m.echipa1_id) OR (e.id_echipa=m.echipa2_id)) AND m.data_meci <= SYSDATE
    GROUP BY e.id_echipa) t1,
    (SELECT e.id_echipa id, NVL(SUM(goluri),0) goals, NVL(COUNT(DISTINCT ms.id_meci),0) games
    FROM ECHIPE e, JUCATORI j, MECI_STAT ms
    WHERE e.id_echipa=j.echipa_id AND
          j.id_jucator=ms.jucator(+)
    GROUP BY e.id_echipa) t2
WHERE e.id_echipa = t1.id AND
      e.id_echipa = t2.id
ORDER BY wins*3+draws DESC, goalavg DESC;

SELECT e.id_echipa, NVL(SUM(
    CASE WHEN scor_e1 > scor_e2 AND e.id_echipa=m.echipa1_id THEN 1
         WHEN scor_e1 < scor_e2 AND e.id_echipa=m.echipa2_id THEN 1
         ELSE 0 END
),0) wins, NVL(SUM(
    CASE WHEN scor_e1=scor_e2 THEN 1 ELSE 0 END
),0) draws, NVL(SUM(
    CASE WHEN scor_e1 < scor_e2 AND e.id_echipa=m.echipa1_id THEN 1
         WHEN scor_e1 > scor_e2 AND e.id_echipa=m.echipa2_id THEN 1
         ELSE 0 END
),0) defeats
FROM ECHIPE e, MECIURI m
WHERE (e.id_echipa=m.echipa1_id) OR (e.id_echipa=m.echipa2_id) 
GROUP BY e.id_echipa;

SELECT e.id_echipa, NVL(SUM(goluri),0) goals, NVL(COUNT(DISTINCT ms.id_meci),0) games
FROM ECHIPE e, JUCATORI j, MECI_STAT ms
WHERE e.id_echipa=j.echipa_id AND
      j.id_jucator=ms.jucator
GROUP BY e.id_echipa;

SELECT e.nume, e.tara, 
FROM ECHIPE e, MECIURI m, 
    (SELECT e.echipa_id, NVL(COUNT(scor_e1)))
WHERE (e.id_echipa=m.echipa1_id OR e.id_echipa=m.echipa2_id)

SELECT j.nume || ' ' || j.prenume , j.poz
FROM JUCATORI j, ECHIPE e
WHERE j.echipa_id=e.id_echipa AND
      e.nume='Steaua Bucuresti'
ORDER BY j.nume || ' ' || j.prenume;

SELECT e1.nume, e2.nume, m.data_meci
FROM MECIURI m, ECHIPE e, ECHIPE e1, ECHIPE e2
WHERE (e.id_echipa=m.echipa1_id OR e.id_echipa=m.echipa2_id) AND
      e1.id_echipa=m.echipa1_id AND
      e2.id_echipa=m.echipa2_id AND
      e.nume = 'Steaua Bucuresti'
ORDER BY m.data_meci;

INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat)
       SELECT 'pali66','melipa',1,0,0, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),'pame29'
       FROM( SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(20/60)) || ':' || TO_CHAR(20) || ':' || 5 t
              FROM MECIURI WHERE MECIURI.id_meci='pali66' );

INSERT INTO JUCATORI VALUES ('ad25','da','da', 'da', (SELECT id_echipa FROM ECHIPE WHERE nume='A.C. Milan'), TO_DATE('20.12.1999','DD.MM.YYYY'),'LOL');

SELECT s.nume, e.nume, s.locatie, e.tara, s.pret_bilet, s.locuri_totale
FROM STADIOANE s, ECHIPE e
WHERE s.id_echipa = e.id_echipa AND
      s.nume = 'San Siro';



--SELECT COUNT (*) FROM JUCATORI;

--DROP TABLE STADIOANE;
--DROP TABLE JUCATORI;
--DROP TABLE MECIURI;
--DROP TABLE MECI_STAT;
--DROP TABLE ECHIPE;
    