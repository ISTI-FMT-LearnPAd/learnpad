/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package eu.learnpad.core.impl.qm;

import org.xwiki.component.annotation.Component;
import org.xwiki.rest.XWikiRestComponent;

import eu.learnpad.exception.impl.LpRestExceptionImpl;
import eu.learnpad.qm.CoreFacade;
import eu.learnpad.core.rest.RestResource;

@Component
public class XwikiCoreFacadeRestResource extends RestResource implements XWikiRestComponent, CoreFacade{

	@Override
	public void publish(String questionnairesId, String type,
			byte[] questionnairesFile) throws LpRestExceptionImpl {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genrationCompleted(String questionnairesId)
			throws LpRestExceptionImpl {
		// TODO Auto-generated method stub
		
	}


}
