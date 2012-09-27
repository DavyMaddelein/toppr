package com.compomics.ppr.db.accessors;

import com.compomics.ppr.db.accessors.UserTableAccessor;

import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: niklaascolaert
 * Date: 4-okt-2007
 * Time: 9:41:36
 * To change this template use File | Settings | File Templates.
 */


/**
 * This class provides the following enhancements over the UserTableAccessor:
 *
 * <ul>
 *   <li><i>constructor</i>: to read a single User from a ResultSet.</li>
 *   <li><b>toString()</b>: returns the name of the User.</li>
 * </ul>
 *
 * @author Niklaas Colaert
 */
public class User extends UserTableAccessor {

    /**
     * Simple wrapper for the superclass constructor.
     *
     * @param aParams   HashMap with the parameters.
     */
    public User(HashMap aParams) {
        super(aParams);
    }

    /**
     * This constructor sets the only 'settable' field: username.
     *
     * @param aName String with the full name for the user.
     */
    public User(String aName) {
        super.setName(aName);
    }

    /**
     * This constructor reads a User from a ResultSet. The ResultSet should be positioned such that
     * a single row can be read directly (i.e., without calling the 'next()' method on the ResultSet). <br />
     * The columns should be in this order: <br />
     * Column 1: user ID <br />
     * Column 2: name <br />
     * Column 3: username< br />
     * Column 4: creationdate < br />
     * Column 5: modificationdate < br />
     *
     * @param   aRS ResultSet to read the data from.
     * @exception   java.sql.SQLException    when reading the ResultSet failed.
     */
    public User(ResultSet aRS) throws SQLException {
        this.iUserid = aRS.getLong(1);
        this.iName = aRS.getString(2);
        this.iUsername = aRS.getString(3);
        this.iCreationdate = (java.sql.Timestamp)aRS.getObject(4);
        this.iModificationdate = (java.sql.Timestamp)aRS.getObject(5);
    }

    /**
     * This method retrieves all useccrs from the connection and stores them in a HashMap. <br />
     * The userid is the key (Long type) and the User object is the value.
     *
     * @param aConn Connection to retrieve the Users from.
     * @return  HashMap with the Users, userid is the key (Long type) and User objects are the values.
     * @throws SQLException when the retrieve failed.
     */
    public static HashMap getAllUsersAsMap(Connection aConn) throws SQLException {
        HashMap lUsers = new HashMap();
        PreparedStatement prep = aConn.prepareStatement("select userid, name, username, creationdate, modificationdate from User");
        ResultSet rs = prep.executeQuery();
        while(rs.next()) {
            User temp = new User(rs);
            lUsers.put(new Long(temp.getUserid()),temp);
        }
        rs.close();
        prep.close();

        return lUsers;
    }

    /**
     * This method retrieves all Users from the connection and stores them in a User[].
     *
     * @param aConn Connection to retrieve the Users from.
     * @return  User[] with the Users.
     * @throws SQLException when the retrieve failed.
     */
    public static User[] getAllUsers(Connection aConn) throws SQLException {
        PreparedStatement prep = aConn.prepareStatement("select userid, name, username, creationdate, modificationdate from User");
        ResultSet rs = prep.executeQuery();
        Vector temp = new Vector();
        while(rs.next()) {
            temp.add(new User(rs));
        }
        User[] result = new User[temp.size()];
        temp.toArray(result);
        rs.close();
        prep.close();
        return result;
    }


    /**
     * This method returns a String representation of the User, ie.: the name.
     *
     * @return  String  with the name of the User.
     */
    public String toString() {
        return this.iName;
    }
}

