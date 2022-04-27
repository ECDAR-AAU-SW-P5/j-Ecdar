package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AndGuard extends Guard{

    private List<Guard> guards;

    public AndGuard(List<Guard> guards)
    {
        this.guards=guards;
    }
    public AndGuard(List<Guard>... guards)
    {
        this.guards= new ArrayList<>();
        for (List<Guard> g: guards)
            this.guards.addAll(g);

        for (Guard g: this.guards)
            if (g instanceof TrueGuard)
                this.guards.remove(g);
        if (this.guards.isEmpty())
            this.guards.add(new TrueGuard());
    }
    public AndGuard(Guard... guards)
    {
        this.guards= new ArrayList<>();
        for (Guard g: guards)
            this.guards.add(g);
        for (Guard g: this.guards)
            if (g instanceof TrueGuard)
                this.guards.remove(g);
        if (this.guards.isEmpty())
            this.guards.add(new TrueGuard());

    }

    public AndGuard(AndGuard copy, List<Clock> newClocks,List<Clock> oldClocks,   List<BoolVar> newBVs, List<BoolVar> oldBVs)
    {
        this.guards=new ArrayList<>();
        for (Guard g : copy.guards)
        {
            if (g instanceof ClockGuard)
                this.guards.add(new ClockGuard((ClockGuard) g,  newClocks,oldClocks));
            if (g instanceof BoolGuard)
                this.guards.add(new BoolGuard((BoolGuard) g, newBVs, oldBVs));
            if (g instanceof FalseGuard)
                this.guards.add(new FalseGuard());
            if (g instanceof TrueGuard)
                this.guards.add(new TrueGuard());
            if (g instanceof AndGuard)
                this.guards.add(new AndGuard( (AndGuard) g, newClocks, oldClocks, newBVs, oldBVs));
            if (g instanceof OrGuard)
                this.guards.add(new OrGuard( (OrGuard) g, newClocks, oldClocks, newBVs, oldBVs));
        }
        for (Guard g: this.guards)
            if (g instanceof TrueGuard)
                this.guards.remove(g);
        if (this.guards.isEmpty())
            this.guards.add(new TrueGuard());

    }


    @Override
    int getMaxConstant() {
        int max = 0;
        for (Guard g: guards)
        {
            if (g.getMaxConstant()>max)
                max = g.getMaxConstant();
        }
        return max;
    }

    @Override
    public boolean equals(Object o) { // TODO: AND(G1,G2) != AND(G2,G1) => is that okay?
        if (!(o instanceof AndGuard))
            return false;
        AndGuard other = (AndGuard) o;
        if (other.guards.size()!=guards.size())
            return  false;
        for (int i =0; i<= other.guards.size(); i++)
            if (!guards.get(i).equals(other.guards.get(i)))
                return false;
        return true;
    }

    @Override
    public String toString() {




        String ret = "(";
        for (Guard g: guards)
            ret += g.toString() + " && ";
        if (guards.size()==0)
            return "";
        if (guards.size()==1)
            return guards.get(0).toString();
        return ret.substring(0,ret.length()-4) + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(false);
    }

    public List<Guard> getGuards() {
        return guards;
    }
}
