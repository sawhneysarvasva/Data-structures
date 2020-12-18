using System;
using System.Collections;
using System.Collections.Generic;
public class Evaluator
{
	public static void Main()
	{
		// your code goes here
		Console.WriteLine(Calculate("(3+4)+(4.1*5)"));
	}
	public static double Calculate(string s) 
{
    if (s == null || s.Length == 0)
        return 0;
    
    double num = 0;
    char sign = '+';
    var st = new Stack<double>();
    
    for (int i = 0; i < s.Length; i++)
    {
        char c  = s[i];
                      
        if (c >= '0' && c <= '9')
        {
             num=c-'0';
            while(i+1<s.Length && (s[i+1]>='0' && s[i+1]<='9'))
            {
                num = num * 10 + (double)(s[++i] - '0');
            }
            if(i+1<s.Length && s[i+1]=='.')
            {
                double d=0;
                i++;
                int division=10;
                while( i + 1 < s.Length &&  (s[i+1]>='0' && s[i+1]<='9'))
                {
                    d=d+(double)(s[++i] - '0')/division;
                    division=division*10;
                }
                num=num+d;
            }
           
        }
        else if (c == '(')
        {
            // parse expression in brackets, number of open and closed brackets should match
            int j = i + 1;
            int braces = 1;
            for (; j < s.Length; j++)
            {
                if (s[j] == '(') 
                    ++braces;
                if (s[j] == ')') 
                    --braces;
                if (braces == 0)
                    break;
            }
            // calculate expression in brackets [i+1,j]
            num = Calculate(s.Substring(i + 1, j - i));
            // skip calculation as it is calculated above
            i = j;                
        }
        
        // parse operations
        if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.Length - 1)
        {
            switch (sign)
            {
                case '+':
                    st.Push(num);
                    break;
                case '-':
                    st.Push(-num);
                    break;
                case '*':
                    st.Push(st.Pop() * num);
                    break;
                case '/':
                    st.Push(st.Pop() / num);
                    break;                        
            }
            // important to be 0 as we finish parsing operands
            num = 0;
            sign = c;
        }
    }
            
    // calculate result
    double result = 0;
    while (st.Count > 0)
        result += st.Pop();
    
    return result;        
}
}
