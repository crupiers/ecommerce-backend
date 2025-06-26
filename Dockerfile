#uso maven para buildear la app
from maven:latest

#creo el directorio dentro del contenedor
workdir /app

#copio los archivos necesarios para buildear la app
copy pom.xml .
copy src ./src

#buildeo la app con maven sin realizar los tests escritos
run mvn clean package -DskipTests

#expongo el puerto 8080 ya que mi app lo usa
expose 8080

#corro la app
entrypoint ["java", "-jar", "./target/ecommerce-crupiers-1.0.0-jar-with-dependencies.jar"]
