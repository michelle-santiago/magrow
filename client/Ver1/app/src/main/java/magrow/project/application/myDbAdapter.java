package magrow.project.application;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }


    //WEATHER

    public long insertWeather(String w_id,String temperature,String icon)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.W_ID, w_id);
        contentValues.put(myDbHelper.TEMPERATURE, temperature);
        contentValues.put(myDbHelper.W_ICON, icon);

        long id = dbb.insert(myDbHelper.WEATHER, null , contentValues);
        return id;
    }

    public int updateWeather(String w_id,String temperature,String icon)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.TEMPERATURE, temperature);
        contentValues.put(myDbHelper.W_ICON, icon);
        String[] whereArgs= {w_id};
        int count =db.update(myDbHelper.WEATHER,contentValues, myDbHelper.W_ID+" = ?",whereArgs );
        return count;
    }

    public Cursor checkWeather(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.WEATHER,null);
        return res;
    }


    //MY PREFS
    public Cursor checkPrefs(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.PREFS,null);
        return res;
    }

    public long insertPrefs(String token)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.TOKEN, token);

        long id = dbb.insert(myDbHelper.PREFS, null , contentValues);
        return id;
    }
    //privacy
    public Cursor checkPrivacyTb(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.PRIVACY_TB,null);
        return res;
    }
    public long insertPrivacyTb(String privacy)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PRIVACY_CL, privacy);
        long id = dbb.insert(myDbHelper.PRIVACY_TB, null , contentValues);
        return id;
    }
    public int updatePrivacyTb(String privacy)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PRIVACY_CL, privacy);
        int count =db.update(myDbHelper.PRIVACY_TB,contentValues, null,null );
        return count;
    }
    //getcrops
    public Cursor getCrops() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={"0"};
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.ARCHIVE+"=?",whereArgs);
        return res;
    }
//end-getcrops
//CROP TRANSACTION LOG

    public int deleteTransLogCropID(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(myDbHelper.CROP_TRANSACTION_LOG , myDbHelper.CROP_CID+" = ?",whereArgs);
        return  count;
    }


    public Cursor getCropLogDataList(String epochTime,String status){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={epochTime,status};
        Cursor res = db.rawQuery("select * from "+ myDbHelper.CROP_TRANSACTION_LOG+" where "+ myDbHelper.CROP_DATE_TIME+"< ?  and "+myDbHelper.OPERATION_STATUS+"=?",whereArgs);
        return res;
    }






    public long insertCropLog(String crop_cid,String operation, String operation_id,String operation_status,String timestamp_crop,String date_complete,String priotity,long crop_date_time)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.CROP_CID, crop_cid);
        contentValues.put(myDbHelper.OPERATION, operation);
        contentValues.put(myDbHelper.OPERATION_ID, operation_id);
        contentValues.put(myDbHelper.OPERATION_STATUS, operation_status);
        contentValues.put(myDbHelper.TIMESTAMP_CROP, timestamp_crop);
        contentValues.put(myDbHelper.DATE_COMPLETED, date_complete);
        contentValues.put(myDbHelper.PRIORITY, priotity);
        contentValues.put(myDbHelper.CROP_DATE_TIME, crop_date_time);

        long id = dbb.insert(myDbHelper.CROP_TRANSACTION_LOG, null , contentValues);
        return id;
    }

    public String viewCropLogData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.CID, myDbHelper.CROP_CID, myDbHelper.OPERATION, myDbHelper.OPERATION_ID, myDbHelper.OPERATION_STATUS, myDbHelper.TIMESTAMP_CROP, myDbHelper.DATE_COMPLETED,myDbHelper.PRIORITY,myDbHelper.CROP_DATE_TIME};
        Cursor cursor =db.query(myDbHelper.CROP_TRANSACTION_LOG,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.CID));
            String crop_cid =cursor.getString(cursor.getColumnIndex(myDbHelper.CROP_CID));
            String  operation =cursor.getString(cursor.getColumnIndex(myDbHelper.OPERATION));
            String  operation_id =cursor.getString(cursor.getColumnIndex(myDbHelper.OPERATION_ID));
            String  operation_status =cursor.getString(cursor.getColumnIndex(myDbHelper.OPERATION_STATUS));
            String  timestamp_crop =cursor.getString(cursor.getColumnIndex(myDbHelper.TIMESTAMP_CROP));
            String  date_completed =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_COMPLETED));
            String  priotity =cursor.getString(cursor.getColumnIndex(myDbHelper.PRIORITY));
            String  crop_date_time =cursor.getString(cursor.getColumnIndex(myDbHelper.CROP_DATE_TIME));
            buffer.append(cid+ "   " + crop_cid + "   " + operation +"  " + operation_id +"  "+operation_status+" "+timestamp_crop+" "+date_completed+" "+priotity+" "+crop_date_time+"\n");
        }
        return buffer.toString();
    }

    public Cursor getCropLog(String crop_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.CROP_TRANSACTION_LOG+" where "+ myDbHelper.CROP_CID+"=?",new String []{crop_id});
        return res;

    }






