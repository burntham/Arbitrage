package com.danInterview.graph;

import com.danInterview.Currency;
import java.util.HashMap;

/**
 * Currency Graph class
 * //TODO Replace hashmaps with arrays to increase performance
 */
public class CurrencyGraph {
    protected HashMap<String, Currency> vertices;
    protected HashMap<String, Double> edges;
    private Currency baseCurrency;

    public CurrencyGraph(Currency baseCurrency){

        this.baseCurrency = baseCurrency;
        vertices = new HashMap<>();
        edges = new HashMap<>();
        addVertixAndEdge(baseCurrency);
    }

    public CurrencyGraph(){
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }

    public HashMap<String,Currency> getVertices()
    {
        return this.vertices;
    }

    public HashMap<String, Double> getEdges()
    {
        return this.edges;
    }

    //Return source currency
//    public Currency getSource(){
//        return this.baseCurrency;
//    }

    public void addVertixAndEdge(Currency currencyNode) {
        String a = currencyNode.getCurrencyName();
        vertices.put(a, currencyNode);
        //Add edge (a,a)
        edges.put(a+a, 0D);
        //Add unique edges only
        currencyNode.getExchangableSet().stream().filter(b -> !edges.containsKey(a + b)).forEach(b -> edges.put(a + b, 100000D*Math.log(1D / currencyNode.doExchange(1D, b))));
    }

}
