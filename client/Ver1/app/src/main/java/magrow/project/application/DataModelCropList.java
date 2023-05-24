package magrow.project.application;

public class DataModelCropList {

    int id;
    String crop;
    String crop_name;
    String variety;
    String status;
    int position;
    int archive;





    public DataModelCropList( int id,String crop,String crop_name,String variety,String status,int position,int archive)
    {

        this.id=id;
        this.crop=crop;
        this.crop_name=crop_name;
        this.variety=variety;
        this.status=status;
        this.position=position;
        this.archive=archive;
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

    public int getPosition(){return position;}

    public int getArchive(){return archive;}

}

