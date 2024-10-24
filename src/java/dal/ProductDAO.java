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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
