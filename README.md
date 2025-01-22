![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)

# Weather Tracker
Реализация проекта №5 из [роадмапа](https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/) на Kotlin.


## Использованные технологии:
- **Spring**
- **Thymeleaf**
- **Hibernate**
- **MapStruct**
- **Gradle**
- **Tomcat**
- **Postgres**
- **Docker**

---

## Внешний вид
### Страница регистрации
![изображение](https://github.com/user-attachments/assets/e1ef54a2-6fdb-4a53-95d1-4e320b16ff12)

### Страница логина
![изображение](https://github.com/user-attachments/assets/3f4e2217-4af9-402d-8c2a-b1837b24402b)

### Главная страница
![изображение](https://github.com/user-attachments/assets/b6231c08-b9ff-4e51-953a-2d421020e808)

### Страница поиска локаций
![изображение](https://github.com/user-attachments/assets/6c53875c-e1b6-4066-a2b4-713f09023c26)

---

## Запуск

### Запуск через Docker

1. Выполнить команду:
   ```bash
   docker-compose -f docker-compose-dev.yml up -d
   ```

---

### Сборка проекта и запуск через Docker



---

### Запуск через Tomcat

1. Установить Tomcat версии 10+.

2. Получить ключ OpenWeatherApi:
   - Зарегистрироваться на сайте [OpenWeather](https://openweathermap.org/).
   - Создать ключ [здесь](https://home.openweathermap.org/api_keys).

3. Перейти в директорию `src/main/resources` и создать файл `application.properties` по шаблону из файла `application.properties.origin`:
   ```properties
   datasource.url=<ваш-путь-к-бд>
   datasource.username=<логин-от-бд>
   datasource.password=<пароль-от-бд>
   datasource.driver-class-name=org.postgresql.Driver
   jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   jpa.hibernate.ddl-auto=none
   hibernate.current_session_context_class=org.springframework.orm.hibernate5.SpringSessionContext
   weather.api.key=<ключ-OpenWeatherApi>
   ```

4. Запуск:
   - **Через IDEA**:
     1. Выбрать `war` артефакт в конфигурации Tomcat.

     2. Запустить Tomcat.

   - **На удалённом сервере**:
     1. Собрать `war` файл командой:
        ```bash
        gradle clean war
        ```
   
     2. Переместить `war` файл в директорию `/tomcat/webapps`.

     3. Перейти в директорию `/tomcat/bin` и выполнить:
        ```bash
        ./startup.sh
        ```
