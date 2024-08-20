# **Task Manager**
## **Описание**
**Task manager** представляет собой бэкенд-сервис, разработанный для эффективного управления и распределения задач. Он предлагает широкий спектр функциональности, включая возможность создания новых задач, назначения ответственных за их выполнение и добавления комментариев к уже существующим записям. Этот инструмент идеально подходит для команд, стремящихся оптимизировать рабочие процессы и повысить продуктивность.
# Структура проекта Task Manager
В проекте используется гексагональная архитектура.
```
Interview
└── taskmanager
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
    │   │   │               │       │   │── AuthenticationAndRegistrationService. java
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
    └── 
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

### Аутентификация
  - Запрос: JSON объект `SignInRequest` с учетными данными пользователя.
  - Ответ: Заголовок `Authorization` с JWT токеном при успешной аутентификации.
  - Если аутентификация не удалась, возвращается `HTTP 401 Unauthorized`.

## **API**
API Controller в Task Manager предоставляет набор конечных точек для взаимодействия с задачами, исполнителями и комментариями.
` Важно! Для доступа к API нужно вставить JWT токен в header поле Authorization.`

### Swagger UI
Документация находится по адрессу: http://localhost:8080/swagger-ui/index.html

## Docker-compose
```YML
version: '2.29.1'
services:
  database:
    restart: on-failure
    container_name: postgresql
    image: "postgres:16"
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: taskmanagerdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - database_data:/var/lib/postgresql/data

  pgadmin:
    restart: always
    container_name: pgadmin
    image: elestio/pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@email.com
      PGADMIN_DEFAULT_PASSWORD: admin
      PGADMIN_LISTEN_PORT: 5050
    ports:
      - "5050:5050"

  redis:
    restart: on-failure
    container_name: redis
    image: "redis:7.4.0"
    ports:
      - "6379:6379"
  
  taskmanager:
    restart: on-failure
    container_name: taskmanager
    build: .
    ports:
      - "8080:8080"
    environment:
      POSTGRES_HOST: database
      POSTGRES_PORT: 5432
      REDIS_HOST: redis
      REDIS_PORT: 6379
    depends_on:
      - database
      - redis
  
volumes:
  database_data:
```
