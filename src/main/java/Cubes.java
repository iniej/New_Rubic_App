/**
 * Created by Iniebiyo Joshua on 4/24/2017.
 */
public class Cubes {
    String name;
    double time;

    Cubes (String n, double t){
        name = n;
        time = t;
    }
    @Override
    public String toString(){
        return ("Cube Solver's name: "+ name + "," + " Time taken to solve: " + time);
    }
}




