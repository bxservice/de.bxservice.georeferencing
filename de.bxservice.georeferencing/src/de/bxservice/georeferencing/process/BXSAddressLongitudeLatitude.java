/**********************************************************************
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Diego Ruiz - Bx Service GmbH                                      *
 **********************************************************************/
package de.bxservice.georeferencing.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MLocation;
import org.compiere.model.MOrgInfo;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.bxservice.georeferencing.tools.GeoreferencingHelperFactory;
import de.bxservice.georeferencing.tools.IGeoreferencingHelper;

public class BXSAddressLongitudeLatitude  extends SvrProcess {

	/** AD_Org_ID					*/
	private int	p_AD_Org_ID = 0;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
		}
	}

	@Override
	protected String doIt() throws Exception {
		if (log.isLoggable(Level.INFO)) log.info("Getting latitude/Longitude for addresses. ORG = " + p_AD_Org_ID);

		String whereClause = " AD_Client_ID IN (0, ?)"
				+ " AND latitude IS NULL AND longitude IS NULL";

		List<MLocation> ordersLocation = new Query(Env.getCtx(), MLocation.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[]{Env.getAD_Client_ID(Env.getCtx())})
				.setOnlyActiveRecords(true)
				.list();

		IGeoreferencingHelper mapHelper = GeoreferencingHelperFactory.getGeoreferencingHelper(null);
		if (ordersLocation != null && !ordersLocation.isEmpty()) {
			if (log.isLoggable(Level.INFO)) log.info("Addresses to set the lat/long = " + ordersLocation.size());
			mapHelper.setLatLong(ordersLocation);
		}

		if (p_AD_Org_ID > 0) {
			MOrgInfo org = MOrgInfo.get(getCtx(), p_AD_Org_ID, get_TrxName());
			MLocation orgloc = MLocation.get(getCtx(), org.getC_Location_ID(), get_TrxName());
			mapHelper.setLatLong(orgloc);
		}

		return "@OK@";
	}
}
