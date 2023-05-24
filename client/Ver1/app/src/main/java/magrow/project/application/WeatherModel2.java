package magrow.project.application;

public class WeatherModel2 {

    String date;
    String minTemp;
    String maxTemp;
    int dayIcon;
    String iconPhrase;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return minTemp;
    }

    public void setTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public int getDayIcon() {
        return dayIcon;
    }

    public void setDayIcon(int dayIcon) {
        this.dayIcon = dayIcon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }




}
