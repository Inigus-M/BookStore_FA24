package dal;

import entity.Role;
import java.util.List;

/**
 *
 * @author DuongNKTHE186476
 */
public class RoleDAO extends GenericDAO{

    @Override
    public List findAll() {
        return queryGenericDAO(Role.class);
    }

    @Override
    public int insert(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
