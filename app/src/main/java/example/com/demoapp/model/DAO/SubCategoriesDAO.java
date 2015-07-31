package example.com.demoapp.model.DAO;

import java.util.ArrayList;

import example.com.demoapp.utility.DbHelper;
import example.com.demoapp.model.SubCategoriesItem;

/**
 * Created by Tony on 6/7/2015.
 */
public class SubCategoriesDAO extends BaseDAO {

    public SubCategoriesDAO() {
        super();
    }

    public ArrayList<SubCategoriesItem> getAllSubCategories(int categories_id)
    {
        ArrayList<SubCategoriesItem> arrayList = null;
        String query = "select * from "+DbHelper.DB_TABLE_SUBCATEGORY+" " +
            "where "+DbHelper.DB_SUBCATEGORIES_CATID+" = "+categories_id;
        this.query(query);

        //kiểm tra cursor có dữ liệu không? Nếu có, thưc hiện lấy dữ liệu từ cursor cho vào
        //mảng arrayList

        if(cursor.moveToFirst())
        {
            arrayList = new ArrayList<>();
            do
            {
                SubCategoriesItem item = new SubCategoriesItem();
                item.setId(cursor.getInt((cursor.getColumnIndex(DbHelper.DB_SUBCATEGORIES_ID))));
                item.setName(cursor.getString(cursor.getColumnIndex(DbHelper.DB_SUBCATEGORIES_NAME)));

                arrayList.add(item);
            }while(cursor.moveToNext());
        }
        this.closeCursor();
        return arrayList;
    }
}
