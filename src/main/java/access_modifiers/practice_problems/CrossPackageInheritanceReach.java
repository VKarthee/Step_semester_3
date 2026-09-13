
import java.util.Scanner;

class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }

            if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }

            if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")) {
                return "DENIED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String describeContext(String accessorContext) {
        String[] words = accessorContext.split("_");

        String result = "";

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();

            String first = word.substring(0, 1).toUpperCase();

            String remaining = word.substring(1);

            if (i > 0) {
                result = result + " ";
            }

            result = result + first + remaining;
        }

        return result;
    }
}

public class q2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String fieldModifier = sc.next();

        String accessorContext = sc.next();

        String result = AccessChecker.classifyAccess(fieldModifier, accessorContext);

        System.out.println(result);

        System.out.println(AccessChecker.describeContext(accessorContext));

        sc.close();
    }
}
