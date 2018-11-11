# Space shooter game / Processing / AI / high playability / functionally complete / legacy code

# Space Attack

![Image](https://zagorskidev.files.wordpress.com/2017/10/zrzut-ekranu-z-2017-10-05-13-02-38.png?w=723)

# How is it done?

Main menu in Swing and gameplay as PApplet (Processing 3.0). Project can be easily extended by adding new levels and ships. There are differencies in enemy ships behavior: some of them strictly chase player, another just slowly fly down and shooting, but most interesting ones predict players next moves – game stores data about player habits (most commonly used sectors on map). Due to maps, enemies (bosses too!), weapons and tactics variety with balanced difficulty and good hard rock music, it’s not just a practice programming project. It’s fully playable game with dynamic gameplay that can provide entertainment for few hours.

# How does it look like?

You can watch short video of application running.

[![Video](https://img.youtube.com/vi/8u5BX7EyPnQ/0.jpg)](https://youtu.be/8u5BX7EyPnQ)

# How can I run it?

You can compile src/spaceAttack/SpaceAttack.java or download runnable JAR here: [runnable JAR](https://drive.google.com/open?id=0B_bwkWjLwn2MQjc5ZTNoaGl1YjQ).

It requires Java 8 (PApplet doesn’t work with Java 9 yet).

![Image](https://zagorskidev.files.wordpress.com/2017/10/zrzut-ekranu-z-2017-10-05-13-01-31.png)

# How can it be improved?

Code could be refactored using some design patterns, it would make adding levels/ships even more easy. Main issue is running PApplet from Swing GUI. Each mission requires new instance of PApplet, but closing PApplet causes closing entire application. Now it is solved by hiding useless PApplets, but after few missions, they stack and slow system down. In effect, player has to restart application every few missions. Also there is inelegant solution of concurrency issues, now I could make it much more clean. For now I think Processing isn’t best choice as library used to make such application, especially considering it’s problems with Java 9.

![Image](https://zagorskidev.files.wordpress.com/2017/10/zrzut-ekranu-z-2017-10-05-13-09-30.png?w=723)
