-- Table utilisateur
insert into utilisateur values(1,'Rahal','Ranim','rahal.ranim@gmail.com','111','etudiant');
insert into utilisateur values(2,'Chalghoum','Mayssa','chalghoum.mayssa@gmail.com','111','enseignant');
insert into utilisateur values(3,'Biblio','Flen','bib.flen@gmail.com','111','bibliothécaire');
select * from utilisateur;

-- table livre
insert into livre values(1,"Les Misérables","Victor Hugo","Roman historique",1);
insert into livre values(2,"Le petit Prince","Antoine de Saint-Exupéry","Conte philosophique",1);
insert into livre values(3,"Mille femmes blanches","Jim Fergus","Fiction historique",1);
insert into livre values(4,"1984","George Orwell","Dystopie",1);
select * from livre;

-- table emprunt 
insert into emprunt values(12,1,2,CURRENT_TIMESTAMP - INTERVAL 2 DAY, CURRENT_TIMESTAMP + INTERVAL 10 DAY,"en cours");
insert into emprunt values(21,2,1,CURRENT_TIMESTAMP - INTERVAL 10 DAY,CURRENT_TIMESTAMP ,"terminé");
select * from emprunt;

commit;

select titre, date_emprunt,statut from emprunt e,livre l
	where e.id_livre=l.id_livre;
-- les livres plus empruntés
select livre.titre, count(emprunt.id_livre) As Nombre_emprunts
	from livre , emprunt
    where emprunt.id_livre=livre.id_livre 
    group by livre.titre
    order by Nombre_emprunts desc;
-- utilisateurs les plus assidus
select utilisateur.nom, utilisateur.prenom, count(emprunt.id_emprunt) As nombre_emprunts
	from utilisateur, emprunt
    where utilisateur.id_utilisateur=emprunt.id_utilisateur
    group by utilisateur.id_utilisateur
    order by nombre_emprunts desc;
SELECT utilisateur.nom , utilisateur.prenom
FROM emprunt
JOIN utilisateur ON emprunt.id_utilisateur = utilisateur.id_utilisateur
WHERE emprunt.date_retour < NOW();
ALTER TABLE emprunt
MODIFY COLUMN id_emprunt INT AUTO_INCREMENT;
ALTER TABLE reservation
MODIFY COLUMN id_reservation INT AUTO_INCREMENT;

select * from reservation;
insert into reservation(id_utilisateur,id_livre,date_reservation,statut2)
values(2,2,"2023-12-7","En attente");
SELECT id_reservation FROM reservation WHERE id_livre = 2 ORDER BY date_reservation ASC LIMIT 1;
select * from reservation;
select * from emprunt;
select * from utilisateur;
describe utilisateur;
select * from livre;
SELECT l.titre, e.date_emprunt, e.statut
FROM emprunt e
JOIN livre l ON e.id_livre = l.id_livre;
update emprunt set statut="en cours" where id_emprunt=21;

