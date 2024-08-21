package com.shuhaib.ipf;
 
import android.app.Activity;
import android.os.Bundle;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.view.WindowManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class MainActivity extends Activity { 

    public static TextView warnt;
    public static TextView ipaddress;
    public static Button restart;
    public static String err;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ipaddress = findViewById(R.id.ipaddress);
        warnt = findViewById(R.id.warntext);
        restart = findViewById(R.id.restart);
        refreshIP();
        restart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    refreshIP();
                }
            });
        
        
    }
    
   
    
    public void refreshIP() {
        NetworkUtils nu = new NetworkUtils();
        final String ipaddr = nu.getLocalIpAddress();
        
        ipaddress.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (ipaddr != null){
                    ClipData clip = ClipData.newPlainText("Copied Text", ipaddr);
                    clipboard.setPrimaryClip(clip);}
                    else{
                        ClipData clip = ClipData.newPlainText("Copied Text", "127.0.0.1");
                        clipboard.setPrimaryClip(clip);
                    }
                }
            });
        
           if (ipaddr == null){
               ipaddress.setText("127.0.0.1");
               warnt.setText("Turn on mobile hotspot and press refresh");
            }
            else{
                ipaddress.setText(ipaddr);
                warnt.setText("");
            }
        
    }
	
} 

class NetworkUtils {

    public static String getLocalIpAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface : interfaces) {
                
                if (networkInterface.getName().equalsIgnoreCase("wlan0") ||
                    networkInterface.getName().equalsIgnoreCase("ap0")) {

                    List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());

                    for (InetAddress inetAddress : inetAddresses) {
                        if (inetAddress instanceof Inet4Address) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        }
        catch (Exception e) {
           return null;
        }

        return null;
    }
}
