Projet P9

    TAUX DE COVERAGE du projet : 91%
    Répertoire - docker : conteneur docker de la base de données du projet

Requêtes SQL :

Les requêtes du projet :
    - /myerp-consumer/src/main/resources/com/dummy/myerp/consumer/sqlContext.xml

Initialisation du projet
1 - Docker :

Dans le fichier /docker/dev/docker-compose.yml modifier l'adresse IP en fonction de votre propre environnement

ports:
    - "[votre adresse ip]:9032:5432"

Depuis votre environnement docker

1) se placer dans le répertoire du conteneur à assembler : 
    -  cd /docker/dev
    
2) Charger le conteneur
    - docker-compose up -d
    
3) Démarrage du conteneur
    - docker run --name dev_myerp.db_1 -e POSTGRES_PASSWORD=myerp -d postgres

Pour arréter le conteneur
    - docker-compose stop

2 - Properties Database

Modifier l'adresse ip que vous avez paramétré dans docker

- /myerp-consumer/src/main/resources/database.properties :
    myerp.datasource.driver=org.postgresql.Driver
    myerp.datasource.url=jdbc:postgresql://[votre adresse ip]:9032/db_myerp
    myerp.datasource.username=usr_myerp
    myerp.datasource.password=myerp

3 - Tests unitaires

Afin de vérifier le coverage on s'appuie sur jacoco les profiles à activer sont :

- coverage et business

Lancement commande maven :

- mvn clean verify

Lancement avec TravisCI 

- Le fichier de configuration est a la racine du projet travis.yml

-Se lance automatiquement apres un commit.(lié à github)

Pour le taux de coverage :

- soit par votre navigateur en ouvrant les fichiers :
    - myerp-business/target/test-results/coverage/jacoco/index.html
    - myerp-model/target/test-results/coverage/jacoco/index.html

Correctifs principaux

    Absence de la configuration de la dataSource
    - Dans l'entité EcritureComptable, correction des méthodes getTotalCredit() && getTotalDebit() sur le format de retour 2 chiffres après la virgule
    - Dans l'entité EcritureComptable, correction de la méthode getTotalCredit() qui accédait à la méthode getDebit() au lieu de getCredit()
    - Dans le fichier sqlContext.xml, corriger la propriété SQLinsertListLigneEcritureComptable. Il manquait une virgule dans le INSERT entre les colonnes debit et credit
    - Dans la classe ComptabiliteManagerImpl, correction de la méthode updateEcritureComptable(). Ajouter la ligne this.checkEcritureComptable(pEcritureComptable); en haut afin de vérifier que la référence de l'écriture comptable respecte les règles de comptabilité 5 et 6
    - Dans la classe EcritureComptable le regexp n'etait pas bon.