FROM amazoncorretto:17.0.7-alpine
# Author information
LABEL authors="quangdangcodegym"

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built from the Spring Boot application into the working directory inside the container
COPY target/SPB_EyesClinic_Project-0.0.1-SNAPSHOT.war /app/SPB_EyesClinic_Project-0.0.1-SNAPSHOT.war

# Expose the port used by the application
EXPOSE 8080

# Specify the command to run the application when the container starts
CMD ["java", "-jar", "SPB_EyesClinic_Project-0.0.1-SNAPSHOT.war"]