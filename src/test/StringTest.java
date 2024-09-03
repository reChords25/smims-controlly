package test;

public class StringTest {

    public static void main(String[] args) {

        String data = "#131,14,-2,1000,0?"; // x1, y1, x2, y2, btn

        int indexBegin = data.indexOf("#");
        if (indexBegin < 0) {
            return;
        }
        System.out.println(data);
        data = data.substring(indexBegin + 1);
        System.out.println(data);
        int indexEnd = data.indexOf("?");
        if (indexEnd < 0) {
            return;
        }
        System.out.println(data);
        data = data.substring(0, indexEnd);
        System.out.println(data);

        System.out.println("Hallo");
        String[] dataArray = null;

        dataArray = data.split(",");

        for (String s : dataArray) {
            System.out.print(s + " ");
        }

    }
}
