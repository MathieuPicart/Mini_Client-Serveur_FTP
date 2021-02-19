# Mini Client-Serveur FTP

Mini Client/Serveur FTP. #java #reseau #ftp

Dans le cadre de ce Mini Projet, a faire durant les séances des TPs, on envisage de développer un mini Client/Serveur FTP. Ainsi, le projet comporte deux applications complètement indépendantes, le client et le serveur. L'application serveur, sur laquelle les commandes à exécuter sont codées. L'application client, qui doit pouvoir faire des appels à ces fonctions sans manipulation direct sur le code du serveur. Le détail concernant chacune de ces applications est donné comme suit :

Le serveur doit permettre à un client de se connecter. Une fois le client connecté, le serveur doit lui envoyer le texte suivant (3 lignes):

1 Bienvenue !
1 Serveur FTP Personnel.
O Authentification :


Le serveur envoie des réponses sous la forme : x texte
Le x désigne l'état de la réponse. Il peut avoir 3 valeurs possibles : 
0 → Signifie que la réponse est positive et finie, il n'y a rien à lire après
1 Signifie que la réponse est intermédiaire et pas finie. Il faut continuer à lire car il reste encore des lignes à lire. 
2 → Signifie que la réponse est négative et finie, il n'y a rien à lire après

Une fois connecté au serveur, le client doit envoyer les deux commandes suivantes pour se connecter sur son compte. Aucune commande ne doit être acceptée si l'utilisateur n'est pas connecté. A cette étape, il n'existe aucun compte physique réel. 
user personne
pass abcd

Pour quitter le serveur, il faut lancer la commande bye.
Les commandes à implémenter sont :

1. CD: pour changer de répertoire courant du côté du serveur GET: pour télécharger un fichier du serveur vers le client

LS: afficher la liste des dossiers et des fichiers du répertoire courant du serveur

PASS : pour envoyer le mot de passe PWD: pour afficher le chemin absolu du dossier courant

STOR : pour envoyer un fichier vers le dossier courant serveur

USER: pour envoyer le nom du login

Le code donné contient une première version du serveur et du client. La commande PWD lémentée. Uniquement un seul client est accepté à la fois, et c'est le client a
personne et mot de passe : abcd.


## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Mockup](#mockup)
* [Demo](#demo)
* [Contact](#contact)

## General info

## Technologies
* Java

## Mockup
Maquette : [click here to see the mockup](https://www.figma.com/file/udO2sqspUWIROMsAbJb3yN/Projet-Ebooks?node-id=0%3A1 "Figma Mockup")

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
