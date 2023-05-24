package magrow.project.application;

public class CardItemString {


    private String crops;
    private String mTextResource;
    private String time;
    private String crop_type;
    private Integer activity_type;




    public CardItemString(String title, String text,String crop,String type,Integer activity) {
        time = title;
        mTextResource = text;
        crops = crop;
        crop_type=type;
        activity_type=activity;

    }



    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return time;
    }

    public String getCrop() {
        return crops;
    }

    public String getCrop_type() {
        return crop_type;
    }

    public Integer getActivity_type() {
        return activity_type;
    }

}
