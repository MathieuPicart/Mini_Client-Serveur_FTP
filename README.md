# Mini Client-Serveur FTP

> Mini Client/Serveur FTP. #java #reseau #ftp

Dans le cadre de ce Mini Projet, a faire durant les séances des TPs, on envisage de développer un mini Client/Serveur FTP. Ainsi, le projet comporte deux applications complètement indépendantes, le client et le serveur. L'application serveur, sur laquelle les commandes à exécuter sont codées. L'application client, qui doit pouvoir faire des appels à ces fonctions sans manipulation direct sur le code du serveur. Le détail concernant chacune de ces applications est donné comme suit :

Le serveur doit permettre à un client de se connecter. Une fois le client connecté, le serveur doit lui envoyer le texte suivant (3 lignes):
1 Bienvenue !
1 Serveur FTP Personnel.
O Authentification :


Le serveur envoie des réponses sous la forme : x texte
Le x désigne l'état de la réponse. Il peut avoir 3 valeurs possibles : 
* 0 → Signifie que la réponse est positive et finie, il n'y a rien à lire après
* 1 → Signifie que la réponse est intermédiaire et pas finie. Il faut continuer à lire car il reste encore des lignes à lire. 
* 2 → Signifie que la réponse est négative et finie, il n'y a rien à lire après

Une fois connecté au serveur, le client doit envoyer les deux commandes suivantes pour se connecter sur son compte. Aucune commande ne doit être acceptée si l'utilisateur n'est pas connecté. A cette étape, il n'existe aucun compte physique réel. 
user personne
pass abcd

Pour quitter le serveur, il faut lancer la commande bye.
Les commandes à implémenter sont :
- USER : pour envoyer le nom du login
- PASS : pour envoyer le mot de passe
- LS : afficher la liste des dossiers et des fichiers du répertoire courant du serveur
- CD : pour changer de répertoire courant du côté du serveur 
- PWD: pour afficher le chemin absolu du dossier courant
- STOR : pour envoyer un fichier vers le dossier courant serveur
- GET: pour télécharger un fichier du serveur vers le client

Le code donné contient une première version du serveur et du client. La commande PWD lémentée. Uniquement un seul client est accepté à la fois, et c'est le client a
personne et mot de passe : abcd.


## Table of contents
* [Information General](#information-general)
* [Technologies](#technologies)
* [Mockup](#mockup)
* [Demo](#demo)
* [Contact](#contact)

## Information General

### Etape 1 : Application de base
1. Utilisez Eclypse et créer un nouveau projet Java
2. Téléchargez les sources Java du serveur FTP via l'url suivante :
3. Lisez-le et analysez-le. Quelles sont les commandes ftp implémentées ?
4. Créer un client Java permettant de se connecter à ce serveur et de lui envoyer des commandes ftp (a ce stade on peut utiliser les commandes user, pass et pwd
5. Complétez toutes les commandes non implémentées du serveur 6. Compléter le client pour pouvoir envoyer toutes les commandes implémentées

### Étape 2: Amélioration

On suppose qu'un seul client à la fois peut se connecter sur le serveur. Dans cette étape, on envisage gérer les erreurs liées au départ du client et du serveur. Il faut ajouter la possibilité que le client se déconnecte proprement lorsque le serveur s'arrête subitement. Il doit afficher un message comme quelle serveur est déconnecté. Et de même pour le serveur, il faut qu'il puisse continuer à attendre d'autres clients lorsqu'un client part.

### Etape 3 : Perfection

Dans cette étape, le serveur doit pouvoir recevoir plusieurs clients. Il faut ajouter la possibilité de connexion de plusieurs clients à la fois. Pour ce faire, il faut suivre la démarche suivante:

1. Derrière chaque client se cache un dossier au niveau du serveur portant le nom de son user. A l'intérieur de ce dossier on met un fichier pw.txt contenant le mot de passe La connexion d'un client passe par la commande user nom user qui permet au serveur de vérifier l'existence d'un dossier au nom de nom user et la commande passe xyz qui permet au serveur de vérifier que le texte du fichier pw.txt est le même que xyz. A noter que chaque commande envoyée par le client au serveur engendre une action au niveau du serveur et une réponse au client.
2. Il faut commencer par une version gérant un seul client à la fois 
3. Ensuite par une version qui gère plusieurs clients à la fois.

Cas des commandes STOR et GET:
Pour programmer ces deux commandes, il faut s'inspirer du protocole FTP réel. C'est-à-dire, Il faut prévoir l'utilisation de deux canaux comme suit :
1. STOR nom fichier
La commande STOR permet au serveur de créer un fichier vide au niveau du working directory (dossier courant) au nom de nom_fichier donné en argument. Ensuite, il faut ouvrir un deuxième canal (Socket) sur un autre port (ex. 4000). A partir de ce deuxième canal il faut créer un flux en écriture. C'est ce dernier qui sera utilisé pour envoyer les données du fichier. Une fois le fichier créé, fermer le flux ainsi que le canal correspondant La communication continue toujours sur le premier canal.

2 GET nom fichier
La commande GET permet au client de créer un fichier vide au niveau local au nom de nom_fichier donné en argument. Ensuite, il faut ouvrir un deuxieme canal (Socket) sur un autre port (ex. 5000). A partir de ce deuxième canal il faut créer un flux en lecture. C'est ce dernier qui sera utilisé pour récupérer les données du fichier. Une fois le fichier créé, fermer le flux ainsi que le canal correspondant. La communication continue toujours sur le premier canal

### Etape 4 : L'IHM
Ajouter une interface graphique. Utilisez JavaFX ou Swing selon votre préférence.

## Technologies
* Java

## Mockup
Maquette : [click here to see the mockup](https://owenlebec.fr/)

---

## Demo
[See demo]()

## Contact
```
print("Created by")
let team : {
    "Full-Stack" : {
        firstname : Picart,
        name : Mathieu,
        git : https://github.com/MathieuPicart
    },
    "Back-End" : {
        firstname : Frere,
        name : Jules,
        git : https://github.com/JurleBro
    },
    "Front-End" : {
        firstname : Le Bec,
        name : Owen,
        git : https://github.com/OwenLB
    }
}
```
