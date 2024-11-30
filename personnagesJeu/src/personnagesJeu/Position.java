package personnagesJeu;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }

    public Position clone() {
        return new Position(this.x, this.y);
    }

    public double calculerDistance(Position autrePosition) {
        int deltaX = this.x - autrePosition.getX();
        int deltaY = this.y - autrePosition.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }


}
