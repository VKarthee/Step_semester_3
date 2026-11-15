
import java.util.*;

interface Reservable {

    void reserve(String memberId);
}

interface Downloadable {

    void download();
}

abstract class Resource {

    protected String id;

    Resource(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

class Book extends Resource implements Reservable {

    Book(String id) {
        super(id);
    }

    public void reserve(String memberId) {
        System.out.println(id + " reserved for " + memberId);
    }
}

class EBook extends Resource implements Downloadable {

    EBook(String id) {
        super(id);
    }

    public void download() {
        System.out.println(id + " downloaded");
    }
}

public class LibraryPlatform {

    private ArrayList<Resource> resources = new ArrayList<>();

    public void addResource(Resource resource) {
        int index = findResource(resource.getId());

        if (index != -1) {
            System.out.println("duplicate rejected");
            return;
        }

        resources.add(resource);
        resources.sort(Comparator.comparing(Resource::getId));

        System.out.println(resource.getId() + " added");
    }

    public int findResource(String id) {
        int low = 0;
        int high = resources.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int result = resources.get(mid).getId().compareTo(id);

            if (result == 0) {
                return mid;
            } else if (result < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public void reserve(String id, String memberId) {
        int index = findResource(id);

        if (index == -1) {
            System.out.println(id + " not found");
            return;
        }

        Resource resource = resources.get(index);

        if (resource instanceof Reservable) {
            ((Reservable) resource).reserve(memberId);
        } else {
            System.out.println(id + " rejected: reserve unsupported");
        }
    }

    public void download(String id) {
        int index = findResource(id);

        if (index == -1) {
            System.out.println(id + " not found");
            return;
        }

        Resource resource = resources.get(index);

        if (resource instanceof Downloadable) {
            ((Downloadable) resource).download();
        } else {
            System.out.println(id + " rejected: download unsupported");
        }
    }

    public void find(String id) {
        int index = findResource(id);

        if (index == -1) {
            System.out.println(id + " not found");
        } else {
            System.out.println(id + " found at index " + index);
        }
    }

    public static void main(String[] args) {
        LibraryPlatform library = new LibraryPlatform();

        library.addResource(new Book("B1"));
        library.addResource(new Book("B1"));
        library.addResource(new EBook("E1"));

        library.reserve("B1", "M1");
        library.download("B1");
        library.download("E1");
        library.find("E1");
    }
}
