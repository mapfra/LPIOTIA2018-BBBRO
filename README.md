<<<<<<< HEAD
About OM2M
==========

OM2M is an open source OSGi-based implementation of the ETSI M2M standard .

Prerequisites:
* JAVA 1.7 tu run OM2M.
* Apaceh Maven 3 to build OM2M.

Building OM2M from sources using maven:
* Go to the parent project directory "org.eclipse.om2m"
* Build the OM2M project using the following command:
 > mvn clean install
* The NSCL product is generated on the this directory: "om2m/org.eclipse.om2m/org.eclipse.om2m.site.nscl/target/products/nscl/<os>/<ws>/<arch>"
* The GSCL product is generated on the this directory: "om2m/org.eclipse.om2m/org.eclipse.om2m.site.gscl/target/products/gscl/<os>/<ws>/<arch>"

Configure and Start the NSCL:
* Go to the NSCL product directory.
* You can edit the file "configuration/config.ini" to configure the NSCL
* You can start the NSCL using the following command:
> java -jar -ea -Declipse.ignoreApp=true -Dosgi.clean=true -Ddebug=true plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar -console -noExit
* Open your browser, then enter the address "http://127.0.0.1:8080" to access the NSCL web interface.

Configure and Start the GSCL:
* Go to the NSCL product directory.
* The GSCL can be configured and started with the same steps used for the NSCL.
* Open your browser, then enter the address "http://127.0.0.1:8181" to access the GSCL web interface.

For more details, see http://wiki.eclipse.org/OM2M
=======

              * Accroche du projet
              * nom des étudiants
              * les modeles d'analyse : use cases, ...
              * les exigences
             *  les maquettes 
             * Technos utilisées
             * Organisation du projet
                 * politique de branches
                 * sprints (liés aux Milestones) (Juste les objectifs des Sprints non détaillés)
                 * gestion des tests
                 * politique d'intégration continue
                 * Boards (Lien vers les boards si pas évident)
           
                 
    Le product BackLog (on commence à regarder le 28 janvier matin)
             * les fonctionnalités attendues (histoires utilisateur si vous savez faire) avec un tag dédié  (US ou fonctionnalité ou feature au choix...)
             * les tâches à réaliser  et les liens entre les issues en gitlab (pas trouvé sous github...)
             * l'organisation en Sprint sous forme de milestones
>>>>>>> origin/rasp2
