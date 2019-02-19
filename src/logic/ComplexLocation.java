package logic;

import models.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ComplexLocation extends SymbolicLocation {
    private List<SymbolicLocation> locations;

    public ComplexLocation(List<SymbolicLocation> locations) {
        super();
        this.locations = locations;
    }

    public List<SymbolicLocation> getLocations() {
        return locations;
    }

    public List<Guard> getInvariants() {
        List<Guard> invariantList = new ArrayList<>();

        for (SymbolicLocation loc : locations) {
            invariantList.addAll(loc.getInvariants());
        }

        return invariantList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexLocation that = (ComplexLocation) o;
        return Arrays.equals(locations.toArray(), that.locations.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(locations);
    }
}