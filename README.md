# **Task Manager**
## **Описание**
**Task manager** представляет собой мощный бэкенд-сервис, разработанный для эффективного управления и распределения задач. Он предлагает широкий спектр функциональности, включая возможность создания новых задач, назначения ответственных за их выполнение и добавления комментариев к уже существующим записям. Этот инструмент идеально подходит для команд, стремящихся оптимизировать рабочие процессы и повысить продуктивность.
# Структура проекта Task Manager
```
Interview
│── .vscode
│   └── settings.json
│── readme.md
└── taskmanager
    │── .gitignore
    │── .mvn
    │   └── wrapper
    │       └── maven-wrapper.properties
    │── .vscode
    │   └── NEWLY_CREATED_BY_SPRING_INITIALIZR
    │── docker-compose.yml
    │── Dockerfile
    │── HELP.md
    │── mvnw
    │── mvnw.cmd
    │── pom.xml
    │── src
    │   │── main
    │   │   │── java
    │   │   │   └── com
    │   │   │       └── interview
    │   │   │           └── taskmanager
    │   │   │               │── adapters
    │   │   │               │   └── database
    │   │   │               │       │── CommentRepositoryAdapter.java
    │   │   │               │       │── models
    │   │   │               │       │   │── Comment.java
    │   │   │               │       │   │── statuses
    │   │   │               │       │   │   │── TaskPriority.java
    │   │   │               │       │   │   └── TaskStatus.java
    │   │   │               │       │   │── Task.java
    │   │   │               │       │   └── User.java
    │   │   │               │       │── repositories
    │   │   │               │       │   │── CommentRepository.java
    │   │   │               │       │   │── jpa //Реализация CRUD интрефейсов JPA
    │   │   │               │       │   │   │── CommentCrudJpaOperation.java
    │   │   │               │       │   │   │── TaskCrudJpaOperation.java
    │   │   │               │       │   │   └── UserCrudJpaOperation.java
    │   │   │               │       │   │── TaskRepository.java
    │   │   │               │       │   └── UserRepository.java
    │   │   │               │       │── TaskRepositoryAdapter.java
    │   │   │               │       └── UserRepositoryAdapter.java
    │   │   │               │── common
    │   │   │               │   └── dto
    │   │   │               │       │── comment
    │   │   │               │       │   └── CommentDto.java
    │   │   │               │       │── CommentDetails.java
    │   │   │               │       │── profile
    │   │   │               │       │   │── ExecutedTaskDto.java
    │   │   │               │       │   │── ExecutedTaskDtoMapper.java
    │   │   │               │       │   │── ExecutorDto.java
    │   │   │               │       │   │── OwnerTaskDto.java
    │   │   │               │       │   │── OwnerTaskDtoMapper.java
    │   │   │               │       │   └── UserProfile.java
    │   │   │               │       └── task
    │   │   │               │           │── AuthorDto.java
    │   │   │               │           │── TaskBriefInfoDto.java
    │   │   │               │           │── TaskBriefInfoDtoMapper.java
    │   │   │               │           │── TaskDetails.java
    │   │   │               │           │── TaskDetailsDtoMapper.java
    │   │   │               │           └── TaskDto.java
    │   │   │               │── controllers
    │   │   │               │   │── ApiController.java
    │   │   │               │   └── AuthController.java
    │   │   │               │── domain
    │   │   │               │   └── services
    │   │   │               │       └── task
    │   │   │               │           │── TaskManagementService.java
    │   │   │               │           └── TaskOperation.java
    │   │   │               │── infra
    │   │   │               │   │── CacheConfig.java
    │   │   │               │   └── security
    │   │   │               │       │── authenticated
    │   │   │               │       │   │── AuthenticatedUserDetails.java
    │   │   │               │       │   │── AuthenticatedUserDetailsMapper.java
    │   │   │               │       │   │── AuthenticationAndRegistrationService.java
    │   │   │               │       │   └── AuthenticationService.java
    │   │   │               │       │── dto
    │   │   │               │       │   │── SignInRequest.java
    │   │   │               │       │   └── SignUpRequest.java
    │   │   │               │       │── jwt
    │   │   │               │       │   │── JwtFilter.java
    │   │   │               │       │   │── JwtTokenOperation.java
    │   │   │               │       │   └── JwtTokenService.java
    │   │   │               │       │── Role.java
    │   │   │               │       │── SecurityConfig.java
    │   │   │               │       └── UserDetailsProvider.java
    │   │   │               └── TaskManagerApplication.java
    │   │   └── resources
    │   │       │── application-test.properties
    │   │       │── application.properties
    │   │       │── META-INF
    │   │       │── static
    │   │       └── templates
    │   └── test
    │       └── java
    │           └── com
    │               └── interview
    │                   └── taskmanager
    │                       │── TaskManagementServiceTest.java
    │                       └── TaskManagerApplicationTests.java
    └── target
        │── classes
        │   │── application-test.properties
        │   │── application.properties
        │   └── com
        │       └── interview
        │           └── taskmanager
        │               │── adapters
        │               │   └── database
        │               │       │── CommentRepositoryAdapter.class
        │               │       │── models
        │               │       │   │── Comment.class
        │               │       │   │── statuses
        │               │       │   │   │── TaskPriority.class
        │               │       │   │   └── TaskStatus.class
        │               │       │   │── Task.class
        │               │       │   └── User.class
        │               │       │── repositories
        │               │       │   │── CommentRepository.class
        │               │       │   │── jpa
        │               │       │   │   │── CommentCrudJpaOperation.class
        │               │       │   │   │── TaskCrudJpaOperation.class
        │               │       │   │   └── UserCrudJpaOperation.class
        │               │       │   │── TaskRepository.class
        │               │       │   └── UserRepository.class
        │               │       │── TaskRepositoryAdapter.class
        │               │       └── UserRepositoryAdapter.class
        │               │── common
        │               │   └── dto
        │               │       │── comment
        │               │       │   │── CommentDto$Builder.class
        │               │       │   └── CommentDto.class
        │               │       │── CommentDetails.class
        │               │       │── profile
        │               │       │   │── ExecutedTaskDto.class
        │               │       │   │── ExecutedTaskDtoMapper.class
        │               │       │   │── ExecutorDto.class
        │               │       │   │── OwnerTaskDto.class
        │               │       │   │── OwnerTaskDtoMapper.class
        │               │       │   └── UserProfile.class
        │               │       └── task
        │               │           │── AuthorDto.class
        │               │           │── TaskBriefInfoDto.class
        │               │           │── TaskBriefInfoDtoMapper.class
        │               │           │── TaskDetails.class
        │               │           │── TaskDetailsDtoMapper.class
        │               │           │── TaskDto$Builder.class
        │               │           └── TaskDto.class
        │               │── controllers
        │               │   │── ApiController.class
        │               │   └── AuthController.class
        │               │── domain
        │               │   └── services
        │               │       └── task
        │               │           │── TaskManagementService.class
        │               │           └── TaskOperation.class
        │               │── infra
        │               │   │── CacheConfig$1.class
        │               │   │── CacheConfig.class
        │               │   └── security
        │               │       │── authenticated
        │               │       │   │── AuthenticatedUserDetails.class
        │               │       │   │── AuthenticatedUserDetailsMapper.class
        │               │       │   │── AuthenticationAndRegistrationService.class
        │               │       │   └── AuthenticationService.class
        │               │       │── dto
        │               │       │   │── SignInRequest.class
        │               │       │   └── SignUpRequest.class
        │               │       │── jwt
        │               │       │   │── JwtFilter.class
        │               │       │   │── JwtTokenOperation.class
        │               │       │   └── JwtTokenService.class
        │               │       │── Role.class
        │               │       │── SecurityConfig.class
        │               │       └── UserDetailsProvider.class
        │               └── TaskManagerApplication.class
        └── test-classes
            └── com
                └── interview
                    └── taskmanager
                        │── TaskManagementServiceTest.class
                        └── TaskManagerApplicationTests.class
```
# *Описание работы программы*
## **Авторизация**
### Регистрация
  - Запрос: JSON объект `SignUpRequest` с информацией о пользователе.
  - Ответ: `HTTP 200 OK` после успешной регистрации пользователя.
