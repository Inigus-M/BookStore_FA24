package dal;

import dal.GenericDAO;
import entity.Order;
import java.util.LinkedHashMap;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class OrderDAO extends GenericDAO<Order> {
    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public int insert(Order t) {
        String sql = "INSERT INTO [dbo].[Order]\n"
                + "           ([amount]\n"
                + "           ,[accountId]\n"
                + "           ,[createAt])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        pm = new LinkedHashMap<>();
        pm.put("1", t.getAmount());
        pm.put("2", t.getAccountId());
        pm.put("3", t.getCreateAt());
        return insertGenericDAO(sql, pm);
    }
}