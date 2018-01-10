package Classes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Scope("session")
public class Tictactoe {
    private ArrayList<Integer> crosses;
    private int size;
    private int possibleMoves;

    public Tictactoe(int size) {
        this.size = size;
        this.possibleMoves = size * size;
        this.crosses = new ArrayList<Integer>();
        for (int i = 0; i < size * size; i++)
            this.crosses.add(-1);
    }

    public ArrayList<Integer> getCrosses() {
        return crosses;
    }

    public void setCrosses(int side, int movePosition) {
        crosses.set(movePosition, side);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(int possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
