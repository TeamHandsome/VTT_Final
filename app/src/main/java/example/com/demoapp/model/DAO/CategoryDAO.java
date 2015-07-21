package example.com.demoapp.model.DAO;

import java.util.ArrayList;

import example.com.demoapp.model.CategoryItem;
import example.com.demoapp.model.SubCategoriesItem;
import example.com.demoapp.utility.DbHelper;

/**
 * Created by Tony on 22/7/2015.
 */
public class CategoryDAO extends BaseDAO {
    public CategoryDAO() {
    }


    public ArrayList<CategoryItem> getAllCategories()
    {
        ArrayList<CategoryItem> arrayList = null;
        String query = "select * from categories";
        this.rawQueryReadonly(query);

        //kiểm tra cursor có dữ liệu không? Nếu có, thưc hiện lấy dữ liệu từ cursor cho vào
        //mảng arrayList

        if(cursor.moveToFirst())
        {
            arrayList = new ArrayList<CategoryItem>();
            do
            {
                CategoryItem item = new CategoryItem();
                item.setId(cursor.getInt((cursor.getColumnIndex(DbHelper.DB_CATEGORIES_ID))));
                item.setName(cursor.getString(cursor.getColumnIndex(DbHelper.DB_CATEGORIES_NAME)));

                arrayList.add(item);
            }while(cursor.moveToNext());
        }
        close();
        return arrayList;
    }
}