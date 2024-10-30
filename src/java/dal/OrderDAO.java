package dal;

import dal.GenericDAO;
import entity.Order;
import entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class OrderDAO extends GenericDAO<Order> {

    @Override
    public List<Order> findAll() {
        return queryGenericDAO(Order.class);
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

    public List<Order> findOrderByUserId(int id) {
        String sql = "SELECT [id]\n"
                + "      ,[amount]\n"
                + "      ,[accountId]\n"
                + "      ,[createAt]\n"
                + "  FROM [dbo].[Order]\n"
                + "  WHERE [accountId] = ?";
        pm = new LinkedHashMap<>();
        pm.put("accountId", id);

        return queryGenericDAO(Order.class, sql, pm);
    }

    public void deleteOrderById(int id) {
        // Bước 1: Xóa các sản phẩm liên quan trong OrderDetails
        deleteOrderDetailsByOrderId(id); // Gọi phương thức xóa sản phẩm trước

        // Bước 2: Xóa đơn hàng
        String sql = "DELETE FROM [dbo].[Order]\n"
                + "WHERE [id] = ?";
        pm = new LinkedHashMap<>();
        pm.put("id", id);
        deleteGenericDAO(sql, pm);
    }

// Phương thức để xóa các sản phẩm trong OrderDetails
    public void deleteOrderDetailsByOrderId(int orderId) {
        String sql = "DELETE FROM [dbo].[OrderDetails]\n"
                + "WHERE [orderId] = ?";
        pm = new LinkedHashMap<>();
        pm.put("orderId", orderId);
        deleteGenericDAO(sql, pm);
    }
}
