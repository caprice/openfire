/**
 * $RCSfile$
 * $Revision: 1747 $
 * $Date: 2005-08-04 18:36:36 -0300 (Thu, 04 Aug 2005) $
 *
 * Copyright (C) 2004-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.openfire.handler;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.disco.ServerFeaturesProvider;
import org.jivesoftware.util.XMPPDateTimeFormat;
import org.xmpp.packet.IQ;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

/**
 * Implements the TYPE_IQ jabber:iq:plate protocol (plate info) as
 *. Allows Jabber entities to find a user by plate.  The server will respond with username.
 *
 * @author Liu Wei
 */
public class IQFindUserByPlateHandler extends IQHandler implements ServerFeaturesProvider {

    // todo: Make display text match the locale of user (xml:lang support)
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private static final DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.LONG);

    private Element responseElement;
    private IQHandlerInfo info;

    public IQFindUserByPlateHandler() {
        super("XMPP Server Time Handler");
        info = new IQHandlerInfo("query", "jabber:iq:plate");
        responseElement = DocumentHelper.createElement(QName.get("query", "jabber:iq:plate"));
        responseElement.addElement("username");
    }

    @Override
	public IQ handleIQ(IQ packet) {
        IQ response = null;
        response = IQ.createResultIQ(packet);
        response.setChildElement(buildResponse());
        return response;
    }

    /**
     * Build the responseElement packet
     */
    private Element buildResponse() {
        Element response = responseElement.createCopy();
        Date current = new Date();
        response.element("username").setText(XMPPDateTimeFormat.formatOld(current));
        return response;
    }

    @Override
	public IQHandlerInfo getInfo() {
        return info;
    }

    public Iterator<String> getFeatures() {
        return Collections.singleton("jabber:iq:plate").iterator();
    }
}