package es.jander.ms.quotes;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class QuotesService
{
    private List<String> quotes = new LinkedList<>();

    private boolean isIndexValid (int index)
    {
        return index >= 0 && quotes.size() > index;
    }

    public void init ()
    {
        quotes.clear();
        quotes.add("Artificial Intelligence usually beats natural stupidity");
        quotes.add("To err is human... to really foul up requires the root password");
        quotes.add("My software never has bugs. It just develops random features");
        quotes.add("Programming is like sex, one mistake and you have to support it for the rest of your life");
        quotes.add("I'm not anti-social; I'm just not user friendly");
        quotes.add("Computer language design is just like a stroll in the park. Jurassic Park, that is");
    }

    public int getCount()
    {
        return quotes.size();
    }

    public List<String> getQuotes ()
    {
        return new ArrayList<>(quotes);
    }

    public String getQuote(int index)
    {
        return isIndexValid(index) ? quotes.get(index) : null;
    }

    public QuotesService saveQuote(String quote)
    {
        quotes.add(quote);
        return this;
    }

    public boolean updateQuote (String quote, int index)
    {
        if (isIndexValid(index)) {
            quotes.set(index, quote);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteQuote (int index)
    {
        if (isIndexValid(index)) {
            quotes.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public void deleteQuotes ()
    {
        quotes.clear();
    }
}
