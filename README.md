#### Download Kafka & unzip it
    https://kafka.apache.org/quickstart
    tar -xzf kafka_2.13-2.6.0.tgz
    cd kafka_2.13-2.6.0

#### Start the ZooKeeper service
##### Note: Soon, ZooKeeper will no longer be required by Apache Kafka.
    bin/zookeeper-server-start.sh config/zookeeper.properties

#### Start the Kafka broker service
    bin/kafka-server-start.sh config/server.properties

#### Create topic
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic spring-kafka-demo --partitions 1 --replication-factor 1

#### Show created topic
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

#### Connect consumer
    bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic spring-kafka-demo

#### Delete topic
    bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic spring-kafka-demo --force

#### Send messages with apache utils 
    ab -n 10000 -c 10 http://localhost:8080/message/send-random-message

## With docker
#### Create a topic
    docker exec -it kafka kafka-topics --zookeeper zookeeper:2181 --create --topic click-topic --partitions 1 --replication-factor 1

#### Show created topic
    docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list

#### Connect producer
    docker exec -it kafka kafka-console-producer --broker-list localhost:9092 --topic click-topic

#### Connect consumer
    docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic click-topic --group group12

#### Delete topic
    docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --delete --topic click-topic --force

#### Send messages at once
    kafka-console-producer --broker-list kafka:9092 --topic readings <<END
    1,"2020-05-16 23:55:44",14.2
    2,"2020-05-16 23:55:45",20.1
    3,"2020-05-16 23:55:51",12.9
    END
    