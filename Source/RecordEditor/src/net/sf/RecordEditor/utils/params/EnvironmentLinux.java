package net.sf.RecordEditor.utils.params;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentLinux extends EnvironmentDefault {

	@Override
	public File[] getOtherMountPoints() {
	    List<File> roots = new ArrayList<File> ();
		try {
			Process mountProcess = Runtime.getRuntime().exec ( "mount" );
			mountProcess.waitFor();
			getFiles(roots, mountProcess.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return roots.toArray(new File[roots.size()]);
	}

	public static void getFiles(List<File> roots, InputStream in ) throws IOException {
		BufferedReader mountOutput = new BufferedReader ( new InputStreamReader(in ) );
		try {
		    String line;
		    while ( (line = mountOutput.readLine ()) != null ) {
	
		        // the line will be formatted as "... on <filesystem> (...)"; get the substring we need
		        int indexStart = line.indexOf ( " on /" );
		        int indexEnd = line.indexOf ( " ", indexStart + 4 );
		        
		        if (indexStart >= 0 && indexStart < indexEnd && line.indexOf("/media/") >= 0) {
			        String s = line.substring ( indexStart + 4, indexEnd );
			        System.out.println();
			        File f = new File(s);
//					if (! s.endsWith("/")) {
//			        	File f2 = new File(s + "/");
//						System.out.println("\t\t **" + s + "\t" + f.exists() + "\t"  + f2.exists()
//			        			+ " | " + f.isDirectory() + " " + f2.isDirectory());
//						File[] files = f.listFiles();
//						if (files != null) {
//							System.out.println("\t\t 1: " + files.length);
//						}
//						files = f2.listFiles();
//						if (files != null) {
//							System.out.println("\t\t 2: " + files.length);
//						}
//			        	s = s + "/";
//			        } else {
//			        	System.out.println("\t\t????" + s + "\t" + f.exists());
//			        }
					roots.add ( f );
					
					System.out.println();
			        System.out.println((line + "                                                         ").substring(0, 60)
			        		+ "\t" + line.substring ( indexStart + 4, indexEnd ));
		        }
		    }
		} finally {
			mountOutput.close();
		}
	}
	
//	public static void main(String[] args) throws IOException {
//		byte[] b = (  "/dev/sda1 on / type ext4 (rw,errors=remount-ro)\n"
//					+ "proc on /proc type proc (rw,noexec,nosuid,nodev)\n"
//					+ "sysfs on /sys type sysfs (rw,noexec,nosuid,nodev)\n"
//					+ "none on /sys/fs/cgroup type tmpfs (rw)\n"
//					+ "none on /sys/fs/fuse/connections type fusectl (rw)\n"
//					+ "none on /sys/kernel/debug type debugfs (rw)\n"
//					+ "none on /sys/kernel/security type securityfs (rw)\n"
//					+ "udev on /dev type devtmpfs (rw,mode=0755)\n"
//					+ "devpts on /dev/pts type devpts (rw,noexec,nosuid,gid=5,mode=0620)\n"
//					+ "tmpfs on /run type tmpfs (rw,noexec,nosuid,size=10%,mode=0755)\n"
//					+ "none on /run/lock type tmpfs (rw,noexec,nosuid,nodev,size=5242880)\n"
//					+ "none on /run/shm type tmpfs (rw,nosuid,nodev)\n"
//					+ "none on /run/user type tmpfs (rw,noexec,nosuid,nodev,size=104857600,mode=0755)\n"
//					+ "none on /sys/fs/pstore type pstore (rw)\n"
//					+ "binfmt_misc on /proc/sys/fs/binfmt_misc type binfmt_misc (rw,noexec,nosuid,nodev)\n"
//					+ "systemd on /sys/fs/cgroup/systemd type cgroup (rw,noexec,nosuid,nodev,none,name=systemd)\n"
//					+ "Shared on /media/sf_Shared type vboxsf (gid=111,rw)\n"
//					+ "gvfsd-fuse on /run/user/1000/gvfs type fuse.gvfsd-fuse (rw,nosuid,nodev,user=bruce)\n"
//					+ "/dev/sdb1 on /media/bruce/72DAD2E0DAD2A021 type fuseblk (rw,nosuid,nodev,allow_other,default_permissions,blksize=4096)\n"
//			).getBytes();
//		
//		getFiles( new ArrayList<File>(), new ByteArrayInputStream(b) );
//	}
}
