package dal;

import constant.CommonConst;
import entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author DuongNKTHE186476
 */
public class ProductDAO extends GenericDAO {

    @Override
    public List<Product> findAll() {
        return queryGenericDAO(Product.class);
    }

    @Override
    public int insert(Object t) {
        return insertGenericDAO(t);
    }

    public List<Product> getProductByCid(String cid) {
        List<Product> list = new ArrayList<>();
        String query = "select * from product where categoryId = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, cid);

            rs = ps.executeQuery();

            while (rs.next()) {
                Product product = Product.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .quantity(rs.getInt("quantity"))
                        .price(rs.getDouble("price"))
                        .build();

                list.add(product);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<Product> findProductByOrderId(String orderId) {
        // Tao list chua cac product
        List<Product> userProduct = new ArrayList<>();
        // Truy van lay du lieu
        String sql = "SELECT p.id, p.name, p.image, p.quantity, p.price, p.description, p.categoryId\n"
                + "FROM OrderDetails od\n"
                + "JOIN Product p ON od.productId = p.id\n"
                + "WHERE od.orderId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setImage(rs.getString("image"));
                p.setQuantity(rs.getInt("quantity"));
                p.setQuantity(rs.getInt("quantity"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                p.setCategoryId(rs.getInt("categoryId"));
                
                userProduct.add(p);
            }
        } catch (SQLException e) {
        }
        return userProduct;
    }

    public Product findById(Product product) {
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[image]\n"
                + "      ,[quantity]\n"
                + "      ,[price]\n"
                + "      ,[description]\n"
                + "      ,[categoryId]\n"
                + "  FROM [dbo].[Product]\n"
                + "  where id = ?";
        pm = new LinkedHashMap<>();
        pm.put("id", product.getId());
        List<Product> list = queryGenericDAO(Product.class, sql, pm);
        //neu nhu list ma empty => ko co san pham => tra ve null
        //nguoc lai list ma ko empty => co san pham => nam o vi tri dau tien => lay ve o vi tri so 0
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Product> findByCategory(String categoryId, int page) {
        String sql = "SELECT *\n"
                + "FROM [Product]\n"
                + "WHERE categoryId = ?\n"
                + "ORDER BY id\n"
                + "OFFSET ? ROWS\n"
                + "FETCH NEXT ? ROWS ONLY";
        pm = new LinkedHashMap<>();
        pm.put("categoryId", categoryId);
        pm.put("offset", (page - 1) * CommonConst.RECORD_PER_PAGE);
        pm.put("fetch", CommonConst.RECORD_PER_PAGE);
        return queryGenericDAO(Product.class, sql, pm);
    }

    public List<Product> findByName(String keyword, int page) {
        String sql = "SELECT *\n"
                + "FROM [Product]\n"
                + "  where [name] like ?";
        pm = new LinkedHashMap<>();
        pm.put("name", "%" + keyword + "%");
        return queryGenericDAO(Product.class, sql, pm);
    }

    public int findTotalRecordByCategory(String categoryId) {
        String sql = "SELECT count(*)\n"
                + "  FROM Product\n"
                + "  where categoryId = ?";
        pm = new LinkedHashMap<>();
        pm.put("categoryId", categoryId);
        return findTotalRecordGenericDAO(Product.class, sql, pm);
    }

    public int findTotalRecordByName(String keyword) {
        String sql = "SELECT count(*)\n"
                + "FROM [Product]\n"
                + "WHERE name like '%?%'";
        pm = new LinkedHashMap<>();
        pm.put("keyword", keyword);
        return findTotalRecordGenericDAO(Product.class, sql, pm);
    }

    public List<Product> findByPage(int page) {
        String sql = "SELECT *\n"
                + "  FROM Product\n"
                + "  ORDER BY id\n"
                + "  OFFSET ? ROWS\n" //( PAGE - 1 ) * Y
                + "  FETCH NEXT ? ROWS ONLY"; // NUMBER_RECORD_PER_PAGE
        pm = new LinkedHashMap<>();
        pm.put("offset", (page - 1) * CommonConst.RECORD_PER_PAGE);
        pm.put("fetch", CommonConst.RECORD_PER_PAGE);
        return queryGenericDAO(Product.class, sql, pm);
    }

    public int findTotalRecord() {
        String sql = "SELECT count(*)\n"
                + "  FROM Product\n";
        pm = new LinkedHashMap<>();
        return findTotalRecordGenericDAO(Product.class, sql, pm);
    }

    public void update(Product product) {
        String sql = "UPDATE [dbo].[Product]\n"
                + "   SET [name] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[categoryId] = ?\n"
                + " WHERE [id] = ?";
        pm = new LinkedHashMap<>();
        pm.put("name", product.getName());
        pm.put("image", product.getImage());
        pm.put("quantity", product.getQuantity());
        pm.put("price", product.getPrice());
        pm.put("description", product.getDescription());
        pm.put("categoryId", product.getCategoryId());
        pm.put("id", product.getId());
        updateGenericDAO(sql, pm);
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM [dbo].[Product]\n"
                + "  WHERE [id] = ?";
        pm = new LinkedHashMap<>();
        pm.put("id", id);
        deleteGenericDAO(sql, pm);
    }
}
