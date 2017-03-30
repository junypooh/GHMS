## 기가 홈 매니저 서비스 OpenAPI

#로컬 환경 설정
1. postgresql-9.1-901-1.jdbc4.jar 파일 로컬 톰켓/lib copy
2. 톰켓 launch configuration properies > arguments 에 아래 내용 추가
-Xms128m -Xms512m -XX:MaxPermSize=128m
-Dds.name="jdbc/hcam" -Dlog4j.appender.rolling.File="/home/server/tomcat/logs/ghms/sys.log"
3. 로컬 톰켓 server.xml > GlobalNamingResources 에 아래 내용 추가
    &lt;Resource
      auth="Container"
      name="jdbc/hcam"
      type="javax.sql.DataSource"
      url="jdbc:postgresql://211.42.137.221:5432/openapi6"
      username="hms"
      password="hms123"
      driverClassName="org.postgresql.Driver"
      initialSize="10"
      maxActive="200"
      maxIdle="10"
      minIdle="5"
      maxWait="4000"
      validationQuery="select 1"
      testOnBorrow="false"
      testOnReturn="false"
      testWhileIdle="false"
      timeBetweenEvictionRunsMillis="600000"
      numTestsPerEvictionRun="1"
      minEvictableIdleTimeMillis="300000"
      factory="org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory"/&gt;

3. 로컬 톰켓 context.xml 에 아래 내용 추가
    &lt;ResourceLink name="jdbc/hcam" global="jdbc/hcam" type="javax.sql.DataSource"/&gt;

4. application-context.xml의 encription 태그 에러
  - 원인 : jasypt 사이트에서의 xml스키마 지원이 안될경우 발생.
  - 해결 : eclipse > preferences > xml > xml catalog에 아래의 내용을 추가한다.
  - Location : jar:file:absjarpath/jasypt-spring3-1.9.2.jar!/org/jasypt/spring3/xml/encryption/jasypt-spring3-encryption-1.xsd
  - Key type : Schema location
  - Key : http://www.jasypt.org/schema/encryption/jasypt-spring3-encryption-1.xsd
  
