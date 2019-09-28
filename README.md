# WebHooker [![Discord][discord-members]][discord] [![Download][bintray-download]][bintray] [![Documentation][docs-shield]][docs] [![GitLab Pipeline Status][gitlab-build]][gitlab] [![Coverage][gitlab-coverage]][gitlab]  
The [Gradle][gradle]/[Maven][maven] import string can be found at the Download badge above!

## About
WebHooker is a small library for hosting webhook callbacks to receive and dispatch callbacks from
external services such as Twitch or Slack. This is done by running a webserver using [SparkJava][spark] 
and serializing JSON with [GSON][gson], and then managing `Client`s to map the request URL to callback(s).

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
        WebHooker hooker = new WebHooker("https://webhooks.elypia.org/:uuid", 4567);

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

[discord]: https://discord.gg/hprGMaM "Discord Invite"
[discord-members]: https://discordapp.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[bintray]: https://bintray.com/elypia/webhooker/core/_latestVersion "Bintray Latest Version"
[bintray-download]: https://api.bintray.com/packages/elypia/webhooker/core/images/download.svg "Bintray Download Shield"
[docs]: https://webhooker.elypia.org/ "Commandler Documentation"
[docs-shield]: https://img.shields.io/badge/Docs-WebHooker-blue.svg "Commandler Documentation Shield"
[gitlab]: https://gitlab.com/Elypia/webhooker/commits/master "Repository on GitLab"
[gitlab-build]: https://gitlab.com/Elypia/webhooker/badges/master/pipeline.svg "GitLab Build Shield"
[gitlab-coverage]: https://gitlab.com/Elypia/webhooker/badges/master/coverage.svg "GitLab Coverage Shield"

[gradle]: https://gradle.org/ "Depend via Gradle"
[maven]: https://maven.apache.org/ "Depend via Maven"

[spark]: http://sparkjava.com/ "SparkJava"
[gson]: https://github.com/google/gson "Google GSON"
