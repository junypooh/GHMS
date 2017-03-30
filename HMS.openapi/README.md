[xmldsig-1.0.jar](/files/279)
HBS openapi 모듈
==

홈 IoT 캠/피트니스 OpenAPI 프로젝트

초기 다운로드 시 thirdparty repository를 찾지 못하는 문제로 maven 에러 발생시에 아래와 같이 해당 파일을 담당자로부터 전달받아 인스톨 하면됨.
mvn install:install-file -Dfile=[filepath]\gw-spotdev-api-1.0.0.jar -DgroupId=com.kt.iot.gw -DartifactId=gw-spotdev-api -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=[filepath]\kmcert-1.7.jar -DgroupId=com.icert.comm -DartifactId=kmcert -Dversion=1.7 -Dpackaging=jar
