# SOFTENG 206 ASSIGNMENT 3: KĒMU KUPU #

## To Run This Program ##

### Running via Command Line ###

#### 1. Give QuizFunctionality.sh Executable Permissions ####
1. Extract .zip file to a known location.
2. In a new terminal, change directory into extracted zip folder.
3. Run code below to give executable permissions to script file.
```shell
chmod +x src/script/QuizFunctionality.sh
```

#### 2. Run .jar via Command Line ####
1. Run code below in terminal while in same directory as 1.2.  
(Remember to change */home/student/javafx-sdk-11.0.2/javafx* to your own javaFX library location).
```shell
java --module-path /home/student/javafx-sdk-11.0.2/javafx --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -jar kemuKepu.jar
```

2. ***If running on the SOFTENG206 Virtual Machine***, quiz sometimes renders improperly so instead run this code in terminal.  
(Remember to change */home/student/javafx-sdk-11.0.2/javafx* to your own javaFX library location).
```shell
java -Djdk.gtk.version=2 --module-path /home/student/javafx-sdk-11.0.2/javafx --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -jar kemuKepu.jar
```

### Running via Eclipse ###
#### 1. Give QuizFunctionality.sh Executable Permissions ####
1. In a new terminal, change directory into project folder
2. Run code below to give executable permissions to script file
```shell
chmod +x src/script/QuizFunctionality.sh
```

#### 2. Configure Eclipse Java Project to Run JavaFX Project ####
1. Right click on project > build path > configure build path.
2. Add user's JavaFX11 library under Classpath (this is default in .classpath file but often needs to be reset in Eclipse)
3. Right click on project > run as > run configurations > arguments  
4. Add code below into VM arguments.  
(Remember to change */home/student/javafx-sdk-11.0.2/javafx* to your own javaFX library location).
```shell
 --module-path /home/student/javafx-sdk-11.0.2/javafx --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml
```

5. ***If running on the SOFTENG206 Virtual Machine***, quiz sometimes renders improperly so instead add this code to VM arguments  
(Remember to change */home/student/javafx-sdk-11.0.2/javafx* to your own javaFX library location).
```shell
 -Djdk.gtk.version=2 --module-path /home/student/javafx-sdk-11.0.2/javafx --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml
```
6. Apply and run
- - - -
## To play Kēmu Kupu
### Menu options
Game Modules - allows user to select their chosen topic to revise.  
Quit - quits application.

### Gameplay
1. Click to begin quiz,
2. Adjust slider to change playback speed.
3. Press speaker to play the word.
4. Player can press 'don't know' to skip the current word.
4. Enter spelling of word in text box and press submit (*Note*: player can repeat steps 1-2 as many times as they want before submitting).
5. If correct on the first go, player continues to next word. Player's score will increase by 1.
6. If incorrect, player gets one more attempt -quiz will play the word twice and user will be given the second letter of the word.
7. If correct, player continues to next word. Player's score will increase by 1.
8. If incorrect again, player continues to next word.
9. Continue until quiz is complete
