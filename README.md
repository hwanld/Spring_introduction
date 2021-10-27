# Spring_introduction
<h2>Spring을 공부 기록용 만든 repository</h2>

**2021.09.14)**<br> 회원의 id와 name을 저장하고 반환하는 등의 간단한 서비스를 제작하기 시작함. DB가 아직 구현되어 있지 않고, 어떤 DB를 쓸지 모른다고 가정이 되어 있기 때문에 우선적으로 repository를 만들고 interface를 사용해서 구현한다. 나중에 DB가 선택이 되면 그때 interface만 수정하면 되기 때문에 interface를 우선 만든다. 이와 같이 설계한다면 나중에 interface를 변경함으로써 구현 클래스를 변경할 수 있다. 이후 그 interface를 implement하는 MemoryMemberRepository라는 class를 만들어서 구현한다. 또한 개발을 진행하기 위해서 우선 초기 개발 단계에서는 구현체를 이용해서 가벼운 메모리 기반의 저장소를 사용한다. (실무에서는 list가 사용하기 편하기 때문에 list를 많이 사용한다)<br><br>
**2021.09.15)**<br> Test case를 작성하는 방법을 공부하였다. main 메서드를 통해서 실행하거나 웹 어플리케이션의 컨트롤러를 통해서 해당 기능을 실행하면 실행하는데 오래 걸리고 반복 실행이 어렵고 등등의 단점이 존재한다. 자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다. 또한 테스트 케이스의 메소드는 전부 독단적으로 움직어야 되기 때문에 (메모리 DB에 직전 테스트의 결과가 남았을 수 있다) @AfterEach를 사용해서 메모리에 저장된 DB를 삭제할 필요가 있다. 또한 테스트 순서에 의존관계가 있는 것은 좋은 테스트라고 말할 수 없다. 그리고 현재의 경우는 구현->테스트 순서로 작성하였으나, 실무에서는 테스트->구현의 순서로 개발을 하기도 한다. (tdd:test-driven-development;작은 단위의 테스트 케이스를 만들고 이를 통과하는 코드를 추가하는 단계를 반복한다) Test에 대해서는 매우 중요한 내용이기 때문에 나중에 따로 공부를 더 해보고 정리해볼 것.<br><br>
**2021.09.21)**<br> MemberServiceTest를 작성하였다. 또한 test case에서 memberService 내부의 memberRepository가 test case에서 선언한 memberRepository와 다르기 때문에 @beforeEach를 사용해서 각 test method가 실행될 때 마다 new를 사용해서 객체를 새로 외부에서 넣어주는 방법을 사용하였다. (이전의 method에서 DB에 저장한 것이 지워지지 않기 때문에 각 method가 실행될 때 마다 새롭게 DB를 clear하는 등의 방법을 사용해야 한다.) 이러한 방법을 보고 dependency injection이라고 한다.<br><br>
**2021.09.24)**<br> 스프링 빈과 빈을 등록하는 방법 2가지 (컴포넌트 스캔과 자동 의존관계 설정, 자바 코드로 직접 스프링 빈 등록하기) 중 첫번째 방법을 공부하였다. @Component 애노테이션을 사용해서 스프링 빈으로 자동 등록을 할 수 있다. 예제에서 본 바와 같이 controller 객체를 통해서 외부 요청을 받고 service 객체를 통해서 비즈니스 로직을 만들고 repository 객체에서 데이터를 저장하는 것은 정형화된 패턴이다. 또한 controller 객체 내부에 service 객체가 있고 그 내부에는 repository 객체가 존재하기 때문에 repository 객체만 new keyword를 사용해서 생성하주고 service, controller 객체를 만들때 각 객체의 instance variable 역할을 하는 reposiroty, service 객체는 dependency injection을 통해서 삽입해 주는데, 이러한 과정을 스프링 빈으로 등록해둔다면 알아서 객체 생성 시 setter(constructor) method를 호출해서 injection을 스프링에서 알아서 해준다. 이러한 방식이 컴포넌트 스캔을 통한 자동 의존관계 설정이다. 구현 방법은 @Component 애노테이션을 사용하는데, @Controller, @Service, @Repository의 애노테이션은 전부 @Component 애노테이션을 조금 더 구체화해서 사용한 애노테이션이라고 생각하면 된다. 이후 Setter 메소드 위에 @Autowired 애노테이션을 달아준다면 알아서 Springbean에서 객체를 찾아서 dependency injection을 해준다. 또한 메인 메서드가 포함된 디렉토리의 하위 디렉토리를 스캔해서 찾아내는 방식이기 때문에 반드시 같은 디렉토리 하위에 있어야 하고, 같은 package로 감싸져 있어야 한다.<br><br>
**2021.09.26)**<br> 자바 코드로 직접 스프링 빈 등록하기 ; SpringConfig 클래스를 하나 만들고 (이름은 크게 상관이 없음) @Configuration이라고 애노테이션을 붙인다. 이후 @Bean 애노테이션을 이용해서 우리가 spring bean에 넣어야 하는 객체들을 생성한다. 이때 dependency injection때문에 객체를 생성할 때 그 객체 내부에 있는 또 다른 객체를 넣어줘야지 객체를 생성할 수 있는 경우가 있다. 이때는 그냥 동일 파일 내에서 @Bean 애노테이션과 함께 만든 객체를 전달 인자로 넣어주면 spring bean에 들어있는 객체로 알아서 넣어준다. Controller의 경우는 어쩔 수 없다. (기존의 방법처럼 @Controller라고 애노테이션을 달아주고 @Autowired를 사용한다) 두 방법에는 장단점이 존재한다. (과거하는 xml문서로 설정하기도 했으나 근래에는 거의 사용하지 않는다) (Dependency Injection의 경우는 3가지의 방법이 있다_constructor injection,field injection,setter injection이 있는데 생성자 주입을 권장한다. setter injection의 경우는 public하게 열려있기 때문에 누구나 접근할 수 있다. 다시 말해서 어느곳에서나 메소드의 호출이 가능하기 때문에 좋지 않다) 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다. 정형화되지 않거나, 상황에 따라 변경해야 하는 경우는 자바 코드로 직접 스프링 빈에 등록한다)
회원 웹 기능-홈 화면 추가 ; HomeController를 만들고, @GetMapping("/") 을 사용해서 처음 페이지를 시작했을때, 즉 시작 화면 도메인일때를 만들어 주고 templates 파일에 home.html을 만들어서 시작 화면을 home.html 이 되도록 만들어준다. 이때 static페이지로 welcome 페이지를 만들었더라도 톰켓 서버에서 1차로 spring container를 찾기 때문에 기존에 만들어둔 static폴더 안의 index.html은 무시된다. 마찬가지로 @GetMapping("/members/new") 키워드를 사용해서 새로운 도메인에 members/createMemberForm.html을 viewResolver가 뿌려준다. html파일 안의 form 태그에 의해서 post방식으로(주로 등록할 때 post, 조회할 때 get을 사용한다) input받은 값을 @PostMapping 애노테이션이 붙은 method들 중에서 맵핑이 일치한 method로 보낸다. (MemberForm form 객체의 name이라는 instance variable이 MemberForm.setter로 name의 값이 보내진다)<br><br>
**2021.09.27)**<br> 회원 웹 기능_조회 구현 : 가입 기능을 구현한 것과 마찬가지로 MemberController Class에서 @GetMapping을 사용, 원하는 주소를 가져온다. 그리고 Model 객체를 가져와서 model.addAttribute method를 사용해서 members/memberList에다가 return한다. 그러면 회원이 저장되어있는 리스트가 통째로 model에 담겨서 members/memberList로 전달된다. 그러고 한 가지 알아야 될 점은 memberList.html파일에서 thymeleaf문법을 사용해서 (자세한 내용은 html파일 참조) members가 전달받은 모델을 끝까지 돌면서 member.id, member.name을 출력해준다. 이때 서버를 껏다가 다시 켜게 되면 당연하게도 데이터가 초기화된다. 따라서 이러한 일을 막기 위해서 실무에서는 DB를 사용하거나 Data를 다른 파일 형식으로 저장해두고 사용한다.
<b>의문점 : 같은 이름을 2개 넣게 되면 MemberService객체에서 IlligalStateException 에러를 throw하는데, 이렇게 되면 홈페이지 내에서 Error가 발생하게 된다. 이러한 Error는 어떻게 handling하는가?</b><br><br>
**2021.10.03)**<br> 서버가 내려가면 데이터가 전부 사라지기 때문에 데이터를 따로 DB를 저장하고 서버와 DB를 연결한다. 이 연결하는데 필요한 기술은 Jdbc라고 한다. Jdbc->Spring JdbcTemplate->JPA(객체를 Querry 없이 바로 DB에 저장할 수 있다)->스프링 데이터 JPA<br>
JDBC URL:jdbc:h2:tcp://localhost/~/test ; 파일 직접 접속이 아닌 소켓을 활용한 접속<br><br>
**<리눅스(git bash)> 명령어**<br>
ll ; 하위 디렉토리의 파일을 보여줌<br>
ls -arlth ; 마찬가지 디렉토리 내의 파일을 보여줌<br>
cd ~ ; ~로 디렉토리를 옮겨줌<br>
rm ~ ; ~을 삭제해줌<br><br>
sql파일은 보통 프로젝트 폴더 안에 sql이라는 새로운 디렉토리를 만들고, 그 안에 ddl.sql 등과 같은 파일들을 저장. (깃 등의 버전관리시 편리)<br><br>
순수 Jdbc ; db에 insert 쿼리, select 쿼리 사용해서 db를 날릴 수 있다. 
1) build.gradle 파일에 jdbc, h2 DB 관련 라이브러리 추가 <br>
implementation 'org.springframework.boot:spring-boot-starter-jdbc' <br>
runtimeOnly 'com.h2database:h2' <br>
2) 스프링부트 데이터베이스 연결 설정 추가<br>
spring.datasource.url=jdbc:h2:tcp://localhost/~/test<br>
spring.datasource.driver-class-name=org.h2.Driver<br>
spring.datasource.username=sa<br>

