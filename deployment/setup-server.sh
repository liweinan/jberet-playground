#!/bin/bash
set -x

# run it after running `mvn install` to provision the WildFly server.
JBOSS_HOME=target/server

$JBOSS_HOME/bin/add-user.sh -u admin -p admin
$JBOSS_HOME/bin/jboss-cli.sh -c --commands='xa-data-source add --name=batch_db --enabled=true --use-java-context=true --use-ccm=true --jndi-name=java:jboss/jsr352/batch_db --xa-datasource-properties={"URL"=>"jdbc:h2:/tmp/batch_db"} --driver-name=h2 --password=sa --user-name=sa --same-rm-override=false --no-recovery=true, /subsystem=batch-jberet/jdbc-job-repository=batch_db:add(data-source=batch_db), /subsystem=batch-jberet/:write-attribute(name=default-job-repository,value=batch_db)'