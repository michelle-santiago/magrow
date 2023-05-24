package magrow.project.application;

public class DataModelCropLog {

    String datetimecrop;
    String operation;
    String operation_status;
    String operation_name;


    public DataModelCropLog(String datetimecrop,String operation,String operation_status,String operation_name)
    {

        this.datetimecrop=datetimecrop;
        this.operation=operation;
        this.operation_status=operation_status;
        this.operation_name=operation_name;

    }

    public String getDatetimecrop(){
        return datetimecrop;
    }

    public String getOperation(){
        return operation;
    }

    public String getOperation_status(){
        return operation_status;
    }

    public String getOperation_name(){
        return operation_name;
    }


}
