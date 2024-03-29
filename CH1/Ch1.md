### 도메인

도메인
  - 소프트웨어로 해결하고자 하는 문제 영역

도메인 모델
  - 특정 도메인을 개념적으로 표현한것
  - 모델을 구성하는 핵심 구성요소, 규칙, 기능을 찾아 모델링한다
  - 엔티티(Entity), 모델(Model)

엔티티(Entity)
  - 고유한 식별자를 가진다
  - 엔티티의 식별자 생성 : 특정 규칙에 따라 생성, UUID, Nano ID 같은 고유 식별자 생성기 사용, 직접 입력, 일련번호(시퀀스) 사용

밸류 타입
  - 개념적으로 완전한 하나를 표현한다
  - 불변으로 구현한다(동등성 재정의, setter 사용 X)

### 아키텍쳐

아키텍쳐
  - 표현, 응용, 도메인, 인프라스트럭처
  - 표현 : HTTP 요청을 응용 영역이 필요로 하는 형식으로 변환해서 응용 영역에 전달하고 응용 영역의 응답을 
  HTTP 응답으로 변환하여 전송
  - 응용 : 전달받은 요청을 시스템이 사용자에게 제공해야 하는 기능을 구현(도메인 영역의 도메인 모델 사용)
  응용 서비스는 로직을 직접 수행하지 않고 도메인 모델에 로직수행을 위임한다
  - 인프라스트럭처 : 구현 기술 (RDBMS 연동, 메시징큐 메시지 전송 수신기능 구현, MongoDB, Redis의 데이터 연동 처리)
  SMTP 메일 발송 기능 구현, REST API 호출 처리. 논리적인 개념을 표현하기 보다 실제 구현을 다룬다
  
DIP
  - 고수준 모듈(의미 있는 단일 기능 제공 모듈). 저수준 모듈(하위 기능 실제 구현)
  - 저수준 모듈이 고수준 모듈에 의존하도록 변경 -> 추상화 인터페이스
  - 의존 역전 원칙(Dependency Inversion Principle)
  - 인프라스트럭처 영역 : 구현 기술을 다루는 저수준 모듈
  - 응용, 도메인 영역 : 고수준 모듈

도메인 영역의 구성 요소
  - 엔티티(Entity) : 도메인의 고유한 개념. 도메인 모델의 데이터를 포함하며 데이터와 관련된 기능을 제공. 고유한 식별자를 가진다
  - 밸류(Value) : 개념적으로 하나인 값을 표현. 다른 밸류 타입의 속성으로도 사용 가능. 고유한 식별자를 가지지 않는다
  - 애그리거트(Aggregate) : 연관된 엔티티와 밸류 객체를 개념적으로 하나로 묶은 것
  - 리포지터리(Repository) : 도메인 모델의 영속성을 처리
  - 도메인 서비스(Domain Service) : 특정 엔티티에 속하지 않은 도메인 로직 구현
  - 엔티티 : 주문, 회원, 상품
  - 밸류 : 주소, 금액
  - 애그리거트 : (주문 엔티티 + 주문라인 밸류 + 주문자 밸류) -> 주문 애그리거트
  - 리포지터리 : DBMS 테이블에서 엔티티 객체를 로딩하거나 저장
  - 도메인 서비스 : 할인 금액 계산

엔티티와 밸류
  - 도메인 모델의 엔티티 : 데이터와 함께 도메인 기능을 제공한다
  - 두개 이상 데이터가 개념적으로 하나일 경우 밸류 타입을 이용해서 표현할수 있다

애그리거트
  - 도메인 모델에서 전체 구조를 이해할수 있다
  - 관련 객체를 하나로 묶은 군집
  - 군집에 속한 객체를 관리하는 루트 엔티티를 가진다
  - 루트 엔티티 (속해있는 엔티티와 밸류 객체를 이용해 구현해야 하는 기능을 제공)
  - 애그리거트 단위로 구현을 캡슐화

리포지터리
  - 구현을 위한 도메인 모델
  - 애그리거트 단위로 도메인 객체를 저장하고 조회하는 기능을 정의한다
  - 응용 서비스는 필요한 도메인 객체를 구하거나 저장할 때 리포지터리를 사용한다
  - 응용 서비스는 트랜잭션을 관리하는데, 트랜잭션 처리는 리포지터리 구현 기술의 영향을 받는다

애그리거트의 사용
  - 한 애그리거트에 속한 객체는 유사하거나 동일한 라이프 사이클을 가진다
  - 한 애그리거트에 속한 객체는 다른 애그리거트에 속하지 않는다
  - 각 애그리거트는 다른 애그리거트를 관리하지 않는다
  - 도메인 규칙과 요구사항으로 경계를 설정한다
 
  - 애그리거트 루트 엔티티 : 애그리거트의 대표 엔티티
  - 애그리거트의 일관성이 깨지지 않도록 한다
  - 애그리거트 외부에서 애그리거트에 속한 객체를 직접 변경하면 안된다

트랜잭션
  - 한 트랜잭션에서는 한개의 애그리거트만 수정한다
  - 두개 이상의 애그리거트를 변경해야 한다면, 응용 서비스에서 각 애그리거트의 상태를 변경한다

매핑 구현
  - 애그리거트 루트는 @Entity로 매핑 설정한다
  - 밸류는 @Embeddable로 매핑 설정한다
  - 밸류 타입 프로퍼티는 @Embedded로 매핑 설정한다
  - 







