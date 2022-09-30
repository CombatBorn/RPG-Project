# RPG-Project

Welcome to my start of a RPG-Project using the Spigot API.

I am a huge fan of RPG games, and have aspirations to make one myself one day.

NOTE: I have no plans at this time to continue this project, but I will likely siphon code from here if needed in the future.

=== PLAYER DATA ===
This code utilizes a SQL database for persistent data which will allow the implementation of cross-server activity in the future.
A PlayerData object is initialized on the PlayerJoinEvent which retrieves all player data from the SQL database.
The data is stored it into a static HashMap named PLAYER_DATA, which located in the project's RPGProject class.
Upon unexpected server reloads or restarts, large amounts of SQL queries would not take place. This leads to data loss and setbacks in player progress.
To counteract this, there are means of writing all online player's data to local files which are uploaded to the database the next time the server is started up.
This safety measure will ensure players will not lose their progress when such an event occurs!

=== SKILLS ===
Players are able to participate in certain activities within the game to gain experience in 3 Rank categories: Combat, Gathering, and Crafting.
Each Rank category has Skills and Elite Skills. Ranking up once in a category will give 1 skill point which can be spent on any Skill or Elite Skill of the player's choosing.
Elite Skills require certain Skills to be the max level before applying points into it.
Each Skill and Elite Skill will grant a player perks upon reaching specific milestones.

Thanks for reading :)