#### `SignUpRequest`
``` JSON
    {
        "username": "имя пользователя",
        "email": "почта",
        "password": "пароль" 
    }
```

### Аутентификация

  - Запрос: JSON объект `SignInRequest` с учетными данными пользователя.
  - Ответ: Заголовок `Authorization` с JWT токеном при успешной аутентификации.
  - Если аутентификация не удалась, возвращается `HTTP 401 Unauthorized`.
#### `SignInRequest`
```Json
    {
        "email": "почта",
        "password": "пароль"
    }
```
## **API**
API Controller в Task Manager предоставляет набор конечных точек для взаимодействия с задачами, исполнителями и комментариями. Этот документ описывает доступные эндпоинты и их использование. 
` Важно! Для доступа к API нужно вставить JWT токен в header поле Authorization.`


## Эндпоинты

### Задачи

- **Создание задачи**
  - `PUT /api/task/create`
  - Запрос: JSON объект `TaskDetails` с детальной информацией о задаче.
  - Ответ: `HTTP 200 OK` при успешном создании задачи.

- **Обновление задачи**
  - `PATCH /api/task/update`
  - Запрос: ID задачи и JSON объект `TaskDetails` с новыми данными.
  - Ответ: `HTTP 200 OK` при успешном обновлении задачи.
    

