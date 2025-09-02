# 사용할 기본 이미지 (Java 17 버전)
FROM openjdk:17-jdk-slim

# 이 Dockerfile을 통해 생성될 컨테이너에 애플리케이션을 실행할 유저를 생성하고 그 유저로 전환
RUN useradd --create-home --shell /bin/false springapp
USER springapp

# 애플리케이션의 JAR 파일을 컨테이너 내부로 복사
# 프로젝트를 Maven 또는 Gradle로 빌드하여 생성된 JAR 파일이 필요합니다.
# 예를 들어, target/my-spring-app-0.0.1-SNAPSHOT.jar 파일이라고 가정합니다.
# docker build 명령을 실행할 때 아래의 경로가 올바른지 확인해주세요.
COPY build/libs/customs-bff-0.0.1-SNAPSHOT.jar /home/springapp/app.jar

# 애플리케이션이 사용할 포트 노출
EXPOSE 8080


# 5005번 포트를 디버깅 포트로 설정하고 외부에 노출합니다.
# "suspend=n"은 JVM이 디버거가 연결되기를 기다리지 않고 바로 시작하게 합니다.
# "address=*:5005"는 모든 네트워크 인터페이스에서 연결을 허용합니다.
EXPOSE 5005

ENTRYPOINT ["java", "-Xdebug", "-Xrunjdwp:server=y,transport=dt_socket,address=*:5005,suspend=n", "-jar", "/home/springapp/app.jar"]


# 컨테이너가 시작될 때 실행할 명령어
# 'java -jar' 명령어로 JAR 파일을 실행합니다.
ENTRYPOINT ["java", "-jar", "/home/springapp/app.jar"]