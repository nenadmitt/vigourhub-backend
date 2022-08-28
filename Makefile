start-backend:
	echo "starting" && mvn spring-boot:run

start-main-db:
	cd deployment && docker-compose up main_db