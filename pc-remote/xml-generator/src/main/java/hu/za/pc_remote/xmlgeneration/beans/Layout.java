package hu.za.pc_remote.xmlgeneration.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/23/11
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "layout")
public class Layout {
    @XmlElements({
            @XmlElement(name = "table", type = Table.class),
            @XmlElement(name = "touchpad", type = Touchpad.class)}
    )
    public Element element;
}
