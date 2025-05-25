package com.shonejin.minesweeper.game;

import com.shonejin.minesweeper.game.states.CellStates;
import com.shonejin.minesweeper.game.states.GameStates;
import com.shonejin.minesweeper.gfx.Assets;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Board {
  private int N;
  
  private int NMines;
  
  private int NCovered;
  
  private GameStates gameState;
  
  private boolean[][] isMine;
  
  private int[][] mineCnt;
  
  private CellStates[][] states;
  
  private final int[] di = new int[] { -1, -1, -1, 1, 1, 1 };
  
  private final int[] dj = new int[] { -1, 1, 1, 1, -1, -1 };
  
  private final CellStates[] uncoveredStates = new CellStates[] { CellStates.UNC0, CellStates.UNC1, CellStates.UNC2, CellStates.UNC3, 
      CellStates.UNC4, CellStates.UNC5, CellStates.UNC6, CellStates.UNC7, CellStates.UNC8 };
  
  public Board(int N, int NMines) {
    if (N < 10 || N > 1000 || NMines < 1 || NMines > N * N) {
      N = 30;
      NMines = 100;
    } 
    this.N = N;
    this.NCovered = N * N;
    this.NMines = NMines;
    this.isMine = new boolean[N][N];
    this.mineCnt = new int[N + 2][N + 2];
    this.states = new CellStates[N][N];
    putMines();
    for (int i = 0; i < N; i++)
      Arrays.fill((Object[])this.states[i], CellStates.COVERED); 
    this.gameState = GameStates.ONGOING;
  }
  
  private void putMines() {
    Random rand = new Random();
    int mines = this.NMines;
    while (mines-- > 0) {
      int pos = rand.nextInt(this.NCovered);
      int x = pos % this.N;
      int y = pos / this.N;
      if (this.isMine[y][x]) {
        mines++;
        continue;
      } 
      this.isMine[y][x] = true;
      for (int d = 0; d < this.di.length; d++)
        this.mineCnt[y + this.di[d] + 1][x + this.dj[d] + 1] = this.mineCnt[y + this.di[d] + 1][x + this.dj[d] + 1] + 1; 
    } 
  }
  
  private void uncoverAll(Graphics g, boolean won) {
    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++) {
        if (this.states[i][j] == CellStates.COVERED && this.isMine[i][j]) {
          this.states[i][j] = won ? CellStates.FLAGGED : CellStates.MINE;
          Assets.draw(i, j, this.states[i][j], g);
        } else if (this.states[i][j] == CellStates.FLAGGED && !this.isMine[i][j]) {
          this.states[i][j] = CellStates.WRONG_FLAG;
          Assets.draw(i, j, this.states[i][j], g);
        } 
      } 
    } 
  }
  
  private void bfs(int row, int col, Graphics g) {
    Queue<Integer> q = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();
    this.NCovered++;
    q.add(Integer.valueOf(row * this.N + col));
    visited.add(Integer.valueOf(row * this.N + col));
    while (!q.isEmpty()) {
      int r = ((Integer)q.peek()).intValue() / this.N;
      int c = ((Integer)q.poll()).intValue() % this.N;
      if (this.states[r][c] != CellStates.COVERED)
        continue; 
      this.states[r][c] = this.uncoveredStates[this.mineCnt[r + 1][c + 1]];
      Assets.draw(r, c, this.states[r][c], g);
      this.NCovered--;
      if (this.states[r][c] != CellStates.UNC0)
        continue; 
      for (int i = 0; i < this.di.length; i++) {
        int _r = r + this.di[i];
        int _c = c + this.dj[i];
        int key = _r * this.N + _c;
        if (_r >= 0 && _r < this.N && _c >= 0 && _c < this.N && !visited.contains(Integer.valueOf(key))) {
          q.add(Integer.valueOf(key));
          visited.add(Integer.valueOf(key));
        } 
      } 
    } 
    if (this.NCovered == this.NMines)
      this.gameState = GameStates.WON; 
  }
  
  public boolean uncoverCell(int row, int col, Graphics g) {
    if (this.states[row][col] != CellStates.COVERED)
      return false; 
    if (this.isMine[row][col]) {
      this.gameState = GameStates.LOST;
      uncoverAll(g, false);
      this.states[row][col] = CellStates.FIRED_MINE;
      Assets.draw(row, col, CellStates.FIRED_MINE, g);
    } else {
      this.NCovered--;
      Assets.draw(row, col, this.uncoveredStates[this.mineCnt[row + 1][col + 1]], g);
      if (this.NCovered == this.NMines) {
        this.gameState = GameStates.WON;
        uncoverAll(g, true);
      } else {
        bfs(row, col, g);
      } 
    } 
    g.dispose();
    return true;
  }
  
  public void toggleFlag(int row, int col, Graphics g) {
    if (this.states[row][col] == CellStates.COVERED) {
      this.states[row][col] = CellStates.FLAGGED;
    } else if (this.states[row][col] == CellStates.FLAGGED) {
      this.states[row][col] = CellStates.COVERED;
    } 
    Assets.draw(row, col, this.states[row][col], g);
    g.dispose();
  }
  
  public GameStates getGameState() {
    return this.gameState;
  }
  public boolean isMine(int row, int col){
    return this.isMine[row][col];
  }
}
