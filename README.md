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


## 객체 다이어그램
![스크린샷 2023-11-23 23 50 39](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/9a9451a4-5267-429c-ba48-3038e1f59146)


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
