public class ScoreObject implements java.io.Serializable
{
    String name;
    int score;

    public ScoreObject(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    public String toString()
    {
        return name + " " + score;
    }
}