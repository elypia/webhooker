# Webhooker [![Matrix]][matrix-community] [![Discord]][discord-guild] [![Maven Central]][maven-page] [![Docs]][documentation] [![Build]][gitlab] [![Coverage]][gitlab] [![Donate]][elypia-donate]
The [Gradle]/[Maven] import string can be found at the Download badge above!

## About
Webhooker is a small library for hosting webhook callbacks to receive and dispatch callbacks from
external services such as Twitch or Slack. This is done by running a webserver using [SparkJava] 
and serializing JSON with [GSON], and then managing `Client`s to map the request URL to callback(s).

## Quick-Start
```java
public class Main {
    
    /**
    * This example assumes a reverse proxy is routing
    * requests from `webhooks.elypia.org` to `localhost:4567`.
    * If no reverse proxy is desired, `http://your.public.ip:4567/:uuid` is fine.
    * 
    * @param args
    */
    public static void main(String[] args) {
        // `publicUrl` must specify route parameter `uuid`.
        Webhooker hooker = new Webhooker("https://webhooks.elypia.org/:uuid", 4567);

        // Add a new client to the client controller.
        Client client = hooker.getController().add(new Client());
        
        // Perform these callbacks whenever this client receives payload in the order provided.
        client.addCallbacks((payload) -> System.out.println(payload.getRequest().body()));

        // Get the callback url for this client, this will be provided to a service to POST to.
        String callbackUrl = hooker.getUrl(client);
                
        // Subscribe to payloads from a third party service.
        TwitchNotifier notifier = new TwitchNotifier();
        notifier.subscribe(callbackUrl, "https://api.twitch.tv/helix/users/follows?first=1&from_id=31415");
    }
}
```

[matrix-community]: https://matrix.to/#/+elypia:matrix.org "Matrix Invite"
[discord-guild]: https://discord.gg/hprGMaM "Discord Invite"
[maven-page]: https://search.maven.org/search?q=g:org.elypia.webhooker "Maven Central"
[documentation]: https://elypia.gitlab.io/webhooker "Webhooker Documentation"
[gitlab]: https://gitlab.com/Elypia/webhooker/commits/master "Repository on GitLab"
[Gradle]: https://gradle.org/ "Depend via Gradle"
[Maven]: https://maven.apache.org/ "Depend via Maven"
[SparkJava]: http://sparkjava.com/ "SparkJava"
[GSON]: https://github.com/google/gson "Google GSON"
[elypia-donate]: https://elypia.org/donate "Donate to Elypia"

[Matrix]: https://img.shields.io/matrix/elypia-general:matrix.org?logo=matrix "Matrix Shield"
[Discord]: https://discordapp.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[Maven Central]: https://img.shields.io/maven-central/v/org.elypia.webhooker/core "Download Shield"
[Docs]: https://img.shields.io/badge/docs-Webhooker-blue.svg "Documentation Shield"
[Build]: https://gitlab.com/Elypia/webhooker/badges/master/pipeline.svg "GitLab Build Shield"
[Coverage]: https://gitlab.com/Elypia/webhooker/badges/master/coverage.svg "GitLab Coverage Shield"
[Donate]: https://img.shields.io/badge/elypia-Donate-blueviolet "Donate Shield"
