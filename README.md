# RPG-Project

Welcome to my start of a RPG-Project using the Spigot API.

I am a huge fan of RPG games, and have aspirations to make one myself one day.
This code utilizes a SQL database for persistent data which will allow the implementation of cross-server activity in the future.
A PlayerData object is initialized on the PlayerJoinEvent which retrieves all player data from the SQL database.
The data is stored it into a static HashMap named PLAYER_DATA, which located in the project's RPGProject class.
Upon unexpected server reloads or restarts, large amounts of SQL queries would not take place. This leads to data loss and setbacks in player progress.
To counteract this, there are means of writing a player's data to a local file and reading said files next time the server is started up.
This safety measure will ensure players will not lose their progress when such an event occurs!

I have no plans at this time to continue this project, but I will likely siphon code from here if needed in the future.

Thankis for reading :)