//FARM DETAILS

    public int updateFarmDetails(String id,String farm_name,String location,String farm_size,String measurement)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.FARM_NAME, farm_name);
        contentValues.put(myDbHelper.LOCATION, location);
        contentValues.put(myDbHelper.LAND_AREA, farm_size);
        contentValues.put(myDbHelper.LA_MEASUREMENT, measurement);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME3,contentValues, myDbHelper.FID+" = ?",whereArgs );
        return count;
    }

    public  int deleteFarmDetails(String farmname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={farmname};

        int count =db.delete(myDbHelper.TABLE_NAME3 , myDbHelper.FARM_NAME+" = ?",whereArgs);
        return  count;
    }

    public Cursor getFarmDetails() {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME3,null);
        return res;

    }

    public long insertFarmDetails(String farm_name,String location, String land_area,String la_measurement)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.FARM_NAME, farm_name);
        contentValues.put(myDbHelper.LOCATION, location);
        contentValues.put(myDbHelper.LAND_AREA, land_area);
        contentValues.put(myDbHelper.LA_MEASUREMENT, la_measurement);

        long id = dbb.insert(myDbHelper.TABLE_NAME3, null , contentValues);
        return id;
    }




    //CROP DATA

    public Cursor getVariety(String crop_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.ID2+"=?",new String []{crop_id});
        return res;

    }

    public int updateCropArchive(String id,String status)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ARCHIVE, status);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME2,contentValues, myDbHelper.ID2+" = ?",whereArgs );
        return count;
    }
    public int updatePrivacy(String privacy)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PRIVACY, privacy);
        int count =db.update(myDbHelper.TABLE_NAME2,contentValues, null,null );
        return count;
    }
    public int updateCropStatus(String id,String status)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.STATUS, status);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME2,contentValues, myDbHelper.ID2+" = ?",whereArgs );
        return count;
    }

    public long insertCrop(String crop_name,String crop, String variety,String season,String status,String date_added)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.CROP_NAME, crop_name);
        contentValues.put(myDbHelper.CROP, crop);
        contentValues.put(myDbHelper.VARIETY, variety);
        contentValues.put(myDbHelper.SEASON, season);
        contentValues.put(myDbHelper.STATUS, status);
        contentValues.put(myDbHelper.DATE_ADDED, date_added);

        long id = dbb.insert(myDbHelper.TABLE_NAME2, null , contentValues);
        return id;
    }

    public String getDataCrop()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.ID2, myDbHelper.CROP_NAME, myDbHelper.CROP, myDbHelper.VARIETY,myDbHelper.SEASON};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME2,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid          =cursor.getInt(cursor.getColumnIndex(myDbHelper.ID2));
            String crop_name =cursor.getString(cursor.getColumnIndex(myDbHelper.CROP_NAME));
            String  crop     =cursor.getString(cursor.getColumnIndex(myDbHelper.CROP));
            String  variety  =cursor.getString(cursor.getColumnIndex(myDbHelper.VARIETY));
            String  season  =cursor.getString(cursor.getColumnIndex(myDbHelper.SEASON));
            buffer.append(cid+ "   " + crop_name + "   " + crop +"  " + variety+" "+season);
        }
        return buffer.toString();
    }


    public Cursor getAllDataCropSpes(String crop) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.CROP+"=? order by "+myDbHelper.ID2+" desc",new String []{crop});
        return res;

    }

    public Cursor getAllDataCropSpesArchive(String crop,String a) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.CROP+"=? and "+ myDbHelper.ARCHIVE+"=? order by "+myDbHelper.ID2+" desc",new String []{crop,a});
        return res;

    }

    public Cursor getAllDataCropArchive(String a) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.ARCHIVE+"=?",new String []{a});
        return res;

    }


    public Cursor getCropIdUpdate(String crop,String id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.CROP_NAME+"=? and "+myDbHelper.ID2+"!=?",new String []{crop,id});
        return res;

    }

    public Cursor getCropId(String crop) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.CROP_NAME+"=?",new String []{crop});
        return res;

    }

    public Cursor getCropData(String crop) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME2+" where "+ myDbHelper.ID2+"=?",new String []{crop});
        return res;

    }

    public Cursor getCropChecker(String id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.CROP_ID+"=?",new String []{id});
        return res;

    }

    public  int deleteCrop(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(myDbHelper.TABLE_NAME2 , myDbHelper.ID2+" = ?",whereArgs);
        return  count;
    }

    public int updateCrop(String id,String crop_name,String crop, String variety,String season)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.CROP_NAME, crop_name);
        contentValues.put(myDbHelper.CROP, crop);
        contentValues.put(myDbHelper.VARIETY, variety);
        contentValues.put(myDbHelper.SEASON, season);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME2,contentValues, myDbHelper.ID2+" = ?",whereArgs );
        return count;
    }







