#!/bin/bash
set -x

JBOSS_HOME=target/server

$JBOSS_HOME/bin/add-user.sh -u admin -p admin