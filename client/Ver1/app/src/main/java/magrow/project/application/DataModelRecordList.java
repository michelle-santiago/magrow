package magrow.project.application;

public class DataModelRecordList {

    int id;
    String crop;
    String crop_name;
    String variety;
    String status;



    public DataModelRecordList( int id,String crop,String crop_name,String variety,String status)
    {

        this.id=id;
        this.crop=crop;
        this.crop_name=crop_name;
        this.variety=variety;
        this.status=status;
    }


    public int getId(){return id;}

    public String getCrop()
    {
        return crop;
    }

    public String getCrop_name()
    {
        return crop_name;
    }

    public String getVariety()
    {
        return variety;
    }

    public String getStatus(){ return status;}



}

