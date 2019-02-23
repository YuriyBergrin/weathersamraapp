package com.hfad.user.weatherinsamra;

public class Weather {
    private String day;//число
    private String month;//месяц
    private String weekday;//день недели
    private String tod;//время суток, для которого составлен прогноз: 0 - ночь 1 - утро, 2 - день, 3 - вечер

    private String temperatureMin;
    private String temperatureMax;

    private String windMin;//ветер мин
    private String windMax;//ветер макс

    private String pressure;//давление
    private String relwet;//влажность

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        int tmpMonth = Integer.parseInt(month);
        switch (tmpMonth){
            case 1: return "января";
            case 2: return "февраля";
            case 3: return "марта";
            case 4: return "апреля";
            case 5: return "мая";
            case 6: return "июня";
            case 7: return "июля";
            case 8: return "августа";
            case 9: return "сентября";
            case 10: return "октября";
            case 11: return "ноября";
            case 12: return "декабря";

        }//switch


        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeekday() {

        int tmpWeekday = Integer.parseInt(weekday);
        switch (tmpWeekday){
            case 1: return "Воскресенье";
            case 2: return "Понедельник";
            case 3: return "Вторник";
            case 4: return "Среда";
            case 5: return "Четверг";
            case 6: return "Пятница";
            case 7: return "Суббота";

        }//switch

        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTod() {

        int tmpTod = Integer.parseInt(tod);
        switch (tmpTod){
            case 0: return "Ночь";
            case 1: return "Утро";
            case 2: return "День";
            case 3: return "Вечер";}

        return tod;
    }

    public void setTod(String tod) {
        this.tod = tod;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getWindMin() {
        return windMin;
    }

    public void setWindMin(String windMin) {
        this.windMin = windMin;
    }

    public String getWindMax() {
        return windMax;
    }

    public void setWindMax(String windMax) {
        this.windMax = windMax;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getRelwet() {
        return relwet;
    }

    public void setRelwet(String relwet) {
        this.relwet = relwet;
    }
}
