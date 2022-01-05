--SCRIPTURI INSERARE IN TABELE
--TABELA ECHIPE
INSERT INTO ECHIPE(id_echipa,nume,tara) VALUES('ba24','Barcelona FC','Spain');
INSERT INTO ECHIPE(id_echipa,nume,tara) VALUES('re78','Real Madrid','Spain');
INSERT INTO ECHIPE(id_echipa,nume,tara) VALUES('la16','LA Galaxy','USA');
INSERT INTO ECHIPE(id_echipa,nume,tara) VALUES('po166','Porto FC','Portugal');
INSERT INTO ECHIPE(id_echipa,nume,tara) VALUES('ga420','Galatasaray SK','Turkey');

--TABELA JUCATORI
INSERT INTO JUCATORI(id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz) VALUES('aljoba','Alba','Jordi','Spain','ba24',TO_DATE('21.03.1989','DD.MM.YYYY'),'LB');
INSERT INTO JUCATORI(id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz) VALUES('molure','Modric','Luka','Croatia','re78',TO_DATE('09.09.1985','DD.MM.YYYY'),'CM');
INSERT INTO JUCATORI(id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz) VALUES('bojola','Bond','Jonathan','United Kingdom','la16',TO_DATE('19.03.1993','DD.MM.YYYY'),'GK');
INSERT INTO JUCATORI(id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz) VALUES('dilupo','Diaz','Luis','Colombia','po166',TO_DATE('13.01.1997','DD.MM.YYYY'),'LM');
INSERT INTO JUCATORI(id_jucator,nume,prenume,tara,echipa_id,data_nastere,poz) VALUES('moovga','Morutan','Ovidiu','Romania','ga420',TO_DATE('25.04.1999','DD.MM.YYYY'),'CAM');

--TABELA STADIOANE
INSERT INTO STADIOANE(id_echipa,nume,locatie,locuri_totale,pret_bilet) VALUES('ba24','Camp Nou','Barcelona',99354,269);
INSERT INTO STADIOANE(id_echipa,nume,locatie,locuri_totale,pret_bilet) VALUES('re78','Santiago Bernabeu','Madrid',81044,74);
INSERT INTO STADIOANE(id_echipa,nume,locatie,locuri_totale,pret_bilet) VALUES('la16','Dignity Health Sports','California',27000,69);
INSERT INTO STADIOANE(id_echipa,nume,locatie,locuri_totale,pret_bilet) VALUES('po166','Estadio do Dragao','Porto',50033,77);
INSERT INTO STADIOANE(id_echipa,nume,locatie,locuri_totale,pret_bilet) VALUES('ga420','Nef Stadyumu','Istanbul',52280,51);

--TABELA MECUIRI
INSERT INTO MECIURI(id_meci,echipa1_id,echipa2_id,data_meci,scor_e1,scor_e2,acasa) VALUES('bare54','ba24','re78',TO_DATE('10.10.2020','DD.MM.YYYY'),0,1,'ba24');
INSERT INTO MECIURI(id_meci,echipa1_id,echipa2_id,data_meci,scor_e1,scor_e2,acasa) VALUES('poga10','po166','ga420',TO_DATE('18,06,2020','DD.MM.YYYY'),0,0,'ga420');
INSERT INTO MECIURI(id_meci,echipa1_id,echipa2_id,data_meci,scor_e1,scor_e2,acasa) VALUES('liga94','li711','ga420',TO_DATE('01.01.2021','DD.MM.YYYY'),2,0,'ga420');
INSERT INTO MECIURI(id_meci,echipa1_id,echipa2_id,data_meci,acasa) VALUES('rare8','ra447','re78',TO_DATE('05.07.2022','DD.MM.YYYY'),'ra447');
INSERT INTO MECIURI(id_meci,echipa1_id,echipa2_id,data_meci,scor_e1,scor_e2,acasa) VALUES('papo46','pa526','po166',TO_DATE('09.04.2020','DD.MM.YYYY'),1,0,'po166');

--TABELA MECI_STAT
INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat)
    SELECT 'bare54','molure',1,0,0, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),'bamo74'
    FROM( 
        SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(34/60)) || ':' || TO_CHAR(34) || ':' || 20 t 
        FROM MECIURI 
        WHERE MECIURI.id_meci='bare54');
        
INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat)
    SELECT 'poga10','moovga',0,0,1, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),'pomo68'
    FROM( 
        SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(72/60)) || ':' || TO_CHAR(12) || ':' || 16 t 
        FROM MECIURI 
        WHERE MECIURI.id_meci='poga10');
        
INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat)
    SELECT 'liga94','mosali',1,0,0, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),'limo88'
    FROM( 
        SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(21/60)) || ':' || TO_CHAR(21) || ':' || 44 t 
        FROM MECIURI 
        WHERE MECIURI.id_meci='liga94');
        
INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat)
    SELECT 'liga94','mosali',1,0,0, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),'limo84'
    FROM( 
        SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(43/60)) || ':' || TO_CHAR(43) || ':' || 44 t 
        FROM MECIURI 
        WHERE MECIURI.id_meci='liga94');
    
INSERT INTO MECI_STAT(id_meci,jucator,goluri,asist,cart,moment,id_stat)
    SELECT 'papo46','nedapa',1,0,0, to_date(d || t, 'DD.MM.YYYY HH24:MI:SS'),'pane33'
    FROM( 
        SELECT to_char(data_meci,'DD.MM.YYYY') d, ' ' || TO_CHAR(trunc(54/60)) || ':' || TO_CHAR(14) || ':' || 35 t 
        FROM MECIURI 
        WHERE MECIURI.id_meci='papo46');