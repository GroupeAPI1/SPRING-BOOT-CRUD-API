USE apibd;
CREATE TABLE client (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nom VARCHAR(50) NOT NULL,
  prenom VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  adresse VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE compte (
  id INT(11) NOT NULL AUTO_INCREMENT,
  type_compte VARCHAR(50) NOT NULL,
  date_creation DATE NOT NULL,
  solde DECIMAL(10,2) NOT NULL,
  numero VARCHAR(50) NOT NULL,
  proprietaire_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (proprietaire_id) REFERENCES client(id)
);
