package com.danInterview;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Currency object class
 */
public class Currency {

    private Pattern basePattern = Pattern.compile("^\\{\"base\":\"(?<baseCurrency>[A-Z]{3})\".*\"rates\":\\{(?<exchangeList>.*)\\}\\}$");
    private Pattern pat = Pattern.compile("\"(?<currency>[A-Z]{3})\"\\s*:\\s*(?<exchange>\\d+.\\d*)");

    private HashMap<String,Double> conversionMap;
    private String currency;

    public Currency(String json)
    {
        conversionMap = new HashMap<>();
        GenerateConversionMap(json);
    }

    private void GenerateConversionMap(String json)
    {
        Matcher baseMatcher = basePattern.matcher(json);
        if(baseMatcher.find())
        {
            this.currency = baseMatcher.group("baseCurrency");
            String exchangeList =  baseMatcher.group("exchangeList");

            Matcher m = pat.matcher(exchangeList);

            while (m.find())
            {
                conversionMap.put(m.group("currency"),new Double(m.group("exchange")));
            }
        }
    }

    public Double doExchange(Double baseCount, String exchangeCurrency)
    {
        if (exchangeCurrency.equals(this.currency))
            return baseCount;
        else
            return conversionMap.get(exchangeCurrency)*(baseCount);
    }

    public String getCurrencyName()
    {
        return this.currency;
    }

    public Set<String> getExchangableSet()
    {
        return (conversionMap.keySet());
    }
}
