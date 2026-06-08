-include .env
export

.PHONY: run build test clean

run:
	.\mvnw spring-boot:run

compile:
	.\mvnw compile

package:
	.\mvnw package

clean:
	.\mvnw clean

test:
	.\mvnw test

build: clean package
