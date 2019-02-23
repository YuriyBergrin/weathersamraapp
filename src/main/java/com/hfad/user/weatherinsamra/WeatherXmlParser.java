package com.hfad.user.weatherinsamra;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class WeatherXmlParser {

    public ArrayList<Weather> weathers;

    public WeatherXmlParser(){weathers = new ArrayList<>();}

    public ArrayList<Weather> getWeathers() {
        return weathers;
    }

    public boolean parse(String xmlData){

        boolean status = true;//получилось ли у нас
        Weather currentWeather = null;//tmp для weather
        boolean inEntry = false;//в теге ли мы


        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){//пока не дошли до конца документа

                String tagName = xpp.getName();
                switch (eventType){//ловим тип события
                    case XmlPullParser.START_TAG:
                        if("FORECAST".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentWeather = new Weather();
                            currentWeather.setDay(xpp.getAttributeValue(0));
                            currentWeather.setMonth(xpp.getAttributeValue(1));
                            currentWeather.setTod(xpp.getAttributeValue(4));
                            currentWeather.setWeekday(xpp.getAttributeValue(6));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("FORECAST".equalsIgnoreCase(tagName)){
                                weathers.add(currentWeather);
                                inEntry = false;
                            } else if("TEMPERATURE".equalsIgnoreCase(tagName)){
                                currentWeather.setTemperatureMin(xpp.getAttributeValue(1));
                                currentWeather.setTemperatureMax(xpp.getAttributeValue(0));
                            }
                            else if("WIND".equalsIgnoreCase(tagName)){
                                currentWeather.setWindMax(xpp.getAttributeValue(0));
                                currentWeather.setWindMin(xpp.getAttributeValue(1));
                            }
                            else if("PRESSURE".equalsIgnoreCase(tagName)){
                                currentWeather.setPressure(xpp.getAttributeValue(0));
                            }
                            else if("RELWET".equalsIgnoreCase(tagName)){
                                currentWeather.setRelwet(xpp.getAttributeValue(0));
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;


    }//parse
}
