package d;
import java.util.*;
public class arithmetic {
    private static int intercePosition = 0; //
    private static int[] intercePositionIndex = null;
    private static int[] intercePositionEnd = null;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("请输入你要计算的算式:");
            String numberString = input.next().trim();
            if (ispassString(numberString) == false) {
                System.out.println("您输入的计算字符串有误,请正确输入!");
            } else {
                System.out.println(interceResult(numberString));
            }
        } while (true);
    }

    private static String interceResult(String str) {
        String result = str;
        char[] numberString = str.toCharArray();
        int IndexStart = 0;
        int EndStart = 0;
        for (int i = 0; i < numberString.length; i++) {
            if ('(' == numberString[i]) {
                IndexStart = i;
            }
            if (')' == numberString[i]) {

                EndStart = i;

                result = result.substring(IndexStart + 1, EndStart);

                result = str.substring(0, IndexStart)
                        + interceptOperation(result, '*', '/')
                        + str.substring(EndStart + 1, str.length());


                return interceResult(result);
            }
            if (i == numberString.length - 1)
                if (EndStart == 0)
                    break;
        }

        result = interceptOperation(str, '*', '/');
        return result;
    }


    private static String interceptOperation(String operationNumber, char a,
                                             char b) {
        String mess = operationNumber;
        char[] stringOperation = mess.toCharArray();


        for (int i = 0; i < stringOperation.length; i++) {

            if (stringOperation[i] == a || stringOperation[i] == b) {
                if (i != 0) {
                    double num1 = interceptNumIndex(mess.substring(0, i));
                    int frontPosition = intercePosition;
                    double num2 = interceptNumEnd(mess.substring(i + 1,
                            stringOperation.length));
                    int backPosition = intercePosition;
                    String IndexMess = mess.substring(0, i - frontPosition + 1);
                    String IndexResult = "";

                    if (IndexMess.indexOf('+') == -1
                            && IndexMess.indexOf('*') == -1
                            && IndexMess.indexOf('/') == -1
                            && IndexMess.lastIndexOf('-') == -1)
                        IndexMess = "";
                    if (IndexMess != "")
                        IndexResult = IndexMess.lastIndexOf('-') == IndexMess
                                .length() - 1 ? IndexMess.substring(0, i
                                - frontPosition) : IndexMess;

                    // 组装新的运算字符串
                    mess = IndexResult// mess.substring(0,i-frontPosition+1)
                            + reslutString("" + stringOperation[i], num1, num2)
                            + mess.substring(i + backPosition + 1);
                    // 0.111/1212/2/2/2/2/2/2/2
                    if (mess.lastIndexOf('-') == 0 && mess.indexOf('+') == -1
                            && mess.indexOf('*') == -1
                            && mess.indexOf('/') == -1) {
                        break;
                    }

                    return interceptOperation(mess, a, b);
                } else
                    continue;
            }
            if (i == stringOperation.length - 1) {
                if (mess.indexOf('+') != -1 || mess.indexOf('-') != -1)
                    return interceptOperation(mess, '+', '-');
                break;
            }
        }
        return mess;
    }
    private static double interceptNumEnd(String str) {
        double a = 0;
        int InrerceIndex = 0;
        char[] stringOperation = str.toCharArray();
        boolean ispas = false;
        for (int i = 0; i < stringOperation.length; i++) {
            switch (stringOperation[i]) {
                case '*':
                case '/':
                case '+':
                case '-':
                    InrerceIndex = i;
                    if (i != 0)
                        ispas = true;
                    break;
                default:
                    break;
            }
            if (ispas)
                break;
        }
        if (InrerceIndex == 0) {
            a = Double.parseDouble(str);
            intercePosition = str.length();
            if (ispas)
                intercePosition++;

        } else {
            a = Double.parseDouble(str.substring(0, InrerceIndex));
            intercePosition = str.substring(0, InrerceIndex).length();
        }
        return a;
    }
    private static double interceptNumIndex(String str) {
        double a = 0;
        int InrerceIndex = 0;
        boolean temp = false;
        char[] stringOperation = str.toCharArray();
        for (int i = stringOperation.length - 1; i >= 0; i--) {
            switch (stringOperation[i]) {
                case '*':
                case '/':

                case '+':
                case '-':
                    InrerceIndex = i;
                    temp = true;
                    break;
                default:
                    break;
            }
            if (temp)
                break;
        }
        if (InrerceIndex == 0) {
            a = Double.parseDouble(str);
            intercePosition = str.length();
        } else {
            a = Double.parseDouble(str.substring(InrerceIndex, str.length()));
            intercePosition = str.substring(InrerceIndex, str.length())
                    .length();
        }
        return a;
    }


    private static double reslutString(String operation, double num1,
                                       double num2) {
        double sumResult = 0;
        if (operation.equals("*"))
            sumResult = num1 * num2;
        if (operation.equals("-"))
            sumResult = num1 - num2;
        if (operation.equals("/"))
            sumResult = num1 / num2;
        if (operation.equals("+"))
            sumResult = num1 + num2;
        return sumResult;
    }
    private static boolean ispassString(String messString) {
        boolean ispass = false;
        boolean operationIspass = true;
        int ai = 0;
        char[] IsString = messString.toCharArray();
        int num1 = 0;
        int num2 = 0;
        for (int i = 0; i < IsString.length; i++) {
            if ('(' == IsString[i])
                num1++;
            if (')' == IsString[i])
                num2++;


            if ('/' == IsString[i] && IsString[i + 1] == '0')
                operationIspass = false;

            if (IsString[i] == '+' || IsString[i] == '-' || IsString[i] == '*'
                    || IsString[i] == '/')
                ai++;

            if (i == IsString.length - 1)
                if (ai == 0)
                    num2++;
        }
        if (operationIspass)
            if (num1 == num2)
                ispass = true;
        return ispass;
    }
}