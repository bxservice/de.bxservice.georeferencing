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
package de.bxservice.georeferencing.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for BXS_Georeferencing
 *  @author iDempiere (generated) 
 *  @version Release 6.2
 */
@SuppressWarnings("all")
public interface I_BXS_Georeferencing 
{

    /** TableName=BXS_Georeferencing */
    public static final String Table_Name = "BXS_Georeferencing";

    /** AD_Table_ID=1000040 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name BXS_ColorSQL */
    public static final String COLUMNNAME_BXS_ColorSQL = "BXS_ColorSQL";

	/** Set Color Clause	  */
	public void setBXS_ColorSQL (String BXS_ColorSQL);

	/** Get Color Clause	  */
	public String getBXS_ColorSQL();

    /** Column name BXS_DescriptionSQL */
    public static final String COLUMNNAME_BXS_DescriptionSQL = "BXS_DescriptionSQL";

	/** Set Description Clause	  */
	public void setBXS_DescriptionSQL (String BXS_DescriptionSQL);

	/** Get Description Clause	  */
	public String getBXS_DescriptionSQL();

    /** Column name BXS_Georeferencing_ID */
    public static final String COLUMNNAME_BXS_Georeferencing_ID = "BXS_Georeferencing_ID";

	/** Set Georeferencing Configuration	  */
	public void setBXS_Georeferencing_ID (int BXS_Georeferencing_ID);

	/** Get Georeferencing Configuration	  */
	public int getBXS_Georeferencing_ID();

    /** Column name BXS_Georeferencing_UU */
    public static final String COLUMNNAME_BXS_Georeferencing_UU = "BXS_Georeferencing_UU";

	/** Set BXS_Georeferencing_UU	  */
	public void setBXS_Georeferencing_UU (String BXS_Georeferencing_UU);

	/** Get BXS_Georeferencing_UU	  */
	public String getBXS_Georeferencing_UU();

    /** Column name BXS_IsOrgIncluded */
    public static final String COLUMNNAME_BXS_IsOrgIncluded = "BXS_IsOrgIncluded";

	/** Set Show Organization Marker.
	  * Defines if the logged organization location is shown as a reference marker or not. (It only works if the organization selected at login is not *)
	  */
	public void setBXS_IsOrgIncluded (boolean BXS_IsOrgIncluded);

	/** Get Show Organization Marker.
	  * Defines if the logged organization location is shown as a reference marker or not. (It only works if the organization selected at login is not *)
	  */
	public boolean isBXS_IsOrgIncluded();

    /** Column name BXS_LatitudeSQL */
    public static final String COLUMNNAME_BXS_LatitudeSQL = "BXS_LatitudeSQL";

	/** Set Latitude Clause	  */
	public void setBXS_LatitudeSQL (String BXS_LatitudeSQL);

	/** Get Latitude Clause	  */
	public String getBXS_LatitudeSQL();

    /** Column name BXS_LongitudeSQL */
    public static final String COLUMNNAME_BXS_LongitudeSQL = "BXS_LongitudeSQL";

	/** Set Longitude Clause	  */
	public void setBXS_LongitudeSQL (String BXS_LongitudeSQL);

	/** Get Longitude Clause	  */
	public String getBXS_LongitudeSQL();

    /** Column name BXS_OrgColor_ID */
    public static final String COLUMNNAME_BXS_OrgColor_ID = "BXS_OrgColor_ID";

	/** Set Organization Marker Color.
	  * Color used for the organization marker
	  */
	public void setBXS_OrgColor_ID (int BXS_OrgColor_ID);

	/** Get Organization Marker Color.
	  * Color used for the organization marker
	  */
	public int getBXS_OrgColor_ID();

	public org.compiere.model.I_AD_PrintColor getBXS_OrgColor() throws RuntimeException;

    /** Column name BXS_TitleSQL */
    public static final String COLUMNNAME_BXS_TitleSQL = "BXS_TitleSQL";

	/** Set Title Clause	  */
	public void setBXS_TitleSQL (String BXS_TitleSQL);

	/** Get Title Clause	  */
	public String getBXS_TitleSQL();

    /** Column name BXS_Zoom */
    public static final String COLUMNNAME_BXS_Zoom = "BXS_Zoom";

	/** Set Zoom Level.
	  * Defines the initial Zoom Level
	  */
	public void setBXS_Zoom (BigDecimal BXS_Zoom);

	/** Get Zoom Level.
	  * Defines the initial Zoom Level
	  */
	public BigDecimal getBXS_Zoom();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name FromClause */
    public static final String COLUMNNAME_FromClause = "FromClause";

	/** Set Sql FROM.
	  * SQL FROM clause
	  */
	public void setFromClause (String FromClause);

	/** Get Sql FROM.
	  * SQL FROM clause
	  */
	public String getFromClause();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Latitude */
    public static final String COLUMNNAME_Latitude = "Latitude";

	/** Set Latitude.
	  * Defines where the center of the map will be when it first opens. 
	  */
	public void setLatitude (BigDecimal Latitude);

	/** Get Latitude.
	  * Defines where the center of the map will be when it first opens. 
	  */
	public BigDecimal getLatitude();

    /** Column name Longitude */
    public static final String COLUMNNAME_Longitude = "Longitude";

	/** Set Longitude.
	  * Defines where the center of the map will be when it first opens. 
	  */
	public void setLongitude (BigDecimal Longitude);

	/** Get Longitude.
	  * Defines where the center of the map will be when it first opens. 
	  */
	public BigDecimal getLongitude();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name OtherClause */
    public static final String COLUMNNAME_OtherClause = "OtherClause";

	/** Set Other SQL Clause.
	  * Other SQL Clause
	  */
	public void setOtherClause (String OtherClause);

	/** Get Other SQL Clause.
	  * Other SQL Clause
	  */
	public String getOtherClause();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name WhereClause */
    public static final String COLUMNNAME_WhereClause = "WhereClause";

	/** Set Sql WHERE.
	  * Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause);

	/** Get Sql WHERE.
	  * Fully qualified SQL WHERE clause
	  */
	public String getWhereClause();
}
