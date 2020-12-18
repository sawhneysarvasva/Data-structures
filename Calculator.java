//Evaluation string can have +,-,*,/
//Evaluation string can have closing braket/opening bracket
//Evaluation string works on decimal number
//Evaluation string must not have any spaces

import java.util.*;
import java.lang.*;
import java.io.*;

class Evaluator
{
	public static void main (String[] args) throws java.lang.Exception
	{
		System.out.println(calculate("(3.3*2+1)/11"));
	}
	  public static double calculate(String s) {
    double l1 = 0, o1 = 1;
    double l2 = 1, o2 = 1;

    Deque<Double> stack = new ArrayDeque<>(); // stack to simulate recursion

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);

        if (Character.isDigit(c)) {
            double num = c - '0';

            while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                num = num * 10 + (s.charAt(++i) - '0');
            }
            if(i + 1 < s.length()&&s.charAt(i + 1)=='.')
            {
                double decimal=0;
                i++;
                int division=10;
                while( i + 1 < s.length() && Character.isDigit(s.charAt(i + 1)))
                {
                    decimal=decimal+(double)(s.charAt(++i) - '0')/division;
                    division=division*10;
                }
                num=num+decimal;
                
            }
            

            l2 = (o2 == 1 ? l2 * num : l2 / num);

        } else if (c == '(') {
            // First preserve current calculation status
            stack.offerFirst(l1); stack.offerFirst(o1);
            stack.offerFirst(l2); stack.offerFirst(o2);
            
            // Then reset it for next calculation
            l1 = 0; o1 = 1;
            l2 = 1; o2 = 1;

        } else if (c == ')') {
            // First preserve the result of current calculation
            double num = l1 + o1 * l2;

            // Then restore previous calculation status
            o2 = stack.poll(); l2 = stack.poll();
            o1 = stack.poll(); l1 = stack.poll();
            
            // Previous calculation status is now in effect
            l2 = (o2 == 1 ? l2 * num : l2 / num);

        } else if (c == '*' || c == '/') {
            o2 = (c == '*' ? 1 : -1);

        } else if (c == '+' || c == '-') {
            l1 = l1 + o1 * l2;
            o1 = (c == '+' ? 1 : -1);

            l2 = 1; o2 = 1;
        }
    }

    return (l1 + o1 * l2);
}
}
