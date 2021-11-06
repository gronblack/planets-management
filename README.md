# Управление планетами: тестовое задание на стажировку.
Spring-Boot, Spring Data JPA, Jackson, JUnit, H2.

### Описание
3141 год.
Вселенная исследована и поделена.
Верховный правитель назначает Повелителей Планет, общее количество которых исчисляется миллионами.
Опытные Повелители могут одновременно управлять несколькими Планетами. Никакой демократии, поэтому одной планетой может править только один Повелитель.
Все это безобразие требует системы учета и надзора.

Задание:
Разработать Spring Boot приложение на Java.
Приложение должно иметь API и работать с реляционной БД. Для простоты отладки это может быть in-memory БД, например HSQLDB или иная.

Базовые характеристики сущностей.
Повелитель: Имя и Возраст
Планета: Название
Один Повелитель может управлять несколькими Планетами, но одной Планетой может править только один Повелитель.

Необходимо разработать структуру таблиц для хранения Повелителей и Планет и связь между ними.

Поддержать методы API:
- Добавить нового Повелителя
- Добавить новую Планету
- Назначить Повелителя управлять Планетой
- Уничтожить Планету
- Найти всех Повелителей бездельников, которые прохлаждаются и не управляют никакими Планетами
- Отобразить ТОП 10 самых молодых Повелителей

Написать тесты для этого функционала

Код расположить в GitHub.

Дополнительно (будет большим плюсом, но не обязательно):
- Создать примитивный web интерфейс, в котором будут работать все методы API (красота, дизайн, usability оцениваться НЕ будут).
- Написать тест на Selenium, который будет проверять работу интерфейса.

### curl (For windows use [Git Bash](https://git-scm.com/download)
#### get all Lords (default page size = 10, page number = 0, order by name)
`curl -s http://localhost:8080/api/lords`

#### get Lords with page parameters
`curl -s "http://localhost:8080/api/lords?pageSize=5&pageNumber=1&order=id"`

#### get Lord with id=5
`curl -s http://localhost:8080/api/lords/5`

#### get idle Lords
`curl -s http://localhost:8080/api/lords/idle`

#### get top 10 young Lords
`curl -s http://localhost:8080/api/lords/young`

#### delete Lord with id=5
`curl -s -X DELETE http://localhost:8080/api/lords/5`

#### create new Lord
`curl -s -X POST -d '{"name": "New Lord", "age": 30, "height": 180}' -H 'Content-Type:application/json' http://localhost:8080/api/lords`

#### update Lord with id=1
`curl -s -X PUT -d '{"name": "Updated Lord", "age": 30, "height": 180}' -H 'Content-Type: application/json' http://localhost:8080/api/lords/1`

#### get all Planets (default page size = 10, page number = 0, order by name)
`curl -s http://localhost:8080/api/planets`

#### get Planets with page parameters
`curl -s "http://localhost:8080/api/planets?pageSize=5&pageNumber=1&order=id"`

#### get Planet with id=5
`curl -s http://localhost:8080/api/planets/5`

#### delete Planet with id=5
`curl -s -X DELETE http://localhost:8080/api/planets/5`

#### create new Planet
`curl -s -X POST -d '{"name": "New Planet", "radius": 30856}' -H 'Content-Type:application/json' http://localhost:8080/api/planets`

#### update Planet with id=1
`curl -s -X PUT -d '{"name": "Updated Planet", "radius": 9999}' -H 'Content-Type: application/json' http://localhost:8080/api/planets/1`

#### set Lord with id=10 for Planet with id=14
`curl -s -X PATCH http://localhost:8080/api/planets/14?lordId=10`