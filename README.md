# PTMK-TestTask

## Запуск:

### Собираем jar-файл:
    mvn clean package

### Выбор режим:
    java -jar target/demo-0.0.1-SNAPSHOT.jar <mode> [additional parameters]

## Доступные режимы:

### Создание таблицы:

    java -jar target/demo-0.0.1-SNAPSHOT.jar 1

### Вставка одной записи:

    java -jar target/demo-0.0.1-SNAPSHOT.jar 2 "Ivanov Petr Sergeevich" 2009-07-12 Male

### Вывод соотрудников, отсортированных по ФИО:

    java -jar target/demo-0.0.1-SNAPSHOT.jar 3

### Генерация 1000100 соотрудников:

    java -jar target/demo-0.0.1-SNAPSHOT.jar 4

### Сортировка по критериям: пол мужской, фамилия с F:

    java -jar target/demo-0.0.1-SNAPSHOT.jar 5 Male F

### Удаление всех данных с таблицы:

    java -jar target/demo-0.0.1-SNAPSHOT.jar 6


