package net.arogarth.android.littlearcher.database.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;

/**
 * Create and restore backup of app databases
 * 
 * The Backups are stored in a zip file
 * 
 * @author arogarth
 *
 */
public class DatabaseBackupHelper {
	private String packagename = "";
	private String backupFolder = "";
	
	private static final Integer BUFFER = 2028;
	
	private ZipOutputStream outZipFile = null;
	private ZipInputStream inZipFile = null;
	
	public DatabaseBackupHelper(Context context) {
		// fetch the package name from app
		this.setPackagename(context.getPackageName());
		
		// set default backup folder
		StringBuilder sb = new StringBuilder();
		sb.append("data");
		sb.append("//");
		sb.append(this.getPackagename());
		sb.append("//");
		sb.append("backup");
		sb.append("//");
		this.setBackupFolder(sb.toString());
	}
	
	
	//#########################################################################
	//## GETTERS/SETTERS
	//#########################################################################
	
	/**
	 * @return the packagename
	 */
	public final String getPackagename() {
		return packagename;
	}


	/**
	 * @param packagename the packagename to set
	 */
	public final void setPackagename(String packagename) {
		this.packagename = packagename;
	}


	/**
	 * @return the backupFolder
	 */
	public final String getBackupFolder() {
		return backupFolder;
	}


	/**
	 * @param backupFolder the backupFolder to set
	 */
	public final void setBackupFolder(String backupFolder) {
		this.backupFolder = backupFolder;
	}
	
	//#########################################################################
	//## RESTORE
	//#########################################################################
	
	/**
	 * Restore Backup
	 */
	public void restoreBackup(File file) throws Exception {
		this.initInBackupZip(file);
		
		this.restoreDatabase();
		
		this.finishInBackupZip();
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 */
	private void initInBackupZip(File source) throws FileNotFoundException, IOException {
		if (source.canRead()) {
			FileInputStream dest = new FileInputStream(source); 
			this.inZipFile = new ZipInputStream(new BufferedInputStream(dest));
		} else {
			throw new IOException("source not readable");
		}
	}
	
	private void finishInBackupZip() throws IOException {
		this.inZipFile.close();
	}
	
	/**
	 * restore the databases from zip file
	 * 
	 * take care it will delete old databases if exists
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void restoreDatabase() throws FileNotFoundException, IOException {
		// Zip entry for file
		ZipEntry entry = null;
		
		// The system data directory
		File data = Environment.getDataDirectory();
		
		// iterate over zip entries
		while((entry = this.inZipFile.getNextEntry()) != null) {
			
			String currentDBPath = String.format(
					"//data//%s//databases//%s",
					this.getPackagename(), entry.getName());
			
			File currentDB = new File(data, currentDBPath);
			
			// create directories if they don't exists
			new File(currentDB.getPath()).mkdirs();
			
			// delete old database if exist 
			if (currentDB.exists()) {
				currentDB.delete();
			}
			
			// initialize the buffer
			byte buffer[] = new byte[BUFFER];
			
			// Output stream for database to write
			FileOutputStream fo = new FileOutputStream(currentDB);
			BufferedOutputStream origin = new BufferedOutputStream(fo, BUFFER);
			
			// Read zip stream and write to file
			int count;
			while ((count = this.inZipFile.read(buffer, 0, BUFFER)) != -1) {
				origin.write(buffer, 0, count);
			}
			
			origin.close();
		}
	}
	
	//#########################################################################
	//## BACKUP
	//#########################################################################
	
	/**
	 * 
	 * @throws Exception
	 */
	public void createBackup() throws Exception {
		this.initOutBackupZip();
		
		this.backupDatabases();
		
		this.finishOutBackupZip();
	}
	
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	private void initOutBackupZip() throws FileNotFoundException, IOException {
		File sd = Environment.getExternalStorageDirectory();
		
		if (sd.canWrite()) {
			String timestamp = DateFormat.format("yyyyMMddhhmmss", Calendar.getInstance()).toString();
					
			String file = this.getBackupFolder() +  timestamp + ".zip";
			
			File backupDB = new File(sd, file);
			backupDB.getParentFile().mkdirs();
			
			FileOutputStream dest = new FileOutputStream(backupDB); 
			this.outZipFile = new ZipOutputStream(new BufferedOutputStream(dest));
		} else {
			throw new IOException("cannot write file");
		}
	}
	
	/**
	 * finish and close the zipfile
	 * 
	 * @throws IOException
	 */
	private void finishOutBackupZip() throws IOException {
		this.outZipFile.close();
	}
	
	/**
	 * backup the database
	 * 
	 * @param database
	 * @throws IOException
	 */
	private void backupDatabases() throws IOException {
        File data = Environment.getDataDirectory();
        
        // Path where the databases exists
        String currentDBPath = String.format(
        		"//data//%s//databases", this.packagename);
        
        File dbDirectory = new File(data, currentDBPath);

        for(File currentDB : dbDirectory.listFiles()) {
        	// initialize the buffer
            byte buffer[] = new byte[BUFFER];
       
            // open the database input stream
			FileInputStream fi = new FileInputStream(currentDB);
			BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
			
			// The zip entry for the file
			ZipEntry entry = new ZipEntry(currentDB.getName());
			this.outZipFile.putNextEntry(entry);
			
			// Write stream to zip file
			int count;
			while ((count = origin.read(buffer, 0, BUFFER)) != -1) {
				this.outZipFile.write(buffer, 0, count);
			}
			
			origin.close();
        }			
	}
	
	//#########################################################################
	//## ADDITIONAL
	//#########################################################################
	
	public ArrayList<File> listBackups() {
		File sd = Environment.getExternalStorageDirectory();
		File backupDir = new File(sd, this.getBackupFolder());
		
		ArrayList<File> files = new ArrayList<File>();
		
		File[] filesArray = backupDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.toLowerCase().endsWith("zip");
			}
		});
		
		if(filesArray != null) {
			Collections.addAll(files, filesArray);
		}
		
		
		return files;
	}
}
