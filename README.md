# Community API Service

## 프로젝트 개요
Spring Boot, JPA, JWT, Docker, PostgreSQL, Swagger를 활용한 게시글 API 서비스입니다.
- 게시글 CRUD (목록, 단일 조회, 작성, 수정, 삭제)
- JWT 기반 인증/인가 구조 (확장 가능)
- DDD(도메인 주도 설계) 기반 폴더 구조
- **요청/응답 DTO 분리로 도메인 모델과 API 계층 분리**
- Swagger UI로 API 테스트 지원
- Docker Compose로 PostgreSQL 연동

### [시퀀스 다이어그램](src/main/java/com/pre/community/docs/sequence.md)

## 기술 스택
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- PostgreSQL (Docker)
- jjwt (JWT)
- Swagger (springdoc-openapi)
- Gradle

---

## 폴더 구조 (DDD 기반)
```
community/
  └─ src/
      ├─ main/java/com/pre/community/
      │   ├─ board/
      │   │   ├─ domain/        # 엔티티
      │   │   ├─ repository/    # 리포지토리
      │   │   ├─ service/       # 서비스
      │   │   ├─ controller/    # 컨트롤러
      │   │   └─ dto/           # 요청/응답 DTO (API 계층)
      │   ├─ common/jwt/        # JWT 유틸리티
      │   └─ config/            # 시큐리티 등 설정
      └─ test/java/com/pre/community/
          └─ board/service/     # BoardService 단위 테스트
```

---

## DTO 분리 및 적용
- **엔티티(Board)와 API 요청/응답 DTO(BoardCreateRequest 등)를 분리**하여, 도메인 모델이 외부와 직접 통신하지 않도록 설계
- 예시: 게시글 생성(POST) 시 id 필드가 Swagger에 노출되지 않음 → 잘못된 id 입력 방지 및 JPA 오류 예방
- Controller에서 DTO를 받아 도메인 객체로 변환하여 서비스 계층에 전달
- 응답용 DTO(BoardResponse 등)도 확장 가능

---

## 실행 방법
### 1. Docker Desktop 실행
- Docker Desktop을 반드시 켜주세요.

### 2. PostgreSQL 컨테이너 실행 (docker-compose)
```bash
docker-compose up -d
```

### 3. Spring Boot 실행
```bash
./gradlew bootRun
```
- 또는 IDE에서 `CommunityApplication` 실행

### 4. Swagger UI 접속
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- API 테스트 가능

---

## DB 접속 정보 (Docker)
- Host: `localhost`
- Port: `5432`
- DB: `postgres`
- User: `postgres`
- Password: `h4702@@qq`

### DBeaver/pgAdmin 등으로 접속 가능

---

## 주요 설정 파일
- `docker-compose.yml` : PostgreSQL 컨테이너 설정
- `src/main/resources/application.properties` : DB, JPA, Swagger 등 설정
- `build.gradle` : 의존성, docker-compose 플러그인, springdoc 등

---

## 단위 테스트
```bash
./gradlew test
```
- `BoardService`의 CRUD 단위 테스트 포함

---

## 자주 발생하는 문제 & 해결법

### 1. DB 연결 오류 (UnknownHostException: db)
- 로컬에서 실행 시 `application.properties`의 DB 호스트를 `localhost`로 변경
- Docker Compose 네트워크에서만 `db` 사용

### 2. Swagger에서 403 Forbidden
- SecurityConfig에서 `.csrf(csrf -> csrf.disable())` 추가
- 또는 `.anyRequest().permitAll()`로 임시 허용

### 3. SpringApplication cannot be resolved
- `import org.springframework.boot.SpringApplication;` 누락 여부 확인
- `build.gradle`에 spring-boot-starter 의존성 추가

### 4. Row was updated or deleted by another transaction
- POST 요청 시 id를 보내지 않거나 null/0으로 보낼 것
- PUT/DELETE 요청 시 실제 DB에 존재하는 id 사용
- **DTO 분리로 Swagger에서 id 입력란이 사라져 이 문제를 원천 차단**

---

## 기타
- 추가 도메인, 인증/인가, 테스트, CI/CD 등 확장 가능
- 궁금한 점은 이슈로 남겨주세요! 