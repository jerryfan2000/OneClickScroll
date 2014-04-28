package com.kiumiu.ca.oneclickscroll;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

public class EventHandler {
    

    private CommandShell cmd = null;
    private String android_id = null;
    private InputShell mInputShell = null;
    public int homeCounter = 0;
    private Context mContext;

    public CommandShell getCommandShell() throws Exception {
        if (cmd == null) {
            if (android_id == null) {
                // Log.d("softkeys", "Detected emulator");
                cmd = new CommandShell("sh");
            } else {
                cmd = new CommandShell("su");
            }
        }

        return (cmd);
    }
    
    public InputShell getInputShell() throws Exception {
        if (mInputShell == null) {
            String wd = mContext.getFilesDir().getAbsolutePath();
            File jar;
            if(Build.VERSION.SDK_INT > 15) {
            	Log.d("button", "using JB code");
            	jar = new File(wd + "/input2_jb.jar");
            }else if(Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 16) {
            	Log.d("button", "using HC code");
            	jar = new File(wd + "/input2_hc.jar");
            } else {
            	Log.d("button", "using non JB code");
            	jar = new File(wd + "/input2.jar");
            }
            
            if (true) {
                AssetManager m = mContext.getResources().getAssets();
                InputStream in;
                if(Build.VERSION.SDK_INT > 15)
                	in = m.open("input2_jb.jar");
                else if(Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 16)
                	in = m.open("input2_hc.jar");
                else
                	in = m.open("input2.jar");
                FileOutputStream out = new FileOutputStream(jar);
                int read;
                byte[] b = new byte[4 * 1024];
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }
                out.close();
                in.close();
            }

            mInputShell = new InputShell("su", wd);
        }

        return (mInputShell);
    }

    public int sendKeys(final int keyids) {
        try {
            final EventHandler.InputShell cmd = getInputShell();
            Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						cmd.runCommand("down " + keyids);
		                Thread.sleep(5);
		                cmd.runCommand("up " + keyids);
					} catch(Exception e) {
						
					}
				}
            	
            });
            thread.start();
        } catch (Exception e) {
            Log.e("Button Savior", e.getMessage());
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
    
    public int sendUpTouchKeys(int keyids) {
        try {
            EventHandler.InputShell cmd = getInputShell();
            cmd.runCommand("up " + keyids);
        } catch (Exception e) {
            Log.e("Button Savior", e.getMessage());
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
    
    public int sendDownTouchKeys(int keyids) {
        try {
            EventHandler.InputShell cmd = getInputShell();
            cmd.runCommand("down " + keyids);
        } catch (Exception e) {
            Log.e("Button Savior", e.getMessage());
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
    
    public int sendDownTouchKeys(int keyids, int Repeat) {
        try {
            EventHandler.InputShell cmd = getInputShell();
            cmd.runCommand("downr " + keyids + " " + Repeat);
        } catch (Exception e) {
            Log.e("Button Savior", e.getMessage());
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    class CommandShell {
        Process p;
        OutputStream o;

        CommandShell(String shell) throws Exception {
            //Log.d("softkeys.cmdshell", "Starting shell: '" + shell + "'");
            p = Runtime.getRuntime().exec(shell);
            o = p.getOutputStream();
        }

        public void system(String cmd) throws Exception {
            //Log.d("softkeys.cmdshell", "Running command: '" + cmd + "'");
            o.write((cmd + "\n").getBytes("ASCII"));
        }

        public void close() throws Exception {
            //Log.d("softkeys.cmdshell", "Destroying shell");
            o.flush();
            o.close();
            p.destroy();  
        }
    }
    
    public class InputShell {
        Process p;
        OutputStream o;

        InputShell(String shell, String workingDir) throws Exception {
            p = Runtime.getRuntime().exec(shell);
            o = p.getOutputStream();

            // Create environment
            system("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            if(Build.VERSION.SDK_INT > 15)
            	system("export CLASSPATH=" + workingDir + "/input2_jb.jar");
            else if(Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 16)
            	system("export CLASSPATH=" + workingDir + "/input2_hc.jar");
            else
            	system("export CLASSPATH=" + workingDir + "/input2.jar");
            if(Build.VERSION.SDK_INT >= 19) {
            	//Check for selinux status and disable it
            	system("/system/bin/setenforce 0");
            }
            system("exec app_process " + workingDir + " com.smart.swkey.input");
        }

        private void system(String cmd) throws Exception {
            o.write((cmd + "\n").getBytes("ASCII"));
        }

        public void runCommand(String cmd) throws Exception {
            system(cmd);
        }

        public void close() throws Exception {
            o.flush();
            o.close();
            p.destroy();
        }
    }

    public EventHandler(Context context) {
    	mContext = context;
        android_id = Settings.Secure.getString(mContext
                .getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}