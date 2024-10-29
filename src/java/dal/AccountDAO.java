package dal;

import entity.Account;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author DuongNKTHE186476
 */
public class AccountDAO extends GenericDAO<Account> {

    @Override
    public List findAll() {
        return queryGenericDAO(Account.class);
    }

    public int insert(Account t) {
        String sql = "INSERT INTO [dbo].[Account]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[email]\n"
                + "           ,[address]\n"
                + "           ,[roleId])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,2)";
        pm = new LinkedHashMap<>();
        pm.put("username", t.getUsername());
        pm.put("password", t.getPassword());
        pm.put("email", t.getEmail());
        pm.put("address", t.getAddress());
        return insertGenericDAO(sql,pm);
    }

    public Account findByUsernameAndPass(Account account) {
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Account]\n"
                + "  WHERE username = ? AND password = ?";
        pm = new LinkedHashMap<>();
        pm.put("username", account.getUsername());
        pm.put("password", account.getPassword());

        List<Account> list = queryGenericDAO(Account.class, sql, pm);
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean checkUsernameExist(Account account) {
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Account]\n"
                + "  WHERE username = ?";
        pm = new LinkedHashMap<>();
        pm.put("username", account.getUsername());
        return !queryGenericDAO(Account.class, sql, pm).isEmpty();
    }
}
