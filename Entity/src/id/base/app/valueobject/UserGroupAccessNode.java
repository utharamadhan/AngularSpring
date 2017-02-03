/*
 * Created on May 17, 2004
 *
 */
package id.base.app.valueobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/*
 * Helper class for rendering user group access tree
 */
public class UserGroupAccessNode  {
    private static final long serialVersionUID = -1L;

    /**
     *
     */
    public UserGroupAccessNode() {
        super();
    }

    private long parentPK;
    private long childPK;
    private String name;
    private boolean checked;

    /**
     * @return
     */
    public long getChildPK() {
        return childPK;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public long getParentPK() {
        return parentPK;
    }

    /**
     * @param l
     */
    public void setChildPK(long l) {
        childPK = l;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * @param l
     */
    public void setParentPK(long l) {
        parentPK = l;
    }

    /**
     * @param b
     */
    public void setChecked(boolean b) {
        checked = b;
    }

    /**
     * @return
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof UserGroupAccessNode)) {
            return false;
        }
        UserGroupAccessNode rhs = (UserGroupAccessNode) object;
        return new EqualsBuilder()
            .append(this.name, rhs.name)
            .append(this.childPK, rhs.childPK)
            .append(this.parentPK, rhs.parentPK)
            .append(this.checked, rhs.checked)
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-969026759, -660742347)
            .append(this.name)
            .append(this.childPK)
            .append(this.parentPK)
            .append(this.checked)
            .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
            .append("parentPK", this.parentPK)
            .append("childPK", this.childPK)
            .append("name", this.name)
            .append("checked", this.checked)
            .toString();
    }
}
