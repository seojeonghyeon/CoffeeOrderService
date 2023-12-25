# CoffeeOrderService
Coffee 주문 서비스

## 구현 기능
1. 커피 메뉴 목록 조회 API(조건 검색 기능)
2. 포인트 충전하기 API
3. 커피 주문, 결제하기 API
4. 인기메뉴 목록 조회 API

## Swagger
http://localhost:8080/swagger-ui/index.html
![스크린샷 2023-11-24 00 01 13](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/2d4cc326-8083-4860-9b78-00e04705f1b2)


## Entity 기반 ERD
![스크린샷 2023-12-04 23 51 21](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/b13e0225-d584-4008-a8d0-33c0483a4d86)


## ERD
![스크린샷 2023-12-04 23 44 39](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/4e970229-8a8d-46ab-b516-de32788dd917)


## Spring Boot 요청, 응답 간 프로세스
<img width="1644" alt="image" src="https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/78d305ac-0a11-49be-b695-afe215238f75">

Connector : 클라이언트로부터의 요청을 받아들이고 서블릿 컨테이너로 전달, 웹 서버와 Tomcat 간의 통신을 담당
Servlet Container : Connector로부터 전달받은 요청을 처리하는 부분, 서블릿의 생명주기를 관리하고, 요청에 따라 적절한 서블릿을 호출하여 응답을 생성

Dispatcher Servlet
 - 요청 분배 역할, Dispatching
    HTTP 요청에 대해 적절한 Handler에게 전달, URL 매핑, 핸들러 매핑 등을 사용하여 어떤 컨트롤러가 해당 요청을 처리할 지 결정한다.
    1. Spring MVC에서 제공하는 Controller Annocation(@Controller -> Retention Policy : Runtime)이 Component Scan의 대상이 되어 Spring IoC Container 내 Bean으로 등록됨.
    2. Dispatcher Servlet은 Spring IoC Container에 등록된 Bean 중에 @Controller이 부여된 Class를 찾아서 컨트롤러로 인식한다.
    3. Controller Method에 지정된 @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 정보를 읽어온다.
    4. URL 매핑 정보(클래스수준과 메소드 수준의 Annotation을 조합하여 최종 URL 정보가 결정)를 읽어와 URL 매핑에 따른 Controller의 Method를 호출하여 처리한다.
- View 결정 및 Rendering
    controller가 처리한 결과를 기반으로 어떤 View를 사용할 지 결정한다. View는 JSP, Thymeleaf와 같은 템플릿 엔진이나 JSON, XML와 같은 형식으로 구성될 수 있다. 결정된 View는 Client에 보내질 최종 응답을 생성한다.
    1. model.addAttribute 메소드를 통해 전달된 값을 Dispatcher Servlet은 뷰 템블릿에 전달
    2. 뷰 템플릿이 html 파일에 값이 추가되어 최종적인 HTML 응답이 생성한다.(View Rendering)
    3. 다시 Dispatcher Servlet에 전달된 Rendering된 결과 값을  Client에게 반환된다.
    OSIV를 키게 되면 Dispatcher Servlet이 요청을 처리하는 동안에만 Database Session이 유지되는 것으로 뷰 템플릿이 뷰 렌더링 완료 후 Dispatcher Servlet에서 Rendering된 결과를 Client에게 전달하면 그 이후에 Database Session을 닫는다.

- Intercepter 지원
   Handler Intercepter를 사용하여 요청 처리 전후에 추가적인 로직을 수행할 수 있다.

preHandle : Handler Adapter 호출 전 호출, 응답값이 true이면 Handler Adapter 호출하고 false이면 나머지 Intercepter와 Handler Adapter를 호출하지 않는다.
postHandle : Handler Adapter 호출 후 호출(Controller에서 Exception 발생 시, 호출되지 않는다.)
afterCompletion : View Rendering 된 이후 호출, Exception이 발생해도 호출되며, 예외의 Parameter(ex)로 받아 어떤 예외가 발생했는지 로그로 출력이 가능하다.



## PostgreSQL Settings
PostgreSQL 기준 Default Connection pool은 100
각 Pod의 maximum-pool-size 합이 100을 넘지 않도록 해야 함.(또는 Connection pool을 늘려주거나..)
```
 zayden@Justin-MacBook-Pro  ~  docker pull postgres:latest
 zayden@Justin-MacBook-Pro  ~  docker run -p 5432:5432 --name local-postgres \
> -e POSTGRES_PASSWORD=00000000 \
> -e TZ=Asia/Seoul \
> -v /Users/zayden/Documents/Work/postgres/data:/var/lib/postgresql/data \
> -d postgres:latest
647a65814db1334f07844b4313b62e51e8522c53b70044a061693f8f8f75e1a2

```

