# WhiteApp

Exemple architectural de projet avec les technologies Java EE.
Ce projet est sous licence **CeCILL** (**CE**A **C**NRS **I**NRIA **L**ogiciel **L**ibre),
une licence de logiciel libre compatible avec la **GNU GPL**.

> En savoir plus sur la licence [CeCILL](http://cecill.info/index.fr.html)

## Objectifs

Mettre en œuvre:

* Les technologies Java EE
* La structure d'un projet type *EAR*
* Payara

## Architecture

L'architecture logicielle mis en place est **SOA** *(Service Oriented Architecture)* en respectant au mieux les règles d'élégance du code *(clean code)* et *S.O.L.I.D.*.

## Environnement

Ce projet est réalisé en **Java 11** *(OpenJDK)*. et **JavaEE 8**.
Il utilise l'outil **Maven** en version 3.6.2.

### Exécution

Récupération du projet:
~~~
    git clone https://github.com/ZelmoTheDragon/whiteapp.git
    cd whiteapp
    mvn install
~~~
