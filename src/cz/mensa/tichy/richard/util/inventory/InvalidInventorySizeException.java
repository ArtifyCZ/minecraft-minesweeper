package cz.mensa.tichy.richard.util.inventory;

public class InvalidInventorySizeException extends Exception
{
    private final int sizeX;

    private final int sizeY;

    private final String msg;

    public InvalidInventorySizeException(int sizeX, int sizeY, String message)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.msg = message;
    }

    public int getSizeX()
    {
        return this.sizeX;
    }

    public int getSizeY()
    {
        return this.sizeY;
    }

    @Override
    public String getMessage()
    {
        return this.msg;
    }
}
