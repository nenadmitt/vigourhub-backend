start-backend:
	cd backend-api && mvn spring-boot:run

start-main-db:
	cd deployment && docker-compose up main_db

deploy-services:
	cd deployment && docker-compose up