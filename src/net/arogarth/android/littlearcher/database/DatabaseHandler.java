package net.arogarth.android.littlearcher.database;

import net.arogarth.android.littlearcher.LittleArcher;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DatabaseHandler extends SQLiteOpenHelper {

	private final String databaseName = "littleArcher";
	private static Integer databaseVersion = 3;
//	private String tableName;

	public DatabaseHandler(String tableName) {
		super(LittleArcher.getContext(), tableName, null, databaseVersion);
	}

//	/**
//	 * @return the tableName
//	 */
//	public final String getTableName() {
//		return tableName;
//	}
//
//	/**
//	 * @param tableName
//	 *            the tableName to set
//	 */
//	public final void setTableName(String tableName) {
//		this.tableName = tableName;
//	}

	/**
	 * @return the databaseName
	 */
	public final String getDatabaseName() {
		return databaseName;
	}
}
