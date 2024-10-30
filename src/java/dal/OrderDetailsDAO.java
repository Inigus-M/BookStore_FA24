package dal;

import dal.GenericDAO;
import entity.OrderDetails;
import java.util.LinkedHashMap;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class OrderDetailsDAO extends GenericDAO<OrderDetails> {
    @Override
    public List<OrderDetails> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public int insert(OrderDetails t) {
        String sql = "INSERT INTO [dbo].[OrderDetails]\n"
                + "           ([quantity]\n"
                + "           ,[productId]\n"
                + "           ,[orderId])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";
        pm = new LinkedHashMap<>();
        pm.put("1", t.getQuantity());
        pm.put("2", t.getProductId());
        pm.put("3", t.getOrderId());
        return insertGenericDAO(sql, pm);
    }
}