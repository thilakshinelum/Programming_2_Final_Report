public class Person extends Unit {

    private String role; // optional

    public Person(String name) {
        super(name);
        this.role = "";
    }

    public Person(String name, String role) {
        super(name);
        this.role = role;
    }

    @Override
    public void print() {
        String displayName = (role == null || role.isEmpty())
                ? name
                : name + " (" + role + ")";
        System.out.println(indentation() + displayName);
    }
}
