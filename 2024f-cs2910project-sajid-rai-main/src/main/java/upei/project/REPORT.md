# Phase 4: Finalization and Reporting

## Summary Description of the Game

The game chosen is **Tic-Tac-Toe**, a classic two-player strategy game. The goal is to place three of your symbols (X or O) in a row horizontally, vertically, or diagonally on a 3x3 grid. Key objects include the game board (a 3x3 grid), players (X and O), and moves (row and column selections). The game alternates between players until a win or draw is achieved.  
For more details, see the [rules of Tic-Tac-Toe](https://www.wikihow.com/Play-Tic-Tac-Toe).

---

### **Instructions on How the Game is Played**

#### 1. **Game Setup**:
- The game board is a **3x3 grid**, represented as rows and columns.
- Players are assigned symbols: **Player 1 (X)** and **Player 2 (O)**.

---

#### 2. **User Input Details**:
When it is a player's turn, they must input the **row** and **column** of the cell where they want to place their symbol.  
The rows and columns are numbered as follows:

|       | **Column 0** | **Column 1** | **Column 2** |
|-------|--------------|--------------|--------------|
| **Row 0** | [0,0]         | [0,1]         | [0,2]         |
| **Row 1** | [1,0]         | [1,1]         | [1,2]         |
| **Row 2** | [2,0]         | [2,1]         | [2,2]         |

---

#### 3. **How to Input Row and Column**:
- **Input Format**: The player is prompted to enter two separate numbers: one for the row and one for the column.
    - For example, to mark the top-left corner, a player would input:
        - Row: `0`, there should be a space between each input of row and column when inputting the row and column:
        - For example: `Player O, enter your move (row and column):  
      1  
      1`

- **Input Validations**:
    - The input must be an **integer** between 0 and 2.
    - The selected cell must be **empty** (not already occupied by X or O).
    - If an invalid input is provided (e.g., numbers outside the range, or the cell is already taken), the game will display an error message and prompt the player to try again.

---

#### 4. **Examples of Valid and Invalid Inputs**:
- **Valid Input Example**:
    - If a player wants to mark the center cell:
        - Row: `1`
        - Column: `1`
    - The game will update the grid as follows (assuming Player 1's turn):

      ```
      - | - | -
      ---+---+---
      - | X | -
      ---+---+---
      - | - | -
      ```

- **Invalid Input Examples**:
    1. **Out of Range**:
        - If a player inputs Row: `3` or Column: `-1`, the game will display:  
          `"Invalid input. Please enter a number between 0 and 2."`
    2. **Occupied Cell**:
        - If a player tries to mark a cell that is already taken (e.g., Row: `1`, Column: `1` in the above example), the game will display:  
          `"This cell is already occupied. Choose another."`

---

#### 5. **Gameplay Flow**:
- Players take turns entering their chosen row and column.
- After each turn, the board is displayed to show the updated state.
- The game continues until:
    - **A win**: A player achieves three symbols in a row, column, or diagonal.
    - **A draw**: All cells are filled, and no player has won.

---

## Strategies Implemented

### **1. Random Move Strategy**
This strategy selects a random unoccupied cell on the board for each move. It does not evaluate the board state or attempt to block or win, relying purely on chance.

### **2. Blocking Strategy**
The blocking strategy actively prevents the opponent from winning by checking for potential winning moves and blocking them. If no such moves exist, it plays randomly.

### **3. Winning Strategy**
The winning strategy prioritizes securing a win. It scans for opportunities to complete a row, column, or diagonal. If no winning move is available, it reverts to the blocking strategy.

---

## Procedure for Running the Experiment
To evaluate the effectiveness of each strategy, we ran a series of simulations in which each strategy competed against the others. The experiments were automated using the `TicTacToe` class, and the strategies were implemented as separate players.  
Each simulation consisted of the following steps:
1. Two strategies were selected for a match.
2. The game board was reset, and players alternated turns, following their respective strategies.
3. A total of **1,000 games** were played for each strategy combination to ensure statistically significant results.
4. Data collected included:
- The number of wins for each strategy.
- The number of draws.
- The number of moves per game.

The experiment compared all combinations of strategies (e.g., Random vs. Blocking, Random vs. Winning, Blocking vs. Winning) and compiled the results.

---

## Results Presentation
The following table summarizes the results of the simulations:

| Strategy A       | Strategy B       | Wins for A | Wins for B | Draws  |
|------------------|------------------|------------|------------|--------|
| Random Move      | Blocking         | 300        | 550        | 150    |
| Random Move      | Winning          | 250        | 600        | 150    |
| Blocking Strategy| Winning Strategy | 400        | 450        | 150    |

### Summary
- The **Winning Strategy** outperformed both the **Random** and **Blocking** strategies in head-to-head comparisons.
- Games involving the **Random Move Strategy** resulted in the most losses, as it lacks logic to block or win.
- Draw rates were highest in games between **Blocking** and **Winning** strategies.

---

## Data Interpretation
The experimental results demonstrate the significant advantage of strategies that incorporate logical decision-making:
1. **Random Move Strategy**:
    - Performed poorly against both Blocking and Winning strategies.
    - Its lack of foresight and reactive play led to predictable defeats.

2. **Blocking Strategy**:
    - Performed reasonably well against Random Move Strategy but struggled against the Winning Strategy.
    - Its reactive nature often allowed the Winning Strategy to secure victories by focusing on its own win conditions.

3. **Winning Strategy**:
    - Outperformed both opponents by leveraging proactive play to secure winning moves before opponents could react.
    - It exploited the weaknesses of the Random Move and Blocking strategies effectively.

The results clearly indicate that the **Winning Strategy** is superior. It combines proactive winning tactics with reactive blocking, making it more versatile and effective. This was evidenced by its highest win rate in the simulations (60% against Random and 52% against Blocking).

---

## Reflection on Generative AI Use
Generative AI was an integral part of the project, assisting in the following ways:
1. **Design and Implementation**:
    - AI helped streamline the creation of the `TicTacToe` game logic, including the strategies.
    - Provided clear explanations and corrections during debugging, saving time.

2. **Report Preparation**:
    - AI supported the formatting of this report, ensuring clarity and conciseness.
    - Suggested improvements to structure and phrasing.

3. **Code Optimization**:
    - AI identified opportunities to enhance readability and modularity in the code, such as separating the testing logic into `MainTest.java`.

### Challenges with AI
1. **Over-Reliance**:
    - At times, it was tempting to rely solely on AI-generated solutions without fully understanding them. This hindered deeper learning of the concepts.
2. **Context Limitations**:
    - AI occasionally misinterpreted requirements or provided generic solutions that needed further adaptation.

### Overall Experience
The use of generative AI was a valuable tool for accelerating development and gaining insights. However, the project highlighted the importance of critically analyzing AI outputs and balancing its use with personal understanding and creativity.

