package com.danInterview.algorithm;

import com.danInterview.Currency;
import com.danInterview.graph.CurrencyGraph;
import java.util.*;

/**
 * FloydWarshall - Kind of...
 */
public class FloydWarshall {

    HashMap<String,Currency> vertices;
    HashMap<String,Double> edges;
    HashMap<String,Currency> nextNode;


    public FloydWarshall(CurrencyGraph currencyGraph)
    {
        vertices = currencyGraph.getVertices();
        edges = currencyGraph.getEdges();
        nextNode = new HashMap<>();
        initialize();
        relaxEdges();
    }

    /**
     * Rebuild the path after relaxing edges
     * @param currency specified currency
     * @return Returns a list of results or null if the construction fails (No path, incorrect currency code)
     */
    public ArrayList<String> constructResult(String currency)
    {
        if(!vertices.containsKey(currency))
            return null;
            //Duplicate tracker (Prevent endless loops)
            Set<String> tracker = new HashSet<>();
            ArrayList<String> list = new ArrayList<>();
            //Look for cycles
            if(edges.get(currency+currency)<0D)
            {
                Currency runNode = vertices.get(currency);
                list.add(runNode.getCurrencyName());

                do{
                    if(!tracker.add(runNode.getCurrencyName()))
                    {
                        break;
                    }
                    runNode = nextNode.get(runNode.getCurrencyName()+currency);
                    list.add(runNode.getCurrencyName());
                }while (!runNode.getCurrencyName().equals(currency));
            }
        if(list.size()<=3)
            return null;
        list.add(currency);
        return list;
    }

    public void getResult(String currency){
        List<String> result = constructResult(currency);
        if(result==null)
        {
            System.out.println("Error no arbitrage path found for "+currency);
            return;
        }

        double amount = 1D;
        for (int i=0; i< result.size()-1;i++){
            String from = result.get(i),to = result.get(i+1);
            System.out.println(String.format("%f %s into %f %s",amount,from,amount=vertices.get(from).doExchange(amount,to),to));
        }
    }

    private void relaxEdges() {
        for (String k : vertices.keySet())
        {
            for (String edge : edges.keySet())
            {
                String i = edge.substring(0,3),
                        j = edge.substring(3,6);

                if(edges.get(edge)>edges.get(i+k)+edges.get(k+j))
                {
                    edges.put(edge, edges.get(i+k) + edges.get(k+j));
                    nextNode.put(i+j,nextNode.get(i+k));
                }
            }
        }
    }


    private void initialize()
    {
        System.out.println(String.format("Initializing Algorithm using graph with %d vertices and %d edges", vertices.size(), edges.size()));
        for(String c : edges.keySet()) {
            String a = c.substring(0, 3), b = c.substring(3, 6);
            nextNode.put(a+b, vertices.get(b));
        }
    }


}
