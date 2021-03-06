WSTrack
=======

WSTrack (Workstation Tracking Project) contains client and server projects for tracking user workstation logins.

Documentation
-------------


How to Install WSTrack on the Server
-------------

    # Stop the tomcat

    cd /apps/cms/tomcat-misc
    ./control stop

    cd /apps/cms/webapps
    mv wstrack wstrack-old.dir
    mv wstrack{.war,-war.old}

    # SNAPSHOT versions should include the full version string. (E.g. 1.7-20180620.202022-1)
    wget -O wstrack.war 'https://maven.lib.umd.edu/nexus/service/rest/beta/search/assets/download?&group=edu.umd.lib.wstrack.server&name=wstrack-server&maven.extension=war&version=<VERSION>'

    mv wstrack-*.war wstrack.war
    cd /apps/cms/tomcat-misc
    ./control start

    # Test

    rm -rf wstrack-old.dir

Build Instructions
-------------
To build projects execute `mvn  -DskipTests clean install` from the repository root directory. 

That will create 2 files, a .war (the server code) located here `/wstrack/server/target/wstrack-server-{version}.war` and a .jar (the client code) located here `/wstrack/client/target/wstrack-client-{version}-jar-with-dependencies.jar`

How to Test the App?
--------------------

* 1. Build the code using `mvn  -DskipTests clean install` from the repository root directory.
* 2. Navigate to ~/server/ and run "grails run-app"
* 3. Navigate to ~/client/scripts/ and execute ./wstrack-client.sh [login|logout] [local|DEV|Prod] 
    (This step should add a new row in the Current list.)

Note - While executing "./wstrack-client.sh [login|logout] [local|DEV|Prod]" , if you get an error "Unable to access jarfile /apps/git/wstrack/client/script/wstrack-client.jar"
Follow the following steps.

1. Navigate to ~/client/scripts and remove the wstrack-client.jar file. (rm -rf wstrack-client.jar)
2. Execute "ln -s ../target/wstrack-client-{VERSION}-jar-with-dependencies.jar wstrack-client.jar" (This will relink the jar file with the correct jar file.)
3. Restart the server. and rerun the ./wstrack-client.sh [login|logout] [local|DEV|Prod] command.

License
-------

See the [LICENSE](LICENSE.md) file for license rights and limitations (Apache 2.0).
