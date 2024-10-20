package dal;

import entity.Category;
import java.util.List;

/**
 *
 * @author DuongNKTHE186476
 */
public class CategoryDAO extends GenericDAO{

    @Override
    public List<Category> findAll() {
        return queryGenericDAO(Category.class);
    }

    @Override
    public int insert(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
