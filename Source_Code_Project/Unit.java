public abstract class Unit implements Unit_IF {

    protected String name;
    protected int level;

    public Unit(String name) {
        this.name = name;
        this.level = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    protected String indentation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  "); // two spaces per level
        }
        return sb.toString();
    }
}
