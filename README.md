2014년 개발 경험 프로젝트
=========

1. 로컬 개발 환경에 Tomcat 서버를 시작한 후 http://localhost:8080으로 접근하면 질문 목록을 확인할 수 있다. http://localhost:8080으로 접근해서 질문 목록이 보이기까지의 소스 코드의 호출 순서 및 흐름을 설명하라.

- Index.jsp로 접근한 후 response.sendRedirect("/list.next"); 에 의해 list.next로 redirect.
'*.next'는 annotation에 의해 FrontContoller로 보내짐.
- RequestMapping 클래스에 의해 이미 '/list.next'는 listController에 매핑되어 있으므로 frontController에서 req.getRequestURI()로 findController함수를 실행하면 listController 객체를 생성해서 넘겨줌.
- Controller를 이용하여 listController를 execute().
- listController에서 questionDAO의 findAll함수를 실행.
- questionDAO는 jdbcTemplate을 호출하고 sql과 rm을 생성하여 jdbcTemplat의 list함수에게 넘긴다. 여기에서 rm은 rowMapper로, questionDAO의 findAll함수 내에서 interface클래스를 생성하여 mapRow라는 method를 품은 상태로 jdbcTemplate에 넘겨진다. mapRow은 query를 실행한 후에 받은 resultSet에서 필요한 부분만 저장할 수 있는 로직을 가진다.
- jdbcTemplate의 list함수에서는 mysql과의 연동을 위해 connection과 pstmt등을 생성하고 넘겨받은 sql을 실행하여 resultSet에 담는다. 넘겨받는 rm의 mapRow함수에 rs를 넘겨주어 필요한 부분만 담은 list를 만들고 리턴한다.
- list<question>을 넘겨받은 listController는 mav의 view로 'list.jsp'를 설정하고 list<question>을 mav에 object로 추가한후 mav를 return한다. 
- mav를 넘겨받은 frontController는 mav에서 view를 꺼내 객체로 생성하고 render함수를 실행한다. view는 interface이므로 해당 view가 jstl일 경우 jstlView의 render가, json일 경우 jsonView의 render가 실행되어 list.jsp로 보내준다. 
- list.jsp는 받은 questions를 활용하여 화면에 띄워준다.