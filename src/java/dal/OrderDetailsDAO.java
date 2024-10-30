package dal;

import dal.GenericDAO;
import entity.OrderDetails;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class OrderDetailsDAO extends GenericDAO<OrderDetails> {

    @Override
    public List<OrderDetails> findAll() {
        return queryGenericDAO(OrderDetails.class);
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

    public List<OrderDetails> findOrderDetailsByOrderId(String orderId) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE orderId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetails ods = new OrderDetails();
                ods.setId(rs.getInt("id"));
                ods.setQuantity(rs.getInt("quantity"));
                ods.setProductId(rs.getInt("productId"));
                ods.setOrderId(rs.getInt("orderId"));
                orderDetailsList.add(ods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetailsList;
    }
}
