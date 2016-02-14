package com.danInterview;

import com.danInterview.algorithm.FloydWarshall;
import com.danInterview.graph.CurrencyGraph;
import com.danInterview.io.RequestHandler;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    static Scanner input;
    static Pattern currencyPattern = Pattern.compile("^[A-Z]{3}$");
    FloydWarshall alg;

    public static void main(String[] args) {
        try{
            input = new Scanner(System.in);
            RequestHandler req = new RequestHandler();

            //FETCH the base currency (USD)
            System.out.print("Fetching Currency data for:");
            String json = req.fetchCurrencyJson("USD");
            Currency base = new Currency(json);
            CurrencyGraph cg= new CurrencyGraph(new Currency(json));

            //Collect the rest (using list of currencies from USD)
            for(String cur : base.getExchangableSet())
            {
                cg.addVertixAndEdge(new Currency(req.fetchCurrencyJson(cur)));
            }
            System.out.println();

            //Generate graph
            FloydWarshall alg = new FloydWarshall(cg);

            String in;
            while(!(in = requestUserAction()).equals("ZZZ"))
            {
                System.out.println("Results!");
                alg.getResult(in);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static String requestUserAction()
    {
        String currency = "";
        Matcher inputValidater;

        do {
            System.out.print(
                    "Please enter a base 3 letter currency, or zzz to quit: ");
          currency=input.nextLine().toUpperCase();
        }while(!(inputValidater = currencyPattern.matcher(currency)).matches());

        return currency;
    }

}
