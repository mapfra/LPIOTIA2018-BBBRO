package org.eclipse.om2m.commons.obix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;
/**
 * Status oBIX enumeration
 * @author Francois Aissaoui
 *
 */
@XmlType(name = "status")
@XmlEnum
public enum Status {

    @XmlEnumValue("disabled")
    DISABLED("disabled"),
    @XmlEnumValue("fault")
    FAULT("fault"),
    @XmlEnumValue("down")
    DOWN("down"),
    @XmlEnumValue("unackedAlarm")
    UNACKED_ALARM("unackedAlarm"),
    @XmlEnumValue("alarm")
    ALARM("alarm"),
    @XmlEnumValue("unacked")
    UNACKED("unacked"),
    @XmlEnumValue("overridden")
    OVERRIDDEN("overridden"),
    @XmlEnumValue("ok")
    OK("ok");
    private final String value;

   

    
    Status(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Status fromValue(String v) {
        for (Status c: Status.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
