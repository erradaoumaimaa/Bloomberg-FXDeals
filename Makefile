.PHONY: build run clean test docker-build docker-run docker-stop

APP_NAME=fx-deals-service

build:
	@echo "Building the application..."
	./mvnw clean package

run: build
	@echo "Running the application..."
	./mvnw spring-boot:run

test:
	@echo "Running tests..."
	./mvnw test

clean:
	@echo "Cleaning build artifacts..."
	./mvnw clean

docker-build:
	@echo "Building Docker image..."
	docker-compose build

docker-run:
	@echo "Starting Docker containers..."
	docker-compose up -d

docker-stop:
	@echo "Stopping Docker containers..."
	docker-compose down


setup: docker-build docker-run
	@echo "Setup complete!"

full-clean: docker-stop clean
	@echo "Full cleanup complete!"

help:
	@echo "Available commands:"
	@echo "  make build          - Build the application"
	@echo "  make run            - Run the application locally"
	@echo "  make test           - Run the tests"
	@echo "  make clean          - Clean build artifacts"
	@echo "  make docker-build   - Build Docker image"
	@echo "  make docker-run     - Run the application in Docker"
	@echo "  make docker-stop    - Stop Docker containers"
	@echo "  make setup          - Set up and start the application in Docker"
	@echo "  make full-clean     - Stop Docker containers and clean artifacts"