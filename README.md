# ![Vector icon with a basic mailbox. Open box with the flag lifted up, rolled newspaper in the mailbox.](https://github.com/drazisil/Mailit/blob/master/src/main/resources/FreeVector-Mailbox-Vector.jpg?raw=true) Mailit

Mailit is a Minecraft Bukkit plugin to allow for player mailboxes.

Any versions tagged `-SNAPSHOT` are development versions that may not be feature complete (but hopefully don't error)

### Features:

* Send items between players
* Notify players of new mail when they join

### Planned Features:


* Handle Metadata
* Ability to delete unopened mail
* Corrupted mail should not cause crash
* Ping a player when new mail for them arives
* Allow server staff to access player mailboxes for security and abuse reasons

Any additional feature requests, please submit them at the [support link](https://github.com/drazisil/Mailit/issues) and I will see what I can do.

### Commands:

`/mail`

Check your mail

`/mail list` 

View the details (somewhat) of the packages in your mailbox

`/open <package number>`

Open a package! It will auto delete once it's empty.

`/send <player name>`

Create a new package addressed to a player. You should be able to send almost any item (except items with metadata, those will corrupt your mailbox)

### Permissions:

```
mailit.mailbox:
  description: Allow players to check mail
  default: true
  
mailit.send:
  description: Allow players to send mail
  default: true
```

Enjoy!

### Credits:

* This plugin is inspired by [RCMailbox](https://www.spigotmc.org/resources/rcmailbox-send-items-to-online-offline-players-1-7-1-13.60636/), which does not currently work for 1.15.
* Mailbox icon from [freevector.com](https://www.freevector.com/mailbox-vector) and is used under the CCA 4.0 license.
