/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.bxservice.georeferencing.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for BXS_Georeferencing
 *  @author iDempiere (generated) 
 *  @version Release 6.2 - $Id$ */
public class X_BXS_Georeferencing extends PO implements I_BXS_Georeferencing, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190718L;

    /** Standard Constructor */
    public X_BXS_Georeferencing (Properties ctx, int BXS_Georeferencing_ID, String trxName)
    {
      super (ctx, BXS_Georeferencing_ID, trxName);
      /** if (BXS_Georeferencing_ID == 0)
        {
			setAD_Table_ID (0);
			setBXS_Georeferencing_ID (0);
			setBXS_LatitudeSQL (null);
			setBXS_LongitudeSQL (null);
			setFromClause (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_BXS_Georeferencing (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_BXS_Georeferencing[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Color Clause.
		@param BXS_ColorSQL Color Clause	  */
	public void setBXS_ColorSQL (String BXS_ColorSQL)
	{
		set_Value (COLUMNNAME_BXS_ColorSQL, BXS_ColorSQL);
	}

	/** Get Color Clause.
		@return Color Clause	  */
	public String getBXS_ColorSQL () 
	{
		return (String)get_Value(COLUMNNAME_BXS_ColorSQL);
	}

	/** Set Description Clause.
		@param BXS_DescriptionSQL Description Clause	  */
	public void setBXS_DescriptionSQL (String BXS_DescriptionSQL)
	{
		set_Value (COLUMNNAME_BXS_DescriptionSQL, BXS_DescriptionSQL);
	}

	/** Get Description Clause.
		@return Description Clause	  */
	public String getBXS_DescriptionSQL () 
	{
		return (String)get_Value(COLUMNNAME_BXS_DescriptionSQL);
	}

	/** Set Georeferencing Configuration.
		@param BXS_Georeferencing_ID Georeferencing Configuration	  */
	public void setBXS_Georeferencing_ID (int BXS_Georeferencing_ID)
	{
		if (BXS_Georeferencing_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_BXS_Georeferencing_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_BXS_Georeferencing_ID, Integer.valueOf(BXS_Georeferencing_ID));
	}

	/** Get Georeferencing Configuration.
		@return Georeferencing Configuration	  */
	public int getBXS_Georeferencing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BXS_Georeferencing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BXS_Georeferencing_UU.
		@param BXS_Georeferencing_UU BXS_Georeferencing_UU	  */
	public void setBXS_Georeferencing_UU (String BXS_Georeferencing_UU)
	{
		set_Value (COLUMNNAME_BXS_Georeferencing_UU, BXS_Georeferencing_UU);
	}

	/** Get BXS_Georeferencing_UU.
		@return BXS_Georeferencing_UU	  */
	public String getBXS_Georeferencing_UU () 
	{
		return (String)get_Value(COLUMNNAME_BXS_Georeferencing_UU);
	}

	/** Set Show Organization Marker.
		@param BXS_IsOrgIncluded 
		Defines if the logged organization location is shown as a reference marker or not. (It only works if the organization selected at login is not *)
	  */
	public void setBXS_IsOrgIncluded (boolean BXS_IsOrgIncluded)
	{
		set_Value (COLUMNNAME_BXS_IsOrgIncluded, Boolean.valueOf(BXS_IsOrgIncluded));
	}

	/** Get Show Organization Marker.
		@return Defines if the logged organization location is shown as a reference marker or not. (It only works if the organization selected at login is not *)
	  */
	public boolean isBXS_IsOrgIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_BXS_IsOrgIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Latitude Clause.
		@param BXS_LatitudeSQL Latitude Clause	  */
	public void setBXS_LatitudeSQL (String BXS_LatitudeSQL)
	{
		set_Value (COLUMNNAME_BXS_LatitudeSQL, BXS_LatitudeSQL);
	}

	/** Get Latitude Clause.
		@return Latitude Clause	  */
	public String getBXS_LatitudeSQL () 
	{
		return (String)get_Value(COLUMNNAME_BXS_LatitudeSQL);
	}

	/** Set Longitude Clause.
		@param BXS_LongitudeSQL Longitude Clause	  */
	public void setBXS_LongitudeSQL (String BXS_LongitudeSQL)
	{
		set_Value (COLUMNNAME_BXS_LongitudeSQL, BXS_LongitudeSQL);
	}

	/** Get Longitude Clause.
		@return Longitude Clause	  */
	public String getBXS_LongitudeSQL () 
	{
		return (String)get_Value(COLUMNNAME_BXS_LongitudeSQL);
	}

	public org.compiere.model.I_AD_PrintColor getBXS_OrgColor() throws RuntimeException
    {
		return (org.compiere.model.I_AD_PrintColor)MTable.get(getCtx(), org.compiere.model.I_AD_PrintColor.Table_Name)
			.getPO(getBXS_OrgColor_ID(), get_TrxName());	}

	/** Set Organization Marker Color.
		@param BXS_OrgColor_ID 
		Color used for the organization marker
	  */
	public void setBXS_OrgColor_ID (int BXS_OrgColor_ID)
	{
		if (BXS_OrgColor_ID < 1) 
			set_Value (COLUMNNAME_BXS_OrgColor_ID, null);
		else 
			set_Value (COLUMNNAME_BXS_OrgColor_ID, Integer.valueOf(BXS_OrgColor_ID));
	}

	/** Get Organization Marker Color.
		@return Color used for the organization marker
	  */
	public int getBXS_OrgColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BXS_OrgColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Title Clause.
		@param BXS_TitleSQL Title Clause	  */
	public void setBXS_TitleSQL (String BXS_TitleSQL)
	{
		set_Value (COLUMNNAME_BXS_TitleSQL, BXS_TitleSQL);
	}

	/** Get Title Clause.
		@return Title Clause	  */
	public String getBXS_TitleSQL () 
	{
		return (String)get_Value(COLUMNNAME_BXS_TitleSQL);
	}

	/** Set Zoom Level.
		@param BXS_Zoom 
		Defines the initial Zoom Level
	  */
	public void setBXS_Zoom (BigDecimal BXS_Zoom)
	{
		set_Value (COLUMNNAME_BXS_Zoom, BXS_Zoom);
	}

	/** Get Zoom Level.
		@return Defines the initial Zoom Level
	  */
	public BigDecimal getBXS_Zoom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BXS_Zoom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Sql FROM.
		@param FromClause 
		SQL FROM clause
	  */
	public void setFromClause (String FromClause)
	{
		set_Value (COLUMNNAME_FromClause, FromClause);
	}

	/** Get Sql FROM.
		@return SQL FROM clause
	  */
	public String getFromClause () 
	{
		return (String)get_Value(COLUMNNAME_FromClause);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Latitude.
		@param Latitude 
		Defines where the center of the map will be when it first opens. 
	  */
	public void setLatitude (BigDecimal Latitude)
	{
		set_Value (COLUMNNAME_Latitude, Latitude);
	}

	/** Get Latitude.
		@return Defines where the center of the map will be when it first opens. 
	  */
	public BigDecimal getLatitude () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Latitude);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Longitude.
		@param Longitude 
		Defines where the center of the map will be when it first opens. 
	  */
	public void setLongitude (BigDecimal Longitude)
	{
		set_Value (COLUMNNAME_Longitude, Longitude);
	}

	/** Get Longitude.
		@return Defines where the center of the map will be when it first opens. 
	  */
	public BigDecimal getLongitude () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Longitude);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Other SQL Clause.
		@param OtherClause 
		Other SQL Clause
	  */
	public void setOtherClause (String OtherClause)
	{
		set_Value (COLUMNNAME_OtherClause, OtherClause);
	}

	/** Get Other SQL Clause.
		@return Other SQL Clause
	  */
	public String getOtherClause () 
	{
		return (String)get_Value(COLUMNNAME_OtherClause);
	}

	/** Set Sql WHERE.
		@param WhereClause 
		Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	/** Get Sql WHERE.
		@return Fully qualified SQL WHERE clause
	  */
	public String getWhereClause () 
	{
		return (String)get_Value(COLUMNNAME_WhereClause);
	}
}