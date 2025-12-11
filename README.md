# Pang Game

A retro-style, action-packed arcade game built with Java.  
Destroy bouncing bubbles, survive through the chaos, and progress through 5 increasing difficulty levels.

## Features

- Classic Pang-style bubble mechanics  
- Java Swing GUI-based gameplay  
- Multiple levels with different background music and design  
- Sound effects for popping, starting, leveling up, and winning  
- Pause and mute functionality  
- Score and life tracking  
- "Play Without Save" mode  

## File Structure

- `GamePanel.java`: Main game loop, rendering, key events  
- `GameFrame.java`: UI and menu logic  
- `Player.java`: Player movement and shooting  
- `Bubble.java`: Bubble behavior and splitting  
- `Arrow.java`: Arrow movement and collisions  
- `Member.java`: Holds player data (level, score, lives)  
- `User.java`: Login, register, save/load from `log.txt`  
- `log.txt`: User data file
- `assets`: Are located in /src file 

## Controls

- `← →` or `A / D` — Move left/right  
- `Space / ↑ / W` — Shoot  
- `ESC` — Pause / Resume  
- `M` — Mute / Unmute  

## How to Run

1. Make sure you have **Java 17+** installed.
2. Compile all Java files:

   ```bash
   javac Main.java
   ```

3. Run the game:

   ```bash
   java Main.java
   ```
 
## License

MIT License.  
Free to use, modify, or distribute.

## Credits

Created by: Ege Huriel  
Yeditepe University – CSE 212 Term Project  
Email: ege.huriel@gmail.com
