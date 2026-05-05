import java.util.Scanner;

public class OrganizationApp {

    private static Group root = null;
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = in.nextLine().trim();

            try {
                if (choice.equalsIgnoreCase("q")) {
                    running = false;
                } else if (choice.equals("1")) {
                    handleCreateAndPrint();
                } else if (choice.equals("2")) {
                    handleAddPerson();
                } else if (choice.equals("3")) {
                    handleRemovePerson();
                } else {
                    throw new InvalidMenuChoiceException("Wrong choice, try again.");
                }
            } catch (InvalidMenuChoiceException e) {
                System.out.println(e.getMessage());
            } catch (OrganizationNotCreatedException e) {
                System.out.println(e.getMessage());
            } catch (GroupNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InvalidPersonNameException e) {
                System.out.println(e.getMessage());
            } catch (PersonNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Process finished with exit code 0");
    }

    private static void printMenu() {
        System.out.println("Organization management system");
        System.out.println("------------------------------");
        System.out.println("1. Create and print hard coded organization");
        System.out.println("2. Print organization, add person to it and finally print it");
        System.out.println("3. Print organization, remove person from it and finally print it");
        System.out.println("Q. Quit the application");
        System.out.print("Your choice: ");
    }

    private static void handleCreateAndPrint() {
        createHardCodedOrganization();
        root.print();
    }

    private static void handleAddPerson()
            throws OrganizationNotCreatedException, GroupNotFoundException, InvalidPersonNameException {

        ensureOrganizationCreated();

        System.out.println();
        root.print();

        System.out.print("\nGive the name of the group where to add the person: ");
        String groupName = in.nextLine().trim();

        Group target = root.findGroupByName(groupName);
        if (target == null) {
            throw new GroupNotFoundException("No such group in the organization.");
        }

        System.out.print("Give the name of the person to add (Firstname Lastname): ");
        String personName = in.nextLine().trim();
        validatePersonName(personName);

        Person p = new Person(personName);
        target.addMember(p);

        System.out.println("\nOrganization after adding:");
        root.print();
    }

    private static void handleRemovePerson()
            throws OrganizationNotCreatedException, InvalidPersonNameException, PersonNotFoundException {

        ensureOrganizationCreated();

        System.out.println();
        root.print();

        System.out.print("\nGive the name of the person to remove (Firstname Lastname): ");
        String personName = in.nextLine().trim();
        validatePersonName(personName);

        boolean removed = root.removePersonByName(personName);
        if (!removed) {
            throw new PersonNotFoundException("Person does not exist in the organization.");
        }

        System.out.println("\nOrganization after removing:");
        root.print();
    }

    private static void ensureOrganizationCreated() throws OrganizationNotCreatedException {
        if (root == null) {
            throw new OrganizationNotCreatedException(
                    "Create the organization first with choice 1 before using this function.");
        }
    }

    private static void validatePersonName(String name) throws InvalidPersonNameException {
        // Format: Firstname Lastname, first letter uppercase, others lowercase
        if (!name.matches("[A-Z][a-z]+ [A-Z][a-z]+")) {
            throw new InvalidPersonNameException(
                    "Person name must be in format: Firstname Lastname (first letters uppercase, others lowercase).");
        }
    }

    private static void createHardCodedOrganization() {
        // root level 0
        root = new Group("Duckburg Corporation", "Scrooge McDuck");
        root.setLevel(0);

        // level 1 groups
        Group sales = new Group("Sales Department", "Donald Duck");
        Group it = new Group("IT Department", "Gyro Gearloose");
        Group hr = new Group("HR Department", "Daisy Duck");

        root.addMember(sales);
        root.addMember(it);
        root.addMember(hr);

        // level 2 persons under Sales
        sales.addMember(new Person("Huey Duck", "Salesperson"));
        sales.addMember(new Person("Dewey Duck", "Salesperson"));
        sales.addMember(new Person("Louie Duck", "Salesperson"));

        // level 2 persons under IT
        it.addMember(new Person("Fenton Crackshell", "Developer"));
        it.addMember(new Person("Launchpad Mcquack", "Support"));

        // level 2 persons under HR
        hr.addMember(new Person("Gladstone Gander", "Recruiter"));

        // level 2 subgroup under IT
        Group infra = new Group("Infrastructure Team", "Ludwig Von Drake");
        it.addMember(infra);

        // level 3 persons under Infrastructure
        infra.addMember(new Person("John Smith", "Sysadmin"));
        infra.addMember(new Person("Jane Doe", "Network Engineer"));
    }
}

