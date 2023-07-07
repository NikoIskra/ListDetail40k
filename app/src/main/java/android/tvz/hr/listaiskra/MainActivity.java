package android.tvz.hr.listaiskra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.tvz.hr.listaiskra.Database.ListItemDB;
import android.tvz.hr.listaiskra.Database.ListItemDatabase;
import android.tvz.hr.listaiskra.Fragment.PrviFragment;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListItem> mItemList;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.frameLayout2)!=null) {
            mTwoPane=true;
        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel= new NotificationChannel("kanal", "ime kanala", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager=(NotificationManager) (getSystemService(Context.NOTIFICATION_SERVICE));
            notificationManager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("fetching failed");
                            return;
                        }
                        String token=task.getResult();
                        System.out.println(token);
                        Toast.makeText(MainActivity.this, "token: " + token , Toast.LENGTH_SHORT).show();
                    }
                });

        ListItemDatabase db = Room.databaseBuilder(getApplicationContext(),
                ListItemDatabase.class, "listItem-database").allowMainThreadQueries().build();

        ListItemDB item1=new ListItemDB(R.drawable.necron, "Necrons", "The necron xenos species");
        ListItemDB item2=new ListItemDB(R.drawable.imperium, "Imperium of man", "The imperium");
        ListItemDB item3=new ListItemDB(R.drawable.chaos, "Chaos", "Chaos");

        db.listItemDAO().insertAll(item1, item2, item3);

        List<ListItemDB> itemDBList=db.listItemDAO().getAllItems();


        PrviFragment fragment=new PrviFragment();

        Bundle bundle=new Bundle();

        bundle.putParcelable("listItem1", itemDBList.get(0));
        bundle.putParcelable("listItem2", itemDBList.get(1));
        bundle.putParcelable("listItem3", itemDBList.get(2));
        if(mTwoPane==true)
            bundle.putString("twopane", "true");

        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();



    }

    public void createItemList() {
        mItemList=new ArrayList<>();
        mItemList.add(new ListItem(R.drawable.necron, "Necrons", "The necron xenos species", "https://warhammer40k.fandom.com/wiki/Necrons"));
        mItemList.add(new ListItem(R.drawable.imperium, "Imperium of man", "The imperium", "https://warhammer40k.fandom.com/wiki/Imperium_of_Man"));
        mItemList.add(new ListItem(R.drawable.chaos, "Chaos", "Chaos", "https://warhammer40k.fandom.com/wiki/Chaos"));
    }

}