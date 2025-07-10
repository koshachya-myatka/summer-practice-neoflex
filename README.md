## Информация для части с Debezium:
docker compose up --build -d

### Настройка коннектора
* Создание и настройка коннектора
    *  curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d @connector.json
* Проверка статуса коннектора
    *  curl http://localhost:8083/connectors/producer-service-connector/status
* Проверка топиков
    *  docker-compose exec kafka kafka-topics --list --bootstrap-server kafka:9092

### producer-service
Есть две таблицы в БД postgres: users и publications, для них в MyTgController описаны CRUD-операции.
* GET
    * curl http://localhost:8080/users
    * curl http://localhost:8080/users/1
    * curl http://localhost:8080/publications
    * curl http://localhost:8080/publications/1
    
* POST
    * curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"nickname\": \"purr\"}" 
    * curl -X POST http://localhost:8080/publications -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"author_id\": 1, \"content\": \"mewmewmew!\"}"

* PUT / DELETE
    * curl -X PUT http://localhost:8080/users -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"id\": 1, \"nickname\": \"purr222\"}"
    * curl -X DELETE http://localhost:8080/users -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"id\": 1, \"nickname\": \"purr222\"}"
    * curl -X PUT http://localhost:8080/publications -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"id\": 1,\"author_id\": 1, \"content\": \"mewmewmew222!\"}"
    * curl -X DELETE http://localhost:8080/publications -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"id\": 1,\"author_id\": 1, \"content\": \"mewmewmew222!\"}"

### consumer-service
Настроен на чтение сообщений из топиков для таблиц бд, выводит их в свои логи.

## Информация для тренировочной части с Kafka:
### producer-service
Отправляет сообщения в топик с помощью http://localhost:8080/message/*текст*
Пример:
http://localhost:8080/message/mew!!!

### consumer-service
Считывает сообщения из топика и выводит их в логи
