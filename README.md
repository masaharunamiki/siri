## java8 + springBoot1.5.12 + MySQL(or mariaDB in-memonry)

## install for MySQL
1. `CREATE DATABASE siri DEFAULT CHARACTER SET utf8;` in your MySQL SERVER.
2. git clone
3. gradle build
4. gradle configCopy
5. java -jar build/libs/siri.jar --spring.profiles.active=mysql
6. http://localhost:8080/siri

## install for mariaDB
1. git clone
2. gradle build
3. gradle configCopy
4. java -jar build/libs/siri.jar --spring.profiles.active=inmemory
5. http://localhost:8080/siri







