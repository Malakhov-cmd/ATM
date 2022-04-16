cd client
mvn clean && mvn package && java -Dspring.profiles.active=dev -jar ./target/client-0.0.1-SNAPSHOT.jar