-include .env
export

.PHONY: run build test clean

docker-test:
	docker compose -f docker-compose.local.yml up --build 

docker-clean:
	docker compose down -v

docker-db:
	docker exec -it mariadb-propiedad mariadb -u propiedad_test_user -p${DB_PASSWORD_LOCAL}

run:
	./mvnw spring-boot:run

compile:
	./mvnw compile

package:
	./mvnw package

clean:
	./mvnw clean

test:
	./mvnw test

build: clean package
