# WebHooker [![Discord](https://discordapp.com/api/guilds/184657525990359041/widget.png)](https://discord.gg/hprGMaM) [![Download](https://api.bintray.com/packages/elypia/WebHooker/common/images/download.svg)](https://bintray.com/elypia/WebHooker/common/_latestVersion) [![Documentation](https://img.shields.io/badge/Docs-WebHooker-blue.svg)](https://webhooker.elypia.com/) [![GitLab Pipeline Status](https://gitlab.com/Elypia/WebHooker/badges/master/pipeline.svg)](https://gitlab.com/Elypia/Elypiai/commits/master) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/51814ca2e04c46809f97634601595741)](https://www.codacy.com/app/Elypia/WebHooker?utm_source=gitlab.com&amp;utm_medium=referral&amp;utm_content=Elypia/Elypiai&amp;utm_campaign=Badge_Grade) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/51814ca2e04c46809f97634601595741)](https://www.codacy.com/app/Elypia/WebHooker?utm_source=gitlab.com&utm_medium=referral&utm_content=Elypia/WebHooker&utm_campaign=Badge_Coverage)

## Importing
### [Gradle](https://gradle.org/)
```gradle
implementation "com.elypia:webhooker:{VERSION}"
```

### [Maven](https://maven.apache.org/)
```xml
<dependency>
  <groupId>com.elypia</groupId>
  <artifactId>webhooker</artifactId>
  <version>{VERSION}</version>
</dependency>
```

## About
WebHooker is a small library for creating webhook endpoints to receive payloads you want to subscribe too. This is done by running a webserver using [SparkJava](http://sparkjava.com/) and [GSON](https://github.com/google/gson), creating an endpoint comprised of three parts, a long and randomly generated string, then a `@Mapping` to get the class and method to route the payload to.

## Quick-Start
**`Main.java`**
```java
public class Main {
    
    public static void main(String[] args) {
        WebHooker hooker = new WebHooker("https://elypia.com/");
        hooker.add(new ExampleReceiver());
    }
}
```
**`ExampleReceiver.java`**
```java
@Mapping("example")
public class ExampleReceiver extends Receiver {
    
    /**
    * Get's requests to: https://elypia.com/{RANDOM_UUID}/example/example
    */
    @Mapping("example")
    public void justGetTheEvent() {
        // This only gets the event to perform code
    }
    
    /**
    * This will respond to the webhook event, for example in cases
    * you must give the first event back some kind of verification token.
    * 
    * @param payload All methods can take the optional @Payload
    * parameter if it's required.
    */
    @Mapping("respond")
    public void respondToEvent(Payload payload) {
        payload.getResponse().body("{verification_token}");
    }
    
    /**
    * Assume the payload body was something like:
    * {
    *     "username": "Seth",
    *     "age": 20
    * }
    * @param payload The payload again with request and response.
    * @param player JSON was returned with this event, so we map it 
    * to a POJO with GSON so it can be used.
    */
    @Mapping("player")
    public void parseEventObject(Payload payload, Player player) {
        kill(player.getUsername());
    }
}
```
**`MyObject.java`**
```java
public class Player {
    
    private String username;
    private int age;
    
    // Getters and Setters omitted for brevity.
}
```
