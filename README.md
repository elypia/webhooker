# WebHooker [![matrix-members]][matrix] [![discord-members]][discord] [![bintray-download]][bintray] [![docs-shield]][docs] [![gitlab-build]][gitlab] [![gitlab-coverage]][gitlab] [![donate-shield]][elypia-donate]
The [Gradle]/[Maven] import string can be found at the Download badge above!

## About
WebHooker is a small library for hosting webhook callbacks to receive and dispatch callbacks from
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

[matrix]: https://matrix.to/#/+elypia:matrix.org "Matrix Invite"
[discord]: https://discord.gg/hprGMaM "Discord Invite"
[bintray]: https://bintray.com/elypia/webhooker/core/_latestVersion "Bintray Latest Version"
[docs]: https://webhooker.elypia.org/ "Commandler Documentation"
[gitlab]: https://gitlab.com/Elypia/webhooker/commits/master "Repository on GitLab"
[Gradle]: https://gradle.org/ "Depend via Gradle"
[Maven]: https://maven.apache.org/ "Depend via Maven"
[SparkJava]: http://sparkjava.com/ "SparkJava"
[GSON]: https://github.com/google/gson "Google GSON"
[elypia-donate]: https://elypia.org/donate "Donate to Elypia"

[matrix-members]: https://img.shields.io/matrix/elypia-general:matrix.org?logo=matrix "Matrix Shield"
[discord-members]: https://discordapp.com/api/guilds/184657525990359041/widget.png "Discord Shield"
[bintray-download]: https://api.bintray.com/packages/elypia/webhooker/core/images/download.svg "Bintray Download Shield"
[docs-shield]: https://img.shields.io/badge/Docs-WebHooker-blue.svg "Commandler Documentation Shield"
[gitlab-build]: https://gitlab.com/Elypia/webhooker/badges/master/pipeline.svg "GitLab Build Shield"
[gitlab-coverage]: https://gitlab.com/Elypia/webhooker/badges/master/coverage.svg "GitLab Coverage Shield"
[donate-shield]: https://img.shields.io/badge/Elypia-Donate-blueviolet "Donate Shield"