Database 생성
```
 zayden@Justin-MacBook-Pro  ~  docker exec -it 647a bash
root@647a65814db1:/# psql -U postgres

postgres=# create database teaorderdb;
CREATE DATABASE


   Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | ICU Locale | ICU Rules |   Access privileges

------------+----------+----------+-----------------+------------+------------+------------+-----------+---------------------
--
 postgres   | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
 teaorderdb | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
 template0  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres
 +
            |          |          |                 |            |            |            |           | postgres=CTc/postgre
s
 template1  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres
 +
            |          |          |                 |            |            |            |           | postgres=CTc/postgre
s
```

API 계정 생성 및 권한 부여 
```
postgres=# create role api_user with login password '00000000';
CREATE ROLE
postgres=# alter user api_user with createdb;
ALTER ROLE
postgres=# alter user api_user with superuser;
ALTER ROLE
postgres=# grant all privileges on database teaorderdb to api_user;
GRANT
```

DB Table 조회
```
teaorderdb=# \dt
                List of relations
 Schema |         Name         | Type  |  Owner
--------+----------------------+-------+----------
 public | authority            | table | api_user
 public | category             | table | api_user
 public | member_authorities   | table | api_user
 public | members              | table | api_user
 public | menu_category        | table | api_user
 public | menus                | table | api_user
 public | orders               | table | api_user
 public | points               | table | api_user
 public | product_order        | table | api_user
 public | product_order_counts | table | api_user
(10 rows)
```

## Email 연동
1. Google 계정에서 보안 탭 선택(https://myaccount.google.com/)
<img width="843" alt="스크린샷 2023-12-23 19 04 36" src="https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/56437392-6855-465a-9045-47bda01f2387">

2. 보안 탭에서 2단계 인증 선택
<img width="848" alt="스크린샷 2023-12-23 19 05 30" src="https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/f1a6d2cd-bdf0-4497-80d9-4b7902037d80">

3. 2단계 인증에서 최하단에 앱 비밀번호 선택
<img width="848" alt="스크린샷 2023-12-23 19 06 08" src="https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/7a656458-2e8f-4d86-b68d-b019c1e398ee">

4. App Password 생성 후 Copy
<img width="850" alt="스크린샷 2023-12-23 19 07 03" src="https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/9188dd48-6d37-4609-b142-dce6615db0f3">

5. application-api-local.yml 내 spring.mail.username과 password 부분에 업데이트(GitHub에 업데이트 한 password는 유효하지 않음)
<img width="821" alt="스크린샷 2023-12-23 19 08 34" src="https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/4ef95a1d-0113-46f8-a7a4-e0e1f53cfcfa">


## How to run

```
zayden@Justin-MacBook-Pro  ~/Documents/workspace/TeaOrderService   main ±  mvn clean compile package -DskipTests=true

zayden@Justin-MacBook-Pro  ~/Documents/workspace/TeaOrderService   main ±  docker build --tag seojeonghyeon0630/teaorderservice:0.0.1 .
[+] Building 5.4s (8/8) FINISHED
 => [internal] load build definition from Dockerfile                                                                                                                                                                       0.1s
 => => transferring dockerfile: 189B                                                                                                                                                                                       0.0s
 => [internal] load .dockerignore                                                                                                                                                                                          0.0s
 => => transferring context: 2B                                                                                                                                                                                            0.0s
 => [internal] load metadata for docker.io/library/openjdk:17-ea-11-slim                                                                                                                                                   2.9s
 => [auth] library/openjdk:pull token for registry-1.docker.io                                                                                                                                                             0.0s
 => CACHED [1/2] FROM docker.io/library/openjdk:17-ea-11-slim@sha256:58a00fe1968ae4d6a412daee3bc013898f33842a550ad5c5f776b89a86906be0                                                                                      0.0s
 => [internal] load build context                                                                                                                                                                                          1.1s
 => => transferring context: 94.80MB                                                                                                                                                                                       1.1s
 => [2/2] COPY target/TeaOrderService-0.0.1.jar TeaOrderService.jar                                                                                                                                                        0.7s
 => exporting to image                                                                                                                                                                                                     0.5s
 => => exporting layers                                                                                                                                                                                                    0.4s
 => => writing image sha256:0347491d682d030938866e9ef52db751159451294c3253409736df4e42be48a5                                                                                                                               0.0s
 => => naming to docker.io/seojeonghyeon0630/teaorderservice:0.0.1                                                                                                                                                         0.0s

Use 'docker scan' to run Snyk tests against images to find vulnerabilities and learn how to fix them

 zayden@Justin-MacBook-Pro  ~/Documents/workspace/TeaOrderService   main ±  docker push seojeonghyeon0630/teaorderservice:0.0.1
The push refers to repository [docker.io/seojeonghyeon0630/teaorderservice]
e38384c4f62e: Pushed
3d3fdb9815af: Mounted from seojeonghyeon0630/filebeatdemo
08664b16f94c: Mounted from seojeonghyeon0630/filebeatdemo
9eb82f04c782: Mounted from seojeonghyeon0630/filebeatdemo
0.0.1: digest: sha256:2dc12eee0c11b854461c06a1562b6e71415b8dbdc7fa2b2c6d0cde1162af0806 size: 1165

```


