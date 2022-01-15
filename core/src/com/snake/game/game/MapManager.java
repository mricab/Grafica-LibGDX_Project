package com.snake.game.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.snake.game.models.Board;
import com.snake.game.models.Snake;
import com.snake.game.primitives.CellType;
import com.snake.game.primitives.Direction;
import com.snake.game.primitives.Position;


public class MapManager {

    private static MapManager instance = new MapManager();

    public static MapManager instance() {
        return instance;
    }

    public Board LoadMap(String address, Snake snake) {

        try {

            FileHandle handle = Gdx.files.internal(address);
            File Map = handle.file();
            Scanner Reader = new Scanner(Map);

            int rows = Integer.parseInt(Reader.nextLine());
            int cols = Integer.parseInt(Reader.nextLine());
            Board Board = new Board(rows, cols, snake);

            for (int i = 0; i < rows; i++) {
                String s = Reader.nextLine();
                String[] rowData = s.split(GameState.MAP_SEPARATOR);
                for (int j = 0; j < cols; j++) {
                    Integer code = Integer.parseInt(rowData[j]);
                    CellType type = CellType.values()[code];
                    Board.setCell(type, Direction.RIGHT, new Position(j, i));
                }
            }
            Reader.close();

            return Board;

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }
    }
}


