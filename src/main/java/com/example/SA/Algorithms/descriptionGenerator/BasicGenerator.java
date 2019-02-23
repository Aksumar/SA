package com.example.SA.Algorithms.descriptionGenerator;

import org.springframework.data.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class BasicGenerator
{
    static Random r = new Random();
    File Dir;

    //structure:
    //Большинство (%) выбрало <var>
    public BasicGenerator(String filename)
    {
        Dir = new File(filename);
    }

    public String analyze(Pair<String, Double> answer) throws IOException
    {
        Boolean noMoMacros = false;
        String randomTemplate = RandomTemplate("templates");

        while (!noMoMacros)
        {
            noMoMacros = true;

            String[] _split = randomTemplate.split("<|>");
            randomTemplate = "";
            for (int i = 0; i < _split.length; i++)
            {
                if (_split[i].contains("~"))
                {
                    if (_split[i].contains("%"))
                    {
                        _split[i] = answer.getFirst() + "%";
                        noMoMacros = false;
                    }
                    else if (_split[i].contains("r"))
                    {
                        _split[i] = answer.getFirst();
                        noMoMacros = false;
                    }
                } else
                {
                    if (_split[i].equals("")) continue;

                    String temp;
                    try
                    {
                        temp = RandomTemplate(_split[i]);
                        noMoMacros = false;
                    } catch (ArrayIndexOutOfBoundsException e)
                    {
                        temp = _split[i];
                    }
                    _split[i] = temp;
                }
                randomTemplate += _split[i];
            }
        }
        return randomTemplate;
    }

    public String RandomTemplate(final String filename) throws IOException, ArrayIndexOutOfBoundsException
    {
        if(!Dir.exists())
            throw new IOException("");
        File file = Objects.requireNonNull(Dir.listFiles((dir, name) -> name.contains(filename)))[0];

        FileInputStream inputStream = new FileInputStream(file);

        ArrayList<String> Templates = new ArrayList<String>();
        Scanner s = new Scanner(inputStream);
        String line;

        int i = -1;
        while (s.hasNext())
        {
            i++;
            Templates.add("");

            line = s.nextLine();
            while (!line.equals("#"))
            {
                Templates.set(i, Templates.get(i) + line);

                if (!s.hasNext()) break;
                line = s.nextLine();
            }
        }
        return Templates.get(r.nextInt(Templates.size()));
    }

    public String getDir()
    {
        return Dir.getAbsolutePath();
    }
}