이후 bulid.gradle 파일에서 reload (ctrl+shift+O) 를 사용해서 불러와준다.
이후 repository에 기존에 만들어 준 MemoryMemberRepository를 대체할 JdbcMemberRepository를 만들고, MemberRepository(Interface)를 implements하도록 만든다. alt+enter를 사용해서 methods를 import해준다. final 형태의 DataSource를 만들어주고 constructor를 만든 다음 method들을 하나하나 DB형식으로 수정해준다. 메소드를 다 구현했다면, configuration을 해준다. (SpringConfig 파일을 통해서 springBean에 띄우고, 조립하고 등을 했었다. 이것을 MemoryMemberRepository에서 JdbcMemberRepository로 바꿔주어야 한다. **여기서 Configuration 파일에 DataSource를 추가하고 그에 따른 생성자와 @Autowired, 그리고 memberRepository를 반환하는 타입을 JdbcMemberRepository로만 바꿔주면 기존에 memory에 저장하던 repository가 이제는 DB에 저장하는 방식으로 바뀐다;다형성을 활용한다 with Dependency Injection (OOP의 장점)**<br><br>
**2021.10.05)**<br>스프링 컨테이너와 DB까지 연결한 통합 테스트 진행. 이전에 진행하였던 테스트는 사실 spring을 사용한 것이 아닌, java코드만을 사용한 테스트 코드이다. 하지만 DB가 연결된 이후에서는 test코드를 spring을 사용해서 구현해야 한다. 우선 스프링으로 test를 작성할때는 @SpringBootTest(스프링 컨테이너와 테스트를 함께 실행한다),@Transactional(테스트 시작 전에 트랜잭션을 시작하고 테스트 완료 후 항상 롤백한다) 애노테이션을 사용한다. 또한 test case에서는 필요한 객체를 injection해서 사용만 하면 되기 때문에 Constructor를 통해서 injection을 해줄 필요 없이 그냥 @Autowired를 사용해서 field 기반으로 객체를 생성해준다. @BeforeEach를 써서 굳이 주입할 필요가 없이 SpringBoot로부터 받아오면 되기 때문에 삭제한다. 또한 @Transactional때문에 @AfterEach를 통해서 초기화 해줄 필요도 없다. 기존에 DB에 data가 남아있다면 ERROR가 발생할 가능성이 높다. 따라서 test용 DB를 따로 만들어서 연결해주거나, local에서 DB를 초기화하고 테스트 하는것이 권장된다. testcode는 자고로 반복해서 사용할 수 있어야 한다. 하지만 우리가 test를 할때마다 DB를 초기화 하지 않으면 중복되는 값이 DB에 남게 된다. (이전의 test에서 DB에 저장한 data가 삭제되지 않았다) 따라서 @Transactional 애노테이션을 활용하면 test가 끝나고 난 다음 롤백을 해버린다. (insertquery 등은 정상적으로 전달이 되고, 롤백을 통해서 다시 지워지게 되는 것이다) 만약 저장을 하고 싶다면 (롤백에서 예외시키고 싶다면) @Commit 애노테이션을 사용한다. 순수 자바 코드만을 사용해서 작은 단위로 짤라서 테스트 (순수 단위 테스트)가 실행 시간도 압도적으로 짧고 간단하기 때문에 비교적으로 더 좋은 테스트라고 말할 수 있다. 통합 테스트도 물론 필요하지만 작은 단위 테스트가 훨씬 더 좋은 테스트이다. <b>의문점 : 현재 test code 등을 실행하면 sequence의 값이 ++되는데, 이러한 sequence의 값 때문에 새로 서버를 띄워서 실행할 때 sequence가 1부터 시작하는 것이 아닌 다른 숫자로 시작하게 된다. 이러한 점이 생기는 이유가 무엇이며, DB를 초기화하는 것 말고 spring/java 코드 내에서 해결할 수 있는 방법이 없을까?</b><br><br>
**2021.10.27)**<br>
스프링 JdbcTemplate : 순수 Jdbc와 동일한 환경설정을 하면 된다. 스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다. 우선 repository package에 jdbcTemplateMemterRepository 클래스를 만들고 MemberRepository를 implements하도록 선언한다. 이후 alt+shift+enter 단축키를 사용해서 MemperRepository interface의 methods들을 import해온다. 이이서 JdbcTemplate 형태의 final 변수 jdbcTemplate를 선언해주고, instructor를 생성하는데 이때 constructor를 Datasource를 통해서 받아온다. DataSource는 @Autowired 애노태이션을 활용한다. 그런데 만약 constructor가 하나라면 @Autowired 애노태이션을 생략해도 spring에서 알아서 처리해준다. 이후 하나씩 메소드들을 구현할껀데, 우선 findByID 메소드를 구현한다. jdbcTemplate.query를 리턴하도록 하고 ("select * from member where id = ?", @)와 같이 우선 구현한다. @에는 RowMapping이 필요하다. 따라서 아래에 RowMapper를 또 구현해준다. RowMapper를 구현할 때 return 뒤에 alt+enter를 사용하면 자바의 lambda형태로 대체가 가능하다. 그렇게 구현한 RowMapper를 findByID의 두번째 파라미터로 넣는데, 이것이 List형태로 반환이 되기 때문에 List<Member> result라는 변수를 만들어서 그 변수에 값을 집어넣고 result.stream().findAny()를 return한다. Jdbc코드와 비교했을때 JdbcTemplate코드는 상당히 양이 줄어드는 것을 알 수 있다. 다음으론 save메소드를 구현한다. SimpleJdbcInsert를 활용하면 (JdbcTemplate를 넘겨서 만든다) querry를 짤 필요 없이 그냥 메소드 몇개만 활용해서 만들 수 있다. findByName, findByAll은 findById와 사용하는 것이 동일하다.  이러한 사용법들은 jdbcTemplate 메뉴얼을 검색하는 등으로 볼 수 있으니, 나중에 참고해 보도록 하자. 이렇게 구현한 모든 내용들을 SpringConfig에다가 조립을 해주어야된다. <br><br>


