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

public class MainActivity extends Activity { 

    public static TextView warnt;
    TextView ipaddress;
    Button restart;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ipaddress = findViewById(R.id.ipaddress);
        warnt = findViewById(R.id.warntext);
        restart = findViewById(R.id.restart);
        
        NetworkUtils nu = new NetworkUtils();
        String ipaddr = nu.getLocalIpAddress();
        try{
            if (ipaddr == null){
                ipaddress.setText("127.0.0.1");
                warnt.setText("Please turn on mobile hotspot and press restart");
        }
            else{
                ipaddress.setText(ipaddr);
        }
        }
        catch(Exception e){
            warnt.setText(e.getMessage());
        }
        restart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    restartApp(MainActivity.this);
                }
            });
        
    }
    
    public static void restartApp(Activity activity) {
        Intent intent = activity.getPackageManager()
            .getLaunchIntentForPackage(activity.getPackageName());

        if (intent != null) {
            activity.finish();
            activity.startActivity(intent);
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
        } catch (Exception e) {
            MainActivity.warnt.setText(e.getMessage());
        }

        return null;
    }
}
