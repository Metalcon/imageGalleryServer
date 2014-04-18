export MAVEN_OPTS=-Djava.library.path=/usr/local/lib
echo $MAVEN_OPTS
mvn exec:java -Dexec.args="$@"
