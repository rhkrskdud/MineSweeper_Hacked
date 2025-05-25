package com.shonejin.minesweeper.game;

import com.shonejin.minesweeper.game.states.CellStates;
import com.shonejin.minesweeper.game.states.GameStates;
import com.shonejin.minesweeper.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game {
  private Display display;
  
  private int width;
  
  private int height;
  
  private int N;
  
  public String title;
  
  private boolean finished;
  
  private Board board;
  
  private MouseManager mouseManager;
  
  private BufferStrategy bs;
  
  public Game(String title, int N, int NMines) {
    this.N = N;
    this.width = 16 * N;
    this.height = this.width;
    this.title = title;
    this.board = new Board(N, NMines);
    this.mouseManager = new MouseManager(this);
    this.display = new Display(title, this.width, this.height);
    this.display.getFrame().addMouseListener(this.mouseManager);
    this.display.getCanvas().addMouseListener(this.mouseManager);
    this.display.getCanvas().createBufferStrategy(2);
    this.bs = this.display.getCanvas().getBufferStrategy();
    Assets.init();
  }
  
  public void onClick(boolean isLeft, int x, int y) {
    if (this.finished)
      return; 
    int row = y / 16;
    int col = x / 16;
    Graphics g = this.bs.getDrawGraphics();
    if (isLeft) {
      this.board.uncoverCell(row, col, g);
    } else {
      this.board.toggleFlag(row, col, g);
    } 
    this.bs.show();
    GameStates result = this.board.getGameState();
    if (result != GameStates.ONGOING) {
      this.finished = true;
      System.out.println("Game ended!");
      String msg = (result == GameStates.LOST) ? "!!!!! You Lose !!!!!" : "!!!!! You Won !!!!!";
      this.display.getFrame().setTitle(msg);
    } 
    g.dispose();
  }
  
  public void start() {
    Graphics g = this.bs.getDrawGraphics();
    for (int i = 0; i < this.N; i++) {
      for (int j = 0; j < this.N; j++)
	if(this.board.isMine(i,j)){
         Assets.draw(i, j, CellStates.FLAGGED, g);
	}else{
	  Assets.draw(i, j, CellStates.COVERED, g);
	}
    } 
    this.bs.show();
    g.dispose();
  }
}