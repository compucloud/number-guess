# number-guess

<b>Description:</b> This simple application is a number guessing game.  

The user chooses a number in his mind and types “ready” to indicate
to the computer that he is ready to begin playing. The computer asks a series of questions to 
arrive at the number the user has in mind. The user can only respond with ('h','l','y' or 'e') 
h = higher, l = lower, y = yes, e = end. The game ends when the user responds with “yes” or “end”.
 
The program uses a binary search approach to guess the number the user has choosen. It uses the halfway point between the highest and lowest number guessed so that on average it arrives to the answer with the shortest number of questions.
 
A guessing range minimum and maximum has been put in place to keep the game fun and also prevent the user from having the computer try to guess numbers too large for memory. The guesser maximum can be increased to any number that can fit
into a primitive int data type through the the NumberGuesser(int max) constructor.

<b>Requirements</b> 
<ul>
<li>JDK 1.7 or higher</li>
<li>Maven 3.2.5 or higher</li>
</ul>

<b>Compilation Steps (command line) </b>

1. cd [app-home-dir]
2. mvn clean install

This will compile the application an run its unit test. The jar file number-guess-1.0.0-SNAPSHOT.jar will
be created and placed in the [app-home-dir]/target directory.

<b>Execute the Program</b>

1. Comple the program using the steps outlined above in the <i>Compilation Steps</i> section. 
2. cd [app-home-dir]/target
3. java - jar number-guess-1.0.0-SNAPSHOT.jar

The application will start. Follow the instructions to play the game. Have fun!
