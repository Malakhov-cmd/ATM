cd server
mvn clean && mvn package && java -DPROFILE=test -jar ./target/server-0.0.1-SNAPSHOT.jar