export MAVEN_OPTS=-Djava.library.path=/usr/local/lib
mvn exec:java -Dexec.args="$@"
