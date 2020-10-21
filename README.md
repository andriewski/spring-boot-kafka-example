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
