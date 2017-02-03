package id.base.app.valueobject.util;

import id.base.app.valueobject.Lookup;

import java.util.ArrayList;
import java.util.List;


public class LookupList extends ArrayList<Lookup> implements List<Lookup>  {
    private static final long serialVersionUID = -727806392028272161L;

    public Lookup getByLookupCode(String code) {
        for (Lookup temp : this) {
            if (code.equals(temp.getCode())) {
                return temp;
            }
        }
        return null;
    }

    public Lookup getByLookupPK(long lookupPK) {
        for (Lookup temp : this) {
            if (lookupPK == temp.getPkLookup().longValue()) {
                return temp;
            }
        }
        return null;
    }

}
