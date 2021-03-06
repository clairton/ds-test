#ds-test[![Build Status](https://travis-ci.org/clairton/ds-test.svg?branch=master)](https://travis-ci.org/clairton/ds-test)
Extensão CDI que Cria DataSources para serem usando nos testes

Necessita cria um arquivo datasources.properties em src/test/resources

```properties
#Um exemplo
abc=Datasource
abc.jndi=java:/jdbc/datasources/MyDS
abc.password=
abc.driver=org.hsqldb.jdbcDriver
abc.username=sa
abc.url=jdbc:hsqldb:file:target/test;hsqldb.lock_file=false;create=true
#Outro exemplo
def=dataSource
def.jndi=jdbc/MyDS2
def.password=
def.username=sa
def.driver=org.h2.Driver
def.url=jdbc:h2:~/test
```

Para usar com o eclipselink adicionar na tag properties no persistence.xml:
```xml
<property name="eclipselink.target-server" value="JBoss"/>
```
Para usar com o hibernate adicionar na tag properties no persistence.xml:
```xml
<property name="hibernate.transaction.jta.platform" value="org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform" />
```

Também é necessário ter um producer para um EntityManager pois a extensão observa o evento ProcessProducer<?, EntityManager>, exemplo:
```java
@Produces
public EntityManager getEntityManager(){
	return null;
}
```

 Também adicionar as depêndencias:
```xml
<dependency>
    <groupId>br.eti.clairton</groupId>
	<artifactId>ds-test</artifactId>
	<version>X.X.X</version>
</dependency>
```