- **Удаление задачи**
  - `DELETE /api/task/delete`
  - Запрос: ID задачи.
  - Ответ: `HTTP 200 OK` при успешном удалении задачи.
#### `TaskDetails`
```JSON
        {
            "title" : "Название задачи",
            "description" : "Описание задачи",
            Выбрать один вариант из списка
            "status" : "HIGHT,MIDDLE,LOWER",
            "priority" : "WAITING,PROCESS,COMPLETE"

        }
```

- **Добавление исполнителя к задаче**
  - `PUT /api/task/executor/add`
  - Запрос: Параметры `taskId` = id задачи, `executorId` = id исполнителя.
  - Ответ: `HTTP 200 OK` при успешном добавлении исполнителя к задаче.

- **Удаление исполнителя из задачи**
  - `DELETE /api/task/executor/delete`
  - Запрос: Параметры `taskId` = id задачи, `executorId` = id исполнителя.
  - Ответ: `HTTP 200 OK` при успешном удалении исполнителя из задачи.

- **Поиск задач по названию**
  - `GET /api/task/searchByTitle`
  - Запрос: Параметры `title` = заголовок задачи, `page` = номер страницы
  - Ответ: Список задач, соответствующих критериям поиска.

- **Получение списка задач пользователя**
  - `GET /api/task/assigned`
  - Запрос: Не требуется.
  - Ответ: Список задач, назначенных пользователю.

### Комментарии

- **Создание комментария**
  - `PUT /api/comment/create`
  - Запрос: JSON объект `CommentDetails` с информацией о комментарии.
  - Ответ: `HTTP 200 OK` при успешном создании комментария.

- **Обновление комментария**
  - `PATCH /api/comment/update`
  - Запрос: Параметр `commentId` = id комментария и JSON объект `CommentDetails` с новыми данными.
  - Ответ: `HTTP 200 OK` при успешном обновлении комментария.

- **Удаление комментария**
  - `DELETE /api/comment/delete`
  - Запрос: Параметр `commentId` = id.
  - Ответ: `HTTP 200 OK` при успешном удалении комментария.

- **Получение комментариев по ID задачи**
  - `GET /api/comment/getComments`
  - Запрос: Параметры `taskId` = ID задачи и `page` = номер страницы.
  - Ответ: Список комментариев, связанных с задачей.
#### `CommentDetails`
```JSON
    {
        "taskId" : <id задачи> ,
        "content" : "Контент"
    }
 ```

### Профиль пользователя

- **Получение профиля пользователя**
  - `GET /api/user/{param}`
  - Запрос: ID или имя пользователя.
  - Ответ: Профиль пользователя.

## Обработка исключений

- **Найдено нет результатов**
  - `HTTP 404 Not Found` при отсутствии результата запроса.

- **Доступ запрещен**
  - `HTTP 403 Forbidden` при попытке доступа без необходимых прав.
