# TeaOrderService
Tea 주문 서비스

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



## PostgreSQL Settings
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


