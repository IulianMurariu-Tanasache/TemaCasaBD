CREATE TABLE jucatori(
    --id_jucator NUMBER(4) PK
    nume VARCHAR2(20) CONSTRAINT nume_nn NOT NULL,
    prenume VARCHAR2(20) CONSTRAINT prenume_nn NOT NULL,
    tara VARCHAR2(30),
    echipa VARCHAR2(30),
    goluri NUMBER(4),
    cart_galben NUMBER(3),
    cart_rosu NUMBER(3),

    CONSTRAINT nume_jucator_uq UNIQUE (nume, prenume),
    CONSTRAINT goluri_poz_ck CHECK (goluri >= 0),
    CONSTRAINT galbene_poz_ck CHECK (cart_galben >= 0),
    CONSTRAINT rosii_poz_ck CHECK (cart_rosu >= 0));

CREATE TABLE ECHIPE(
    nume VARCHAR2(30) CONSTRAINT nume_echipa_nn NOT NULL, -- PK?
    tara VARCHAR2(30),
    puncte NUMBER(4),
    victorii NUMBER(4),
    infrangeri NUMBER(4),
    egaluri NUMBER(4),
    golavg NUMBER(4),
    
    CONSTRAINT nume_echipa_uq UNIQUE(nume),
    CONSTRAINT victorii_poz CHECK(victorii >= 0),
    CONSTRAINT infrangeri_poz CHECK(infrangeri >= 0),
    CONSTRAINT egaluri_poz CHECK(egaluri >= 0),
    CONSTRAINT puncte_poz CHECK(golavg >= 0)
);

CREATE TABLE MECIURI (
    id_meci NUMBER(4), -- pk
    echipa1 VARCHAR2(30) CONSTRAINT nume_echipa1_nn NOT NULL, --FK
    echipa2 VARCHAR2(30) CONSTRAINT nume_echipa2_nn NOT NULL, --FK
    data DATE,
    rezultat VARCHAR2(20),
    stadion VARCHAR2(30), --FK
    locuri_libere NUMBER(4),
    
    CONSTRAINT locuri_libere_poz CHECK(locuri_libere >= 0)
);

CREATE TABLE STADIOANE (
    nume VARCHAR2(30) CONSTRAINT nume_stadion_nn NOT NULL,
    locatie VARCHAR2(30) CONSTRAINT locatie_nn NOT NULL,
    locuri_totale NUMBER(10),
    data DATE,
    meci VARCHAR2(30), -- FK
    
    CONSTRAINT nume_stadion_uq UNIQUE(nume)
);

--INSERT INTO ECHIPE VALUES ('Rapid Bucuresti', 'Romania', 7, 2, 0, 1, 2); 
SELECT * FROM ECHIPE;
--UPDATE ECHIPE SET nume='FCSB', tara='nu', puncte=7, victorii=4, infrangeri=0, egaluri=20, golavg=100 WHERE nume='FCSB';