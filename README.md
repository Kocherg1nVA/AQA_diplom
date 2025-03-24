[![Java CI with Gradle](https://github.com/Kocherg1nVA/AQA_diplom/actions/workflows/gradle.yml/badge.svg)](https://github.com/Kocherg1nVA/AQA_diplom/actions/workflows/gradle.yml)

**Запуск процедуры автотестирования веб-приложения "Путешествие дня"**

1. Склонировать в локальный репозиторий [Дипломный проект](https://github.com/Kocherg1nVA/AQA_diplom "Дипломный проект Кочергина В.А.") с помощью терминала командой:

        git clone https://github.com/Kocherg1nVA/AQA_diplom.git

2. Запустить Docker Desktop

3. Открыть дипломный проект в IntelliJ IDEA

4. Запустить контейнеры в Docker с помощью терминала командой:

        docker compose up

5. Запустить SUT с помощью терминала командой:

        для postgresgl:
        java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

        для mySQL: 
        java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

6. Запустить автотесты с помощью терминала командой:

        для postgresgl: 
        ./gradlew clean test -DurlDB="jdbc:postgresql://localhost:5432/app"
        
        для mySQL:
        ./gradlew clean test -DurlDB="jdbc:mysql://localhost:3306/app"

7. Создать Allure отчет и открыть его в браузере по умолчанию с помощью терминала командой:

        ./gradlew allureServe

8. Завершить работу AllureServe с помощью терминала комбинацией клавиш:

        Ctrl+C

9. Завершить работу SUT с помощью терминала комбинацией клавиш:

        Ctrl+C

10. Завершить работу Docker контейнеров с помощью терминала комбинацией клавиш:

        Ctrl+C

11. Удалить все контейнеры и внутренние сети Docker с помощью терминала командой:

        docker compose down

