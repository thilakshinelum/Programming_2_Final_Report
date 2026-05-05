import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Group extends Unit {

    private String bossName;
    private List<Unit_IF> members;

    public Group(String groupName, String bossName) {
        super(groupName);
        this.bossName = bossName;
        this.members = new ArrayList<>();
    }

    public void addMember(Unit_IF unit) {
        unit.setLevel(this.level + 1);
        members.add(unit);
    }

    public boolean removePersonByName(String personName) {
        // remove direct leaf
        Iterator<Unit_IF> it = members.iterator();
        while (it.hasNext()) {
            Unit_IF u = it.next();
            if (u instanceof Person && u.getName().equals(personName)) {
                it.remove();
                return true;
            }
        }
        // recurse into subgroups
        for (Unit_IF u : members) {
            if (u instanceof Group) {
                if (((Group) u).removePersonByName(personName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Group findGroupByName(String groupName) {
        if (this.name.equals(groupName)) {
            return this;
        }
        for (Unit_IF u : members) {
            if (u instanceof Group) {
                Group found = ((Group) u).findGroupByName(groupName);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @Override
    public void print() {
        System.out.println(indentation() + name + " (boss: " + bossName + ")");
        for (Unit_IF u : members) {
            u.print();
        }
    }
}