//EVENT DATA

    public Cursor checkEvent(long date_time,String status) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_TIME+"=? and "+myDbHelper.OPERATION_STATUS+"=?",new String []{String.valueOf(date_time),status});
        return res;

    }

    public int updateEventLog(String id,String status)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.OPERATION_STATUS, status);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.ID+" = ?",whereArgs );
        return count;
    }

    public Cursor getEventLog(String crop_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.CROP_ID+"=? order by "+myDbHelper.DATE_TIME+" asc",new String []{crop_id});
        return res;

    }

    public Cursor checkPendingCropActivity(String crop_id,String status){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={crop_id,status};
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.CROP_ID+"= ?  and "+myDbHelper.OPERATION_STATUS+"=?",whereArgs);
        return res;
    }


    public Cursor getEventList(String epochTime,String status){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={epochTime,status};
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_TIME+"< ?  and "+myDbHelper.OPERATION_STATUS+"=?",whereArgs);
        return res;
    }

    public int deleteEventCropId(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.CROP_ID+" = ?",whereArgs);
        return  count;
    }

    public long insertData(long date_time,int color, String event,String date_start, String time_event, int date_id,int icon,int crop_id,String operation_id,String operation_status,String priority,Double amount)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DATE_TIME, date_time);
        contentValues.put(myDbHelper.COLOR, color);
        contentValues.put(myDbHelper.EVENT, event);
        contentValues.put(myDbHelper.DATE_START, date_start);
        contentValues.put(myDbHelper.TIME_EVENT, time_event);
        contentValues.put(myDbHelper.DATE_ID, date_id);
        contentValues.put(myDbHelper.ICON, icon);
        contentValues.put(myDbHelper.CROP_ID, crop_id);
        contentValues.put(myDbHelper.OPERATION_ID, operation_id);
        contentValues.put(myDbHelper.OPERATION_STATUS, operation_status);
        contentValues.put(myDbHelper.PRIORITY, priority);
        contentValues.put(myDbHelper.AMOUNT, amount);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }



    public Cursor getAllData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME,null);
        return res;

    }

    public Cursor getAllDataEvent(String id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+ " where "+myDbHelper.CROP_ID+"=?",new String []{id});
        return res;

    }

    public Cursor getAllDataWithId(String id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.ID+"=?",new String []{id});
        return res;

    }

    public Cursor getEventData(String date_start) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_START+"=?",new String []{date_start});
        return res;

    }

    public Cursor getEventDataCrop(String date_start,String id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_START+"=? and "+ myDbHelper.CROP_ID+"=?",new String []{date_start,id});
        return res;

    }

    public Cursor getEventDataList(String epochTime,String date_today){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={epochTime,date_today};
        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_TIME+">? and "+myDbHelper.DATE_START+"=? order by "+myDbHelper.DATE_START+" asc,"+myDbHelper.TIME_EVENT+" asc",whereArgs);
        return res;
    }


    // GETTING LAST ID ADDED
    public Cursor getLastId(){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor res = db.rawQuery("select max("+myDbHelper.ID+") from "+ myDbHelper.TABLE_NAME,null);
        return res;
    }

    // GETTING START DATE
    public Cursor getStartDate(String date_id){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={date_id};

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_ID+"=? order by "+myDbHelper.DATE_START+" asc limit 1",whereArgs);
        return res;
    }



    // GETTING END DATE
    public Cursor getEndDate(String date_id){
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_ID+"=? order by "+myDbHelper.DATE_START+" desc limit 1",new String []{date_id});
        return res;
    }

    // GETTING EVENT INFO
    public Cursor getEventInfo(String date_id){
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME+" where "+ myDbHelper.DATE_ID+"=?",new String []{date_id});
        return res;
    }



    public Cursor getDateIdMax() {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select COALESCE(max("+ myDbHelper.ID+"),0) from "+ myDbHelper.TABLE_NAME,null);
        return res;

    }

    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.ID, myDbHelper.DATE_TIME, myDbHelper.COLOR, myDbHelper.EVENT, myDbHelper.DATE_START, myDbHelper.TIME_EVENT, myDbHelper.DATE_ID,myDbHelper.CROP_ID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.ID));
            String date_time =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_TIME));
            String  color =cursor.getString(cursor.getColumnIndex(myDbHelper.COLOR));
            String  event =cursor.getString(cursor.getColumnIndex(myDbHelper.EVENT));
            String  date_start =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_START));
            String  time_event =cursor.getString(cursor.getColumnIndex(myDbHelper.TIME_EVENT));
            String  date_id =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE_ID));
            String  crop_id =cursor.getString(cursor.getColumnIndex(myDbHelper.CROP_ID));
            buffer.append(cid+ "   " + date_time + "   " + color +"  " + event +"  "+date_start+" "+time_event+" "+date_id+" "+crop_id+"\n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.DATE_ID+" = ?",whereArgs);
        return  count;
    }

    public int updateEvent(long date_time,int color, String event, String date_start, String time_event, String date_id,int icon,int crop_id,String operation_id,String operation_status,String priority, double amount)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DATE_TIME, date_time);
        contentValues.put(myDbHelper.COLOR, color);
        contentValues.put(myDbHelper.EVENT, event);
        contentValues.put(myDbHelper.DATE_START, date_start);
        contentValues.put(myDbHelper.TIME_EVENT, time_event);
        contentValues.put(myDbHelper.ICON, icon);
        contentValues.put(myDbHelper.CROP_ID, crop_id);
        contentValues.put(myDbHelper.OPERATION_ID, operation_id);
        contentValues.put(myDbHelper.OPERATION_STATUS, operation_status);
        contentValues.put(myDbHelper.PRIORITY, priority);
        contentValues.put(myDbHelper.AMOUNT, amount);
        String[] whereArgs= {date_id};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.DATE_ID+" = ?",whereArgs );
        return count;
    }
    //RECORD LOG AND COST
    public long insertRecordLog(int c_id,double area_harvested, String ah_measurement,double area_planted,String ap_measurement,double no_of_cavans_harvested,double weight_per_cavan)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.C_ID, c_id);
        contentValues.put(myDbHelper.AREA_HARVERSTED, area_harvested);
        contentValues.put(myDbHelper.AH_MEASUREMENT, ah_measurement);
        contentValues.put(myDbHelper.AREA_PLANTED, area_planted);
        contentValues.put(myDbHelper.AP_MEASUREMENT, ap_measurement);
        contentValues.put(myDbHelper.N0_OF_CAVANS_HARVESTED, no_of_cavans_harvested);
        contentValues.put(myDbHelper.WEIGHT_PER_CAVAN, weight_per_cavan);

        long id = dbb.insert(myDbHelper.TABLE_NAME4, null , contentValues);
        return id;//return if successful or nah
    }
    public Cursor getRecordLog(String crop_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME4+" where "+ myDbHelper.C_ID+"=?",new String[]{crop_id});
        return res;
    }
    public Cursor getAllRecordLog() {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME4,null);
        return res;
    }
    public Cursor getCostRecordLog(String rid) {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME5+" where "+ myDbHelper.C_RID+"=?",new String []{rid});
        return res;
    }
    public Cursor getAllCostRecordLog() {
        SQLiteDatabase db = myhelper.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ myDbHelper.TABLE_NAME5,null);
        return res;
    }
    public long insertCostRecordLog(int c_rid,double seeds, double irrig_fee,double fertilizer,double pesticides,double labor,double rental, double transport,double other)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.C_RID, c_rid);
        contentValues.put(myDbHelper.SEEDS, seeds);
        contentValues.put(myDbHelper.IRRIG_FEE, irrig_fee);
        contentValues.put(myDbHelper.FERTILIZER, fertilizer);
        contentValues.put(myDbHelper.PESTICIDES, pesticides);
        contentValues.put(myDbHelper.LABOR, labor);
        contentValues.put(myDbHelper.RENTAL, rental);
        contentValues.put(myDbHelper.TRANSPORT, transport);
        contentValues.put(myDbHelper.OTHER, other);

        long id = dbb.insert(myDbHelper.TABLE_NAME5, null , contentValues);
        return id;//return if successful or nah
    }
    public int updateRecordLog(String id,int c_id,double area_harvested, String ah_measurement,double area_planted,String ap_measurement,double no_of_cavans_harvested,double weight_per_cavan)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.C_ID, c_id);
        contentValues.put(myDbHelper.AREA_HARVERSTED, area_harvested);
        contentValues.put(myDbHelper.AH_MEASUREMENT, ah_measurement);
        contentValues.put(myDbHelper.AREA_PLANTED, area_planted);
        contentValues.put(myDbHelper.AP_MEASUREMENT, ap_measurement);
        contentValues.put(myDbHelper.N0_OF_CAVANS_HARVESTED, no_of_cavans_harvested);
        contentValues.put(myDbHelper.WEIGHT_PER_CAVAN, weight_per_cavan);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME4,contentValues, myDbHelper.RID+" = ?",whereArgs );
        return count;
    }
    public int updateCostRecordLog(String id,double seeds, double irrig_fee,double fertilizer,double pesticides,double labor,double rental, double transport,double other)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.SEEDS, seeds);
        contentValues.put(myDbHelper.IRRIG_FEE, irrig_fee);
        contentValues.put(myDbHelper.FERTILIZER, fertilizer);
        contentValues.put(myDbHelper.PESTICIDES, pesticides);
        contentValues.put(myDbHelper.LABOR, labor);
        contentValues.put(myDbHelper.RENTAL, rental);
        contentValues.put(myDbHelper.TRANSPORT, transport);
        contentValues.put(myDbHelper.OTHER, other);
        String[] whereArgs= {id};
        int count =db.update(myDbHelper.TABLE_NAME5,contentValues, myDbHelper.C_RID+" = ?",whereArgs );
        return count;
    }

    public  int deleteCostRecordLog(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(myDbHelper.TABLE_NAME5 , myDbHelper.COST_ID+" = ?",whereArgs);
        return  count;
    }
    public  int deleteRecordLog(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={id};

        int count =db.delete(myDbHelper.TABLE_NAME4 , myDbHelper.RID+" = ?",whereArgs);
        return  count;
    }

    //END RECORD LOG AND COST

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String ID="_id";     // Column I (Primary Key)
        private static final String DATE_TIME = "date_time";    //Column II
        private static final String COLOR= "color";    // Column III
        private static final String EVENT= "event";    // Column III
        private static final String DATE_START= "date_start";    // Column III
        private static final String TIME_EVENT= "time_event";    // Column III
        private static final String DATE_ID= "date_id";    // DI NA KASALI, D KO MUNA INALIS KASI MAGEERROR SA ADD EVENT, D PA AYOS
        private static final String ICON = "icon";
        private static final String CROP_ID = "crop_id";
        private static final String OPERATION = "operation";    //Column II//DI NA KASALI, D KO MUNA INALIS KASI MAGEERROR SA CROP LOG, D PA AYOS CROP LOG
        private static final String OPERATION_ID = "operation_id";    //Column II
        private static final String OPERATION_STATUS = "crop_status";    //Column II
        private static final String PRIORITY = "priority";
        private static final String AMOUNT = "amount";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE_TIME+" BIGINT(99) ,"+ COLOR+" INTEGER,"+ EVENT+" TEXT,"+ DATE_START+" TEXT,"+ TIME_EVENT+" TEXT,"+ DATE_ID+" INTEGER,"+ ICON+" INTEGER,"+ CROP_ID+" INTEGER,"+ OPERATION_ID+" TEXT,"+ OPERATION_STATUS+" TEXT,"+ PRIORITY +" TEXT,"+AMOUNT +" REAL);";

        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;



        private static final String TABLE_NAME2 = "mycrops";   // Table Name
        private static final String ID2="_id";     // Column I (Primary Key)
        private static final String FARM_ID = "farm_id";    //Column II
        private static final String CROP_NAME = "crop_name";    //Column II
        private static final String CROP = "crop";    //Column II
        private static final String VARIETY = "variety";    //Column II
        private static final String SEASON = "season";    //Column II
        private static final String STATUS = "status";    //Column II
        private static final String ARCHIVE = "archive";    //Column II
        private static final String DATE_ADDED= "date_added";
        private static final String PRIVACY= "privacy";


        private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAME2+
                "("+ID2+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CROP_NAME+" TEXT,"+ CROP+" TEXT,"+ VARIETY+" TEXT,"+ SEASON+" TEXT,"+STATUS+" TEXT,"+FARM_ID+" INTEGER DEFAULT 1,"+ ARCHIVE+" INTEGER DEFAULT 0,"+ DATE_ADDED+" TEXT,"+ PRIVACY+" INTEGER DEFAULT 1);";

        private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS "+TABLE_NAME2;


        private static final String TABLE_NAME3 = "farm_details";   // Table Name
        private static final String FID="fid";     // Column I (Primary Key)
        private static final String FARM_NAME = "farm_name";    //Column II
        private static final String LOCATION = "location";    //Column II
        private static final String LAND_AREA = "land_area";    //Column II
        private static final String LA_MEASUREMENT = "la_location";
        private static final String TIMESTAMP = "TIMESTAMP";

        private static final String CREATE_TABLE3 = "CREATE TABLE "+TABLE_NAME3+
                "("+FID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FARM_NAME+" TEXT,"+ LOCATION+" TEXT,"+ LAND_AREA+" REAL,"+ LA_MEASUREMENT+" TEXT," + TIMESTAMP +" TIMESTAMP);";

        private static final String DROP_TABLE3 ="DROP TABLE IF EXISTS "+TABLE_NAME3;


        private static final String CROP_TRANSACTION_LOG = "crop_transaction_log";   // Table Name
        private static final String CID="cid";     // Column I (Primary Key)
        private static final String CROP_CID="crop_cid";     // Column I (Primary Key)
        // private static final String OPERATION = "operation";    //Column II
        // private static final String OPERATION_ID = "operation_id";    //Column II
        //private static final String OPERATION_STATUS = "crop_status";    //Column II
        private static final String TIMESTAMP_CROP = "timestamp_crop";
        private static final String DATE_COMPLETED = "date_completed";
        //private static final String PRIORITY = "priority";
        private static final String CROP_DATE_TIME = "crop_date_time";

        private static final String CREATE_TRANS_LOG = "CREATE TABLE "+CROP_TRANSACTION_LOG+
                "("+CID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CROP_CID+" TEXT,"+ OPERATION+" TEXT,"+ OPERATION_ID+" TEXT,"+ TIMESTAMP_CROP +" TIMESTAMP," + DATE_COMPLETED +" TEXT DEFAULT 0," + PRIORITY +" TEXT, "+CROP_DATE_TIME+" INTEGER);";

        private static final String DROP_TRANS_LOG ="DROP TABLE IF EXISTS "+CROP_TRANSACTION_LOG;

        private static final String TABLE_NAME4= "record_log";   // Table Name
        private static final String RID="rid";     // Column I (Primary Key)
        private static final String C_ID = "crop_id";    //Column II
        private static final String AREA_HARVERSTED = "area_harvested";    //Column II
        private static final String AH_MEASUREMENT = "ah_measurement";    //Column II
        private static final String AREA_PLANTED = "area_planted";
        private static final String AP_MEASUREMENT = "ap_measurement";
        private static final String N0_OF_CAVANS_HARVESTED = "no_of_cavans_harvested";
        private static final String WEIGHT_PER_CAVAN = "weight_per_cavan";


        private static final String CREATE_TABLE4 = "CREATE TABLE "+TABLE_NAME4+
                "("+RID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+C_ID+" INTEGER,"+ AREA_HARVERSTED+" REAL,"+ AH_MEASUREMENT+" TEXT,"+ AREA_PLANTED+" REAL," +AP_MEASUREMENT+" TEXT,"+ N0_OF_CAVANS_HARVESTED+" REAL,"+ WEIGHT_PER_CAVAN+" REAL,  CONSTRAINT fk_mycrops" +
                "    FOREIGN KEY (crop_id)" +
                "    REFERENCES mycrops(_id));";

        private static final String DROP_TABLE4 ="DROP TABLE IF EXISTS "+TABLE_NAME4;

        private static final String TABLE_NAME5 = "cost";   // Table Name
        private static final String COST_ID="cid";     // Column I (Primary Key)
        private static final String C_RID = "record_log_id";    //Column II
        private static final String SEEDS = "seeds";    //Column II
        private static final String IRRIG_FEE = "irrigation_fee";    //Column II
        private static final String FERTILIZER = "fertilizer";
        private static final String PESTICIDES = "pesticides";
        private static final String LABOR = "labor";
        private static final String RENTAL = "rental";
        private static final String TRANSPORT= "transport";
        private static final String OTHER= "other";

        private static final String CREATE_TABLE5 = "CREATE TABLE "+TABLE_NAME5+
                "("+COST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+C_RID+" INTEGER,"+ SEEDS+" REAL,"+ IRRIG_FEE+" REAL,"+ FERTILIZER+" REAL," +PESTICIDES+" REAL,"+LABOR+" REAL,"+RENTAL+" REAL,"+ TRANSPORT+" REAL,"+ OTHER+" REAL,  CONSTRAINT fk_record_log" +
                "    FOREIGN KEY (record_log_id)" +
                "    REFERENCES record_log(rid));";

        private static final String DROP_TABLE5 ="DROP TABLE IF EXISTS "+TABLE_NAME5;



        //REMEMBER TOKEN
        private static final String PREFS = "prefs";   // Table Name
        private static final String TOKEN="token";     // Column I (Primary Key)

        private static final String CREATE_PREFS = "CREATE TABLE "+PREFS+ "("+TOKEN+" TEXT);";

        private static final String DROP_TABLE_PREFS ="DROP TABLE IF EXISTS "+PREFS;

        //PRIVACY
        private static final String PRIVACY_TB = "privacy";   // Table Name
        private static final String PRIVACY_CL="value";     // Column I (Primary Key)

        private static final String CREATE_PRIVACY = "CREATE TABLE "+PRIVACY_TB+ "("+PRIVACY_CL+" TEXT);";

        private static final String DROP_TABLE_PRIVACY ="DROP TABLE IF EXISTS "+PRIVACY_TB;

        //WEATHER
        private static final String WEATHER = "weather";   // Table Name
        private static final String W_ID="w_id";     // Column I (Primary Key)
        private static final String TEMPERATURE="temperature";
        private static final String W_ICON="w_icon";

        private static final String CREATE_WEATHER = "CREATE TABLE "+WEATHER+ "("+W_ID+" INTEGER, "+TEMPERATURE+" TEXT, "+W_ICON+" TEXT);";

        private static final String DROP_TABLE_WEATHER ="DROP TABLE IF EXISTS "+WEATHER;



        private Context context;





        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }


        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE2);
                db.execSQL(CREATE_TABLE3);
                db.execSQL(CREATE_TRANS_LOG);
                db.execSQL(CREATE_TABLE4);
                db.execSQL(CREATE_TABLE5);
                db.execSQL(CREATE_PREFS);
                db.execSQL(CREATE_PRIVACY);
                db.execSQL(CREATE_WEATHER);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_TABLE2);
                db.execSQL(DROP_TABLE3);
                db.execSQL(DROP_TRANS_LOG);
                db.execSQL(DROP_TABLE4);
                db.execSQL(DROP_TABLE5);
                db.execSQL(DROP_TABLE_PREFS);
                db.execSQL(DROP_TABLE_PRIVACY);
                db.execSQL(DROP_TABLE_WEATHER);

                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}
