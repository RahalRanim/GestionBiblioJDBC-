-- Création de la table Utilisateur
CREATE TABLE Utilisateur (
    id_utilisateur numeric PRIMARY KEY,
    nom VARCHAR(30),
    prenom VARCHAR(30),
    login VARCHAR(50),
    pwd VARCHAR(30),
    rolee VARCHAR(10)
);
-- Création de la table Livre
CREATE TABLE Livre (
    id_livre numeric PRIMARY KEY,
    titre VARCHAR(40),
    auteur VARCHAR(40),
    genre varchar(1),
    dispo numeric(1)
);

-- Création de la table Emprunt
CREATE TABLE Emprunt (
    id_emprunt numeric PRIMARY KEY,
    id_utilisateur numeric ,
    id_livre numeric,
    date_emprunt DATE,
    date_retour DATE,
    statut VARCHAR(20),  -- (Emprunté/En attente/Retourné/En retard)
    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur),
    FOREIGN KEY (id_livre) REFERENCES Livre(id_livre)
);

-- Création de la table Reservation
CREATE TABLE Reservation (
    id_reservation numeric PRIMARY KEY,
    id_utilisateur numeric ,
    id_livre numeric ,
    date_reservation DATE,
    statut2 VARCHAR(20),
    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur),
    FOREIGN KEY (id_livre) REFERENCES Livre(id_livre)
);
alter table utilisateur
modify rolee varchar(20);
alter table livre
modify genre varchar(55);

