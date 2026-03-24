# 📦 Simple Order Management Service

상품 관리와 주문 처리의 핵심 로직을 구현한 스프링 부트 애플리케이션입니다.


![Java 17](https://img.shields.io/badge/Java-17-007396?logo=java&logoColor=white)
![Spring Boot 3.x](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot&logoColor=white)
!![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-latest-2496ED?logo=docker&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-B51811?logo=hibernate&logoColor=white)

---

##  Key Features (STAR)

### 1. 상품(Product) CRUD 및 동적 이름 반영
* **Situation**:
* **Task**: 
* **Action**: 
* **Result**: 

### 2. 주문 목록 페이징 및 N+1 문제 해결 (도전 과제)
* **Situation**:
* **Task**:
* **Action**:
* **Result**:

### 3. 원자적(Atomic) 재고 차감 (도전 과제)
* **Situation**:
* **Task**:
* **Action**:
* **Result**:

---

## 📂 Project Structure
도메인 중심의 계층화를 위해 `domain` 패키지에 핵심 로직을 모으고, 기술적 설정은 `config`에서 관리합니다.

```text
src/main/java/com/example/orderservice/
├── domain/                      # 비즈니스 핵심 (도메인별 응집)
│   ├── product/                 # 상품 도메인 (Controller, Service, Repository, Entity, DTO)
│   └── order/                   # 주문 도메인 (Controller, Service, Repository, Entity, DTO)
├── config/                      # 전역 설정 및 인프라
│   └── error/                   # GlobalExceptionHandler 및 커스텀 예외
└── common/                      # 프로젝트 전역 공용 컴포넌트
    └── response/                # 공통 응답 규격


## ⚙️ How to Run
DB 실행: docker-compose up -d
어플리케이션 실행: 